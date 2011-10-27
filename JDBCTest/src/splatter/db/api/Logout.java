// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/27/2011
// Package:  splatter.db.api
// File:     Logout.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.LOGOUT
//           stored function call for the Splatter database.

package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.LOGOUT function.
 * @author msongy
 */
public class Logout
        extends SplatterCallableStatement {
    
    /**
     * Constructs a <code>Logout</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public Logout(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ call SPLATTER_API.LOGOUT(?, ?) }"));
    }
    
    /**
     * Calls the SPLATTER_API.LOGOUT function.
     * 
     * @param userId id of the user to log out
     * @param authToken auth session token for <code>userId</code>
     * @throws SQLException if there is a database error
     */
    public void call(
            long userId,
            String authToken)
            throws SQLException {
        statement.setLong(1, userId);
        statement.setString(2, authToken);
        statement.executeUpdate();        
    }
}
