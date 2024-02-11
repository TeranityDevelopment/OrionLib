package net.teranity.api.records;

import net.teranity.api.OrionConnection;
import net.teranity.api.OrionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordInserter extends RecordSetup {
    private OrionTable orionTable;

    private OrionConnection orionConnection;
    private Connection connection;

    private List<String> records;
    private List<Object> objects;

    private boolean next;

    public RecordInserter(OrionTable orionTable) {
        this.orionTable = orionTable;

        orionConnection = orionTable.getOrionConnection();
        connection = orionConnection.getConnection();

        records = new ArrayList<>();
        objects = new ArrayList<>();
    }

    @Override
    public void setup() {
        if (connection == null) return;
        if (records.isEmpty() || objects.isEmpty()) return;
        if (records.size() != objects.size()) return;

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        for (String str : records) {
            stringBuilder.append(str + ",");
        }
        for (Object obj : objects) {
            stringBuilder1.append("?,");
        }

        int length = stringBuilder.length();
        length = length - 1;

        stringBuilder.deleteCharAt(length);

        length = stringBuilder1.length();
        length = length - 1;

        stringBuilder1.deleteCharAt(length);
        try {
            String sql = "insert into " + orionTable.getTableName() + " (" + stringBuilder.toString() + ") values (" + stringBuilder1.toString() + ")";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Object object : objects) {
                int index = objects.indexOf(object) + 1;

                if (object instanceof String) {
                    statement.setString(index, (String) object);
                } else if (object instanceof Integer) {
                    statement.setInt(index, (Integer) object);
                } else if (object instanceof Boolean) {
                    statement.setBoolean(index, (Boolean) object);
                }
            }

            statement.executeUpdate();
            next = true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(String... records) {
        for (String record : records) {
            this.records.add(record);
        }
    }

    public void addObject(Object... objects) {
        for (Object object : objects) {
            this.objects.add(object);
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

    public List<String> getRecords() {
        return records;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public boolean isNext() {
        return next;
    }
}
