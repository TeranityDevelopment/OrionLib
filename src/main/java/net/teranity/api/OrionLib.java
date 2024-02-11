package net.teranity.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrionLib {
    private static OrionLib instance = new OrionLib();
    private List<OrionConnection> connections;

    public OrionLib() {
        connections = new ArrayList<>();
    }

    public OrionConnection connectSQL(OrionConnector orionConnector) {
        try {
            orionConnector.connect();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        if (orionConnector.isConnected()) {
            addConnection(orionConnector.getConnection());
            return orionConnector.getConnection();
        } return null;
    }

    public List<OrionConnection> getConnections() {
        return connections;
    }

    public void addConnection(OrionConnection orionConnection) {
        if (connections.contains(orionConnection)) return;

        connections.add(orionConnection);
    }

    public OrionConnection getConnection(String databaseName) {
        for (OrionConnection orionConnection : getConnections()) {
            if (orionConnection.getDatabaseName().contains(databaseName)) {
                return orionConnection;
            }
        } return null;
    }
}
