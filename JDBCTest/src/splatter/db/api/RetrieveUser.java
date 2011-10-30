// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/27/2011
// Package:  splatter.db.api
// File:     RetrieveUser.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.RETRIEVE_USER
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.RETRIEVE_USER function.
 * 
 * @author msongy
 */
public class RetrieveUser
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
         * @param userId user userId
         * @param createdTime time the user record was created
         * @param updatedTime time the user record was last updated
         * @param username the user's username
         * @param password the user's password
         * @param first the user's first name
         * @param mi the user's middle initial
         * @param last the user's last name
         * @param namePrivacy the user's name privacy setting
         * @param email the user's email
         * @param emailPrivacy  the user's email privacy setting
         */
        void visit(
                long userId,
                Timestamp createdTime, Timestamp updatedTime,
                String username, String password,
                String first, String mi, String last, AccessLevel namePrivacy,
                String email, AccessLevel emailPrivacy);
    }
    
    /**
     * Constructs a <code>RetrieveUser</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public RetrieveUser(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.RETRIEVE_USER(?, ?) }"));
        ((CallableStatement)statement).registerOutParameter(1, Types.OTHER);
    }
    
    /**
     * Calls the SPLATTER_API.RETRIEVE_USER function.
     * 
     * @param userId id of the user to log out
     * @param authToken auth session token for <code>userId</code>
     * @throws SQLException if there is a database error
     */
    public ResultSet call(
            long userId,
            String authToken)
            throws SQLException {
        statement.setLong(2, userId);
        statement.setString(3, authToken);
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
            resultSet.getLong(1),      // userId
            resultSet.getTimestamp(2), // createdTime
            resultSet.getTimestamp(3), // updatedTime
            resultSet.getString(4),    // username
            resultSet.getString(5),    // password
            resultSet.getString(6),    // first
            resultSet.getString(7),    // mi
            resultSet.getString(8),    // last
            AccessLevel.fromString(    // namePrivacy
                resultSet.getString(9)),
            resultSet.getString(10),   // email
            AccessLevel.fromString(    // emailPrivacy
                resultSet.getString(11)));
    }
}
