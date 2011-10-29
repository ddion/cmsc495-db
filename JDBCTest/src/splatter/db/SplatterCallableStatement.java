// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db
// File:     SplatterCallableStatement.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a callable statement for the
//           Splatter database.
package splatter.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;

/**
 * A callable statement for the Splatter database.
 * 
 * @author msongy
 */
public class SplatterCallableStatement
        implements AutoCloseable {
    
    /**
     * Closes the statement, releasing all resources.
     * 
     * @throws Exception if the connection is already closed
     */
    public void close() throws Exception {
        statement.close();
    }
    
    /**
     * Constructs a prepared call.
     * 
     * @param call the JDBC prepared statement to wrap
     * @throws IllegalArgumentException if <code>connection</code> is null
     */
    protected SplatterCallableStatement(PreparedStatement statement)
            throws IllegalArgumentException {
        if (statement == null) {
            throw new IllegalArgumentException("statement is null");
        }
        this.statement = statement;
    }
    
    /** The JDBC callable statement */
    protected final PreparedStatement statement;
}
