// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     ViewReceivedConnectionRequests.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a
//           SPLATTER_API.VIEW_RECEIVED_CONNECTION_REQUESTS stored function call for
//           the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import splatter.db.LimitedResultSet;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.VIEW_RECEIVED_CONNECTION_REQUESTS function.
 * 
 * @author msongy
 */
public class ViewReceivedConnectionRequests
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
         * @param username username of the sender
         * @param first first name of the sender
         * @param mi middle initial of the sender
         * @param last last name of the sender
         * @param namePrivacy true if the sender's name is returned;
         *        false otherwise
         * @param email the sender's email
         * @param emailPrivacy true if the senders's email is returned;
         *        false otherwise
         * @param receiverFollowingSender <code>true</code> if the receiver is
         *        following the sender; <code>false</code> otherwise
         * @param createdTime time the connection request was created
         * @param message the sender's message to the receiver
         */
        void visit(
                long requestId,
                long senderId, String username,
                String first, String mi, String last, boolean namePrivacy,
                String email, boolean emailPrivacy,
                boolean receiverFollowingSender,
                Timestamp createdTime, String message);
    }
    
    /**
     * Constructs a <code>ViewReceivedConnectionRequests</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public ViewReceivedConnectionRequests(Connection connection)
            throws SQLException {
        super(connection.prepareStatement(
                "select * from SPLATTER_API.VIEW_RECEIVED_CONNECTION_REQUESTS(?, ?, ?, ?)"));
    }
    
    /**
     * Calls the SPLATTER_API.VIEW_RECEIVED_CONNECTION_REQUESTS function.
     * 
     * @param receiverId id of the receiving user
     * @param authToken auth session token for <code>receiverId</code>
     * @param offset offset of first record to retrieve, starting from 0
     * @param count number of connection requests to retrieve starting from
     *        <code>offset</code>; 0 to retrieve all updates
     * @throws SQLException if there is a database error
     */
    public LimitedResultSet call(
            long receiverId,
            String authToken,
            int offset, int count)
            throws SQLException {
        statement.setLong(1, receiverId);
        statement.setString(2, authToken);
        statement.setInt(3, offset);
        if (count <= 0) {
            statement.setNull(4, Types.INTEGER);
        } else {
            statement.setInt(4, count);
        }
        statement.execute();
        return new LimitedResultSet(
                statement.executeQuery());
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
            resultSet.getLong(1),       // requestId
            resultSet.getLong(2),       // senderId
            resultSet.getString(3),     // username
            resultSet.getString(4),     // first
            resultSet.getString(5),     // mi
            resultSet.getString(6),     // last
            resultSet.getBoolean(7),    // namePrivacy
            resultSet.getString(8),     // email
            resultSet.getBoolean(9),    // emailPrivacy
            resultSet.getBoolean(10),    // receiverFollowingSender
            resultSet.getTimestamp(11), // createdTime
            resultSet.getString(12));   // message
    }
}