// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db.test
// File:     SplatterDBCallableStatement.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_TEST.RESET
//           stored function call for the Splatter database.
package splatter.db.test;

import java.sql.Connection;
import java.sql.SQLException;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_TEST.RESET function.
 * 
 * @author msongy
 */
public class Reset
        extends SplatterCallableStatement {

    /**
     * Constructs a <code>Reset</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public Reset(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ call SPLATTER_TEST.RESET() }"));
    }
    
    /**
     * Calls the SPLATTER_TEST.RESET function.
     * 
     * @throws SQLException if there is a database error
     */
    public void call()
            throws SQLException {
        statement.executeUpdate();        
    }
}
