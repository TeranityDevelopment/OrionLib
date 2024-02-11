package net.teranity.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class OrionConnector {
    private String username, password, database, host;
    private int port;
    private boolean ssl;

    private OrionConnection orionConnection;
    private HikariDataSource dataSource;

    private boolean connected = false;
    private boolean connecting = false;

    public OrionConnector(String username, String password, String database, String host, int port, boolean ssl) {
        this.username = username;
        this.password = password;
        this.database = database;
        this.host = host;
        this.port = port;
        this.ssl = ssl;
    }

    public OrionConnection connect() throws SQLException {
        if (orionConnection != null) return null;

        connecting = true;

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("useSSL", false);
        dataSource = new HikariDataSource(hikariConfig);

        connecting = false;
        connected = true;

        orionConnection = new OrionConnection(database, dataSource.getConnection());
        return orionConnection;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public OrionConnection getConnection() {
        return orionConnection;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isConnecting() {
        return connecting;
    }
}
