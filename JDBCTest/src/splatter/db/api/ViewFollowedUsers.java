// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     ViewFollowedUsers.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a
//           SPLATTER_API.VIEW_FOLLOWED_USERS stored function call for
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
 * A prepared call to the SPLATTER_API.VIEW_FOLLOWED_USERS function.
 * 
 * @author msongy
 */
public class ViewFollowedUsers
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
         * @param connectionId id of the connection
         * @param userId id of the user being followed
         * @param username username of the user being followed
         * @param first first name of the user being followed
         * @param mi middle initial of the user being followed
         * @param last last name of the user being followed
         * @param namePrivacy true if the user's name is returned;
         *        false otherwise
         * @param email the user's email
         * @param emailPrivacy <code>true</code> if the user's email is
         *        returned; <code>false</code> otherwise
         * @param userFollowingViewer <code>true</code> if the user is
         *        following the viewer; <code>false</code> otherwise
         * @param createdTime time the connection was created
         */
        void visit(
                long connectionId,
                long userId, String username,
                String first, String mi, String last, boolean namePrivacy,
                String email, boolean emailPrivacy,
                boolean userFollowingViewer,
                Timestamp createdTime);
    }
    
    /**
     * Constructs a <code>ViewFollowedUsers</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public ViewFollowedUsers(Connection connection)
            throws SQLException {
        super(connection.prepareStatement(
                "select * from SPLATTER_API.VIEW_FOLLOWED_USERS(?, ?, ?, ?)"));
    }
    
    /**
     * Calls the SPLATTER_API.VIEW_FOLLOWED_USERS function.
     * 
     * @param viewerId id of the viewer
     * @param authToken auth session token for <code>viewerId</code>
     * @param offset offset of first record to retrieve, starting from 0
     * @param count number of connection requests to retrieve starting from
     *        <code>offset</code>; 0 to retrieve all updates
     * @returns a <code>LimitedResultSet</code> containing the results
     * @throws SQLException if there is a database error
     */
    public LimitedResultSet call(
            long viewerId,
            String authToken,
            int offset, int count)
            throws SQLException {
        statement.setLong(1, viewerId);
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
            resultSet.getLong(1),        // connectionId
            resultSet.getLong(2),        // userId
            resultSet.getString(3),      // username
            resultSet.getString(4),      // first
            resultSet.getString(5),      // mi
            resultSet.getString(6),      // last
            resultSet.getBoolean(7),     // namePrivacy
            resultSet.getString(8),      // email
            resultSet.getBoolean(9),     // emailPrivacy
            resultSet.getBoolean(10),    // userFollowingViewer
            resultSet.getTimestamp(11)); // createdTime
    }
}
