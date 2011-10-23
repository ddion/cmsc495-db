// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Package:  jdbctest
// File:     SplatterDBConnection.java
// Platform: JDK 7
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Blah.
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
