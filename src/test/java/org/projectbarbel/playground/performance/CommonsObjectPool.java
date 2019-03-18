package org.projectbarbel.playground.performance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.Test;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

public class CommonsObjectPool {

    private GenericObjectPool<Connection> connections = new GenericObjectPool<Connection>(new ConnectionFactory());
    
    @Test
    public void pooledConnectionTest() throws Exception {
        Connection connection = null;
        try {
            connection = connections.borrowObject();
            assertTrue(connection.createStatement().execute("SELECT 1"));
            assertNotNull(connections.borrowObject());
        } finally {
            connections.returnObject(connection);
        }
    }

    public static class ConnectionFactory extends BasePooledObjectFactory<Connection> {

        private EmbeddedPostgres pg;

        public ConnectionFactory() {
            try {
                pg = EmbeddedPostgres.start();
            } catch (IOException e) {
                // handle this ...
            }
        }

        @Override
        public Connection create() {
            try {
                return pg.getPostgresDatabase().getConnection();
            } catch (SQLException e) {
                throw new IllegalStateException("could not create connection to memory postres", e);
            }
        }

        @Override
        public PooledObject<Connection> wrap(Connection connection) {
            return new DefaultPooledObject<Connection>(connection);
        }

        @Override
        public void passivateObject(PooledObject<Connection> pooledObject) {
            // nothing to do
        }

    }
}
