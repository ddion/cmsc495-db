// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     ViewSentConnectionRequests.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a
//           SPLATTER_API.VIEW_SENT_CONNECTION_REQUESTS stored function call for
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
 * A prepared call to the SPLATTER_API.VIEW_SENT_CONNECTION_REQUESTS function.
 * 
 * @author msongy
 */
public class ViewSentConnectionRequests
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
         * @param receiverId id of the user who received the request
         * @param username username of the receiver
         * @param first first name of the receiver
         * @param mi middle initial of the receiver
         * @param last last name of the receiver
         * @param namePrivacy true if the receiver's name is returned;
         *        false otherwise
         * @param email the receiver's email
         * @param emailPrivacy true if the receivers's email is returned;
         *        false otherwise
         * @param senderFollowingReceiver <code>true</code> if the sender is
         *        following the receiver; <code>false</code> otherwise
         * @param receiverFollowingSender <code>true</code> if the receiver is
         *        following the sender; <code>false</code> otherwise
         * @param createdTime time the connection request was created
         * @param message the receiver's message to the receiver
         */
        void visit(
                long requestId,
                long receiverId, String username,
                String first, String mi, String last, boolean namePrivacy,
                String email, boolean emailPrivacy,
                boolean senderFollowingReceiver,
                boolean receiverFollowingSender,
                Timestamp createdTime, String message);
    }
    
    /**
     * Constructs a <code>ViewSentConnectionRequests</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public ViewSentConnectionRequests(Connection connection)
            throws SQLException {
        super(connection.prepareStatement(
                "select * from SPLATTER_API.VIEW_SENT_CONNECTION_REQUESTS(?, ?, ?, ?)"));
    }
    
    /**
     * Calls the SPLATTER_API.VIEW_SENT_CONNECTION_REQUESTS function.
     * 
     * @param senderId id of the sending user
     * @param authToken auth session token for <code>senderId</code>
     * @param offset offset of first record to retrieve, starting from 0
     * @param count number of connection requests to retrieve starting from
     *        <code>offset</code>; 0 to retrieve all updates
     * @throws SQLException if there is a database error
     */
    public LimitedResultSet call(
            long senderId,
            String authToken,
            int offset, int count)
            throws SQLException {
        statement.setLong(1, senderId);
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
            resultSet.getLong(2),       // receiverId
            resultSet.getString(3),     // username
            resultSet.getString(4),     // first
            resultSet.getString(5),     // mi
            resultSet.getString(6),     // last
            resultSet.getBoolean(7),    // namePrivacy
            resultSet.getString(8),     // email
            resultSet.getBoolean(9),    // emailPrivacy
            resultSet.getBoolean(10),   // senderFollowingReceiver
            resultSet.getBoolean(11),   // receiverFollowingSender
            resultSet.getTimestamp(12), // createdTime
            resultSet.getString(13));   // message
    }
}
