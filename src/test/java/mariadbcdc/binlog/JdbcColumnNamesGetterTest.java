package mariadbcdc.binlog;

import org.jdragon.JdbcColumnNamesGetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Testcontainers
public class JdbcColumnNamesGetterTest {

    @Container
    public MariaDBContainer mariaDB = (MariaDBContainer) new MariaDBContainer("mariadb:10.5")
            .withConfigurationOverride("conf.d.105")
            .withInitScript("init.sql");

    private MariaCdcTestHelper helper;

    @BeforeEach
    void setUp() {
        helper = new MariaCdcTestHelper(mariaDB);
        helper.createCdcUser("cdc", "cdc");
    }

    @Test
    void getColumnNames() {
        JdbcColumnNamesGetter getter = new JdbcColumnNamesGetter(
                "localhost", mariaDB.getMappedPort(3306), "root", "test");
        List<String> cols = getter.getColumnNames("test", "menu");
        assertThat(cols).contains("id", "displayed", "name", "price", "menu_group_id");
    }


}
