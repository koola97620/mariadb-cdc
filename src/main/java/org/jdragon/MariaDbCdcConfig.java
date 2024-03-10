package org.jdragon;

public class MariaDbCdcConfig {

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String positionTraceFile;

    public MariaDbCdcConfig(String host, int port, String user, String password, String positionTraceFile) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.positionTraceFile = positionTraceFile;
    }

}
