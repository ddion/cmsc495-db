/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctest;

import java.sql.Connection;

/**
 *
 * @author msongy
 */
public class SplatterDBConnection
        implements AutoCloseable{
    
    SplatterDBConnection(Connection connection)
            throws IllegalArgumentException {
        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        this.connection = connection;
    }
    
    public void close() throws Exception {
        connection.close();
    }
    
    private final Connection connection;
}
