// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/27/2011
// Package:  splatter.db.api
// File:     AbstractFindUser.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a base class for find user stored function call for
//           the Splatter database.
package splatter.db.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import splatter.db.SplatterCallableStatement;

/**
 * Base class for find user callable statements
 * 
 * @author msongy
 */
public abstract class AbstractFindUser
        extends SplatterCallableStatement {
    
    /**
     * Interface for visiting a row of the result set returned by
     * a find user function.
     */
    public interface RowVisitor
    {
        /**
         * Called for a visited row.
         * 
         * @param id user id
         * @param username the user's username
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
     * Constructs a <code>AbstractFindUser</code> statement.
     * 
     * @param statement JDBC prepared statement
     * @throws SQLException if there is a database error
     */
    public AbstractFindUser(PreparedStatement statement)
            throws SQLException {
        super(statement);
    }
    
    /**
     * Visits the current row of the supplied result set.
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