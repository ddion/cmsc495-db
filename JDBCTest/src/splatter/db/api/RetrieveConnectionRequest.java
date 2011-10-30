// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/27/2011
// Package:  splatter.db.api
// File:     RetrieveConnectionRequest.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a
//           SPLATTER_API.RETRIEVE_CONNECTION_REQUEST stored function call for
//           the Splatter database.
package splatter.db.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.RETRIEVE_CONNECTION_REQUEST function.
 * 
 * @author msongy
 */
public class RetrieveConnectionRequest
        extends SplatterCallableStatement {
    
    /**
     * Interface for visiting a row of the result set returned by
     * <code>call</code>.
     * 
     * @see #call(long, String)
     */
    public interface RowVisitor {
        /**
         * Called for a visited row.
         * 
         * @param requestId request id
         * @param senderId id of the user who sent the request
         * @param receiverId id of the user who received the request
         * @param createdTime time the user record was created
         * @param message the sender's message to the receiver
         */
        void visit(
                long requestId,
                long senderId, long receiverId,
                Timestamp createdTime, String message);
    }
    
    /**
     * Constructs a <code>RetrieveConnectionRequest</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public RetrieveConnectionRequest(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.RETRIEVE_CONNECTION_REQUEST(?, ?, ?) }"));
        ((CallableStatement)statement).registerOutParameter(1, Types.OTHER);
    }
    
    /**
     * Calls the SPLATTER_API.RETRIEVE_CONNECTION_REQUEST function.
     * 
     * @param userId id of the user to log out
     * @param authToken auth session token for <code>userId</code>
     * @param requestId id of the request to retrieve
     * @throws SQLException if there is a database error
     */
    public ResultSet call(
            long userId,
            String authToken,
            long requestId)
            throws SQLException {
        statement.setLong(2, userId);
        statement.setString(3, authToken);
        statement.setLong(4, requestId);
        statement.execute();
        return (ResultSet)((CallableStatement)statement).getObject(1);
    }
    
    /**
     * Visits the current row of the supplied result set.
     * The result set must have been returned from a call to
     * <code>call</code>
     * 
     * @param resultSet the result set whose current row will be visited
     * @param visitor the visitor callback
     * @throws SQLException if there is a database error
     * @see #call(long, String)
     */
    public void visitRow(ResultSet resultSet, RowVisitor visitor)
            throws SQLException {
        visitor.visit(
            resultSet.getLong(1),      // requestId
            resultSet.getLong(2),      // senderId
            resultSet.getLong(3),      // receiverId
            resultSet.getTimestamp(4), // createdTime
            resultSet.getString(5));   // message
    }
}
