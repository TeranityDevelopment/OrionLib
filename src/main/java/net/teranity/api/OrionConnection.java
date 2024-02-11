package net.teranity.api;

import java.sql.Connection;

public class OrionConnection {
    private String databaseName;
    private Connection connection;

    public OrionConnection(String databaseName, Connection connection) {
        this.databaseName = databaseName;
        this.connection = connection;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Connection getConnection() {
        return connection;
    }
}
