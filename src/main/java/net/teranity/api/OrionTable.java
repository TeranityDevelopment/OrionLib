package net.teranity.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrionTable {
    private String tableName;
    private OrionConnection orionConnection;

    private Connection connection;

    public OrionTable(String tableName, OrionConnection orionConnection) {
        this.tableName = tableName;
        this.orionConnection = orionConnection;

        connection = orionConnection.getConnection();

        if (!exists()) return;
    }

    public boolean exists() {
        return (exists(getTableName()) != false);
    }

    public boolean exists(String tableName) {
        if (connection == null) return false;

        try {
            String sql = "select * from " + tableName;
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return true;
            } return false;
        }catch (SQLException e) {
            e.printStackTrace();
        } return false;
    }

    public String getTableName() {
        return tableName;
    }

    public OrionConnection getOrionConnection() {
        return orionConnection;
    }
}
