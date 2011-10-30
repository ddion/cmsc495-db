// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     RejectConnectionRequest.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a
//           SPLATTER_API.REJECT_CONNECTION_REQUEST stored function call for the
//           Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.REJECT_CONNECTION_REQUEST function.
 * 
 * @author msongy
 */
public class RejectConnectionRequest
        extends SplatterCallableStatement {

    /**
     * Constructs a <code>RejectConnectionRequest</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public RejectConnectionRequest(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ call SPLATTER_API.REJECT_CONNECTION_REQUEST(?, ?, ?) }"));
    }
    
    /**
     * Calls the SPLATTER_API.REJECT_CONNECTION_REQUEST function.
     * 
     * @param userId id of the user rejecting the request
     * @param authToken auth session token for <code>userId</code>
     * @param requestId id of the connection request to reject
     * @throws SQLException if there is a database error
     */
    public void call(
            long userId,
            String authToken,
            long requestId)
            throws SQLException {
        statement.setLong(1, userId);
        statement.setString(2, authToken);
        statement.setLong(3, requestId);
        statement.executeUpdate();        
    }
}