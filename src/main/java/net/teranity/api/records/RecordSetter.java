package net.teranity.api.records;

import net.teranity.api.OrionConnection;
import net.teranity.api.OrionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordSetter extends RecordSetup {
    private OrionTable orionTable;

    private OrionConnection orionConnection;
    private Connection connection;

    private String selectString;
    private Object selectObject;
    private String parentString;
    private Object parentObject;
    private Object previousObject;

    private boolean next;
    private ResultSet resultSet;

    public RecordSetter(OrionTable orionTable) {
        this.orionTable = orionTable;

        orionConnection = orionTable.getOrionConnection();
        connection = orionConnection.getConnection();;
    }

    @Override
    public void setup() {
        if (connection == null) return;

        checkPreviousObject();
        try {
            String sql = "update " + orionTable.getTableName() + " set " + selectString + " = ? where " + parentString + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, selectObject);
            statement.setObject(2, parentObject);
            statement.executeUpdate();

            next = true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RecordSetter setSelect(String string, Object object) {
        this.selectString = string;
        this.selectObject = object;
        return this;
    }

    public RecordSetter setParent(String string, Object object) {
        this.parentString = string;
        this.parentObject = object;
        return this;
    }

    public void checkPreviousObject() {
        RecordGetter recordGetter = new RecordGetter(orionTable)
                .setSelect(selectString)
                .setParent(parentString, parentObject);
        recordGetter.setup();
        this.previousObject = recordGetter.get();
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

    public String getSelectString() {
        return selectString;
    }

    public Object getSelectObject() {
        return selectObject;
    }

    public String getParentString() {
        return parentString;
    }

    public Object getParentObject() {
        return parentObject;
    }

    public Object getPreviousObject() {
        return previousObject;
    }

    public boolean isNext() {
        return next;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
