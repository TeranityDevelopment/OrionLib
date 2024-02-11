package net.teranity.api.records;

import net.teranity.api.OrionConnection;
import net.teranity.api.OrionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecordRemover extends RecordSetup {
    private OrionTable orionTable;

    private OrionConnection orionConnection;
    private Connection connection;

    private String parentString;
    private Object parentObject;

    private boolean next;

    public RecordRemover(OrionTable orionTable) {
        this.orionTable = orionTable;

        orionConnection = orionTable.getOrionConnection();
        connection = orionConnection.getConnection();
    }

    @Override
    public void setup() {
        if (connection == null) return;

        try {
            String sql = "delete from " + orionTable.getTableName() + " where " + parentString + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, parentObject);
            statement.executeUpdate();

            next = true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrionTable getOrionTable() {
        return orionTable;
    }

    public OrionConnection getOrionConnection() {
        return orionConnection;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getParentString() {
        return parentString;
    }

    public Object getParentObject() {
        return parentObject;
    }

    public boolean isNext() {
        return next;
    }

    public RecordRemover setParent(String string, Object object) {
        this.parentString = string;
        this.parentObject = object;
        return this;
    }
}
