// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  jdbctest
// File:     SplatterDBConnection.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a connection to the Splatter
//           database.
package splatter.db;

import java.sql.Connection;
import java.sql.SQLException;
import splatter.db.api.CreateUser;

/**
 * A connection to the Splatter database.
 * 
 * @author msongy
 */
public class SplatterConnection
        implements AutoCloseable {
    
    /**
     * Creates a new <code>CreateUser</code> prepared statement.
     * 
     * @return new prepared statement
     * @throws SQLException if there is an error preparing the statement
     */
    public CreateUser getCreateUser()
            throws SQLException {
        return new CreateUser(connection);
    }
    
    /**
     * Returns the underlying JDBC <code>Connection</code> object.
     * 
     * @return the wrapped JDBC connection
     */
    public Connection getConnection() {
        return connection;
    }
    
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
    SplatterConnection(Connection connection)
            throws IllegalArgumentException {
        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        this.connection = connection;
    }
    
    private final Connection connection;
}
