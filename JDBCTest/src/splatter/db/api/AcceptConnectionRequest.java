// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     AcceptConnectionRequest.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a
//           SPLATTER_API.ACCEPT_CONNECTION_REQUEST stored function call for the
//           Splatter database.
package splatter.db.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.ACCEPT_CONNECTION_REQUEST function.
 * 
 * @author msongy
 */
public class AcceptConnectionRequest
        extends SplatterCallableStatement {

    /**
     * Constructs a <code>AcceptConnectionRequest</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public AcceptConnectionRequest(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.ACCEPT_CONNECTION_REQUEST(?, ?, ?) }"));
        ((CallableStatement)statement).registerOutParameter(1, Types.BIGINT);
    }
    
    /**
     * Calls the SPLATTER_API.ACCEPT_CONNECTION_REQUEST function.
     * 
     * @param userId id of the user accepting the request
     * @param authToken auth session token for <code>userId</code>
     * @param requestId id of the connection request to accept
     * @return id of the new connection
     * @throws SQLException if there is a database error
     */
    public long call(
            long userId,
            String authToken,
            long requestId)
            throws SQLException {
        statement.setLong(2, userId);
        statement.setString(3, authToken);
        statement.setLong(4, requestId);
        statement.executeUpdate();
        return ((CallableStatement)statement).getLong(1);
    }
}