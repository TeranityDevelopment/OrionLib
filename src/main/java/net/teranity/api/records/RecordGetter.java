package net.teranity.api.records;

import net.teranity.api.OrionConnection;
import net.teranity.api.OrionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordGetter extends RecordSetup {
    private OrionTable orionTable;

    private OrionConnection orionConnection;
    private Connection connection;

    private String select;
    private String parentString;
    private Object parentObject;

    private boolean next;
    private ResultSet resultSet;

    public RecordGetter(OrionTable orionTable) {
        this.orionTable = orionTable;

        orionConnection = orionTable.getOrionConnection();
        connection = orionConnection.getConnection();
    }

    @Override
    public void setup() {
        if (connection == null) return;

        try {
            String sql;
            if (parentString == null && parentObject == null) {
                sql = "select " + select + " from " + orionTable.getTableName();
            }

            sql = "select " + select + " from " + orionTable.getTableName() + " where " + parentString + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            if (parentObject != null) {
                statement.setObject(1, parentObject);
            }

            resultSet = statement.executeQuery();
            next = true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object get() {
        if (connection == null) return null;
        if (resultSet == null) return null;
        if (next != true) return null;

        try {
            while (resultSet.next()) {
                return resultSet.getObject(select);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    public String getParentString() {
        return parentString;
    }

    public RecordGetter setSelect(String select) {
        this.select = select;
        return this;
    }

    public RecordGetter setParent(String name, Object object) {
        this.parentString = parentString;
        this.parentObject = object;
        return this;
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

    public String getSelect() {
        return select;
    }

    public Object getParentObject() {
        return parentObject;
    }

    public boolean isNext() {
        return next;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
