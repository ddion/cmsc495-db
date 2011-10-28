// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/27/2011
// Package:  splatter.db.api
// File:     UpdateUser.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.UPDATE_USER
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.UPDATE_USER function.
 * 
 * @author msongy
 */

public class UpdateUser
        extends SplatterCallableStatement {
    
    /**
     * Constructs a <code>UpdateUser</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public UpdateUser(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ call SPLATTER_API.UPDATE_USER(?, ?, ?, ?, ?, ?, ?, ?::SPLATTER_API.ACCESS_LEVEL, ?, ?::SPLATTER_API.ACCESS_LEVEL) }"));        
    }
    
    /**
     * Calls the SPLATTER_API.CREATE_USER function.
     * 
     * @param userId id of the user to update
     * @parame authToken auth session token for <code>userId</code>
     * @param username username of the new user
     * @param password password for the new user
     * @param first first name of the new user; may be null
     * @param mi middle initial of the new user; may be null
     * @param last last name of the new user; may be null
     * @param namePrivacy access level of the new user's name; may be null
     * @param email email address of the new user
     * @param emailPrivacy access level of the new user's email address
     * @throws SQLException if there is a database error
     */
    public void call(
            long userId,
            String authToken,
            String username,
            String password,
            String first,
            String mi,
            String last,
            AccessLevel namePrivacy,
            String email,
            AccessLevel emailPrivacy)
            throws SQLException {
        statement.setLong(1, userId);
        statement.setString(2, authToken);
        statement.setString(3, username);
        statement.setString(4, password);
        statement.setString(5, first);
        statement.setString(6, mi);
        statement.setString(7, last);
        statement.setString(8, namePrivacy.name());
        statement.setString(9, email);
        statement.setString(10, emailPrivacy.name());
        statement.executeUpdate();
    }
    
}
