import org.jdragon.MariaDbCdcConfig;
import org.testcontainers.containers.MariaDBContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaCdcTestHelper {
    private String host;
    private Integer port;
    private String rootPassword;
    private String username;
    private String password;

    private String cdcUser;
    private String cdcPassword;

    public MariaCdcTestHelper(MariaDBContainer mariaDB) {
        host = "localhost";
        port = mariaDB.getMappedPort(3306);
        rootPassword = mariaDB.getPassword();
        username = mariaDB.getUsername();
        password = mariaDB.getPassword();
    }

    public void createCdcUser(String user, String password) {
        try (Connection conn = getRootConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE USER '" + user + "'@'%' IDENTIFIED BY '" + password + "'");
            stmt.executeUpdate("GRANT REPLICATION SLAVE, REPLICATION CLIENT, SELECT ON *.* TO '" + user + "'@'%'");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        this.cdcUser = user;
        this.cdcPassword = password;
    }

    public void deleteSavedPositionFile(String positionFilePath) {
        Path path = Paths.get(positionFilePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MariaDbCdcConfig createMariadbCdcConfig(String posFilePath) {
        return new MariaDbCdcConfig(
                host,
                port(),
                cdcUser(),
                cdcPassword(),
                posFilePath);
    }

    public int port() {
        return port;
    }

    public String cdcUser() {
        return cdcUser;
    }
    public String cdcPassword() {
        return cdcPassword;
    }


    public Connection getRootConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mariadb://" + host + ":" + port(),
                "root",
                getRootPassword());
    }

    private String getRootPassword() {
        return rootPassword;
    }


}
