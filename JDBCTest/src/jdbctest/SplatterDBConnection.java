// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Package:  jdbctest
// File:     SplatterDBConnection.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a connection to the Splatter
//           database.
package jdbctest;

import java.sql.Connection;

/**
 * Class representing a connection to the Splatter database.
 * 
 * @author msongy
 */
public class SplatterDBConnection
        implements AutoCloseable{
    
    /**
     * Closes the database connection, releasing all resources.
     * 
     * @throws Exception if the connection is already closed
     */
    public void close() throws Exception {
        connection.close();
    }
    
    /**
     * Constructs a connection to the Splatter database
     * 
     * @param connection the JDBC connection to wrap
     * @throws IllegalArgumentException if <code>connection</code> is null
     */
    SplatterDBConnection(Connection connection)
            throws IllegalArgumentException {
        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        this.connection = connection;
    }
    
    private final Connection connection;
}
