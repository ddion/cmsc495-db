// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/27/2011
// Package:  splatter.db.api
// File:     ViewUser.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.VIEW_USER
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.VIEW_USER function.
 * 
 * @author msongy
 */
public class ViewUser
        extends SplatterCallableStatement {
    
    /**
     * Interface for visiting a row of the result set returned by
     * <code>call</code>.
     * 
     * @see #call(long, String)
     */
    public interface RowVisitor
    {
        /**
         * Called for a visited row.
         * 
         * @param id user id
         * @param username the user's username
         * @param password the user's password
         * @param first the user's first name
         * @param mi the user's middle initial
         * @param last the user's last name
         * @param namePrivacy true if the user's name is returned;
         *        false otherwise
         * @param email the user's email
         * @param emailPrivacy true if the user's email is returned;
         *        false otherwise
         * @param viewerFollowingUser true if the viewer is following the user;
         *        false otherwise
         * @param userFollowingViewer true if the user is following the viewer;
         *        false otherwise
         */
        void visit(
                long id, String username,
                String first, String mi, String last, boolean namePrivacy,
                String email, boolean emailPrivacy,
                boolean viewerFollowingUser, boolean userFollowingViewer);
    }
    
    /**
     * Constructs a <code>ViewUser</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public ViewUser(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.VIEW_USER(?, ?, ?) }"));
        statement.registerOutParameter(1, Types.OTHER);
    }
    
    /**
     * Calls the SPLATTER_API.VIEW_USER function.
     * 
     * @param viewerId id of the searching user
     * @param authToken auth session token for <code>viewerId</code>
     * @param userId id of the user to view
     * @throws SQLException if there is a database error
     */
    public ResultSet call(
            long viewerId,
            String authToken,
            long userId)
            throws SQLException {
        statement.setLong(2, viewerId);
        statement.setString(3, authToken);
        statement.setLong(4, userId);
        statement.execute();
        return (ResultSet)statement.getObject(1);
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
            resultSet.getLong(1),      // id
            resultSet.getString(2),    // username
            resultSet.getString(3),    // first
            resultSet.getString(4),    // mi
            resultSet.getString(5),    // last
            resultSet.getBoolean(6),   // namePrivacy
            resultSet.getString(7),    // email
            resultSet.getBoolean(8),   // emailPrivacy
            resultSet.getBoolean(9),   // viewerFollowingUser
            resultSet.getBoolean(10)); // userFollowingViewer
    }
}

