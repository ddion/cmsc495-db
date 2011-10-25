// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db.api
// File:     CreateUser.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.CREATE_USER
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.CREATE_USER function.
 * 
 * @author msongy
 */
public class CreateUser
        extends SplatterCallableStatement {

    /**
     * Constructs a <code>CreateUser</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public CreateUser(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.CREATE_USER(?, ?, ?, ?, ?, ?::SPLATTER_API.ACCESS_LEVEL, ?, ?::SPLATTER_API.ACCESS_LEVEL) }"));
        statement.registerOutParameter(1, Types.BIGINT);
    }
    
    /**
     * Calls the SPLATTER_API.CREATE_USER function.
     * 
     * @param username username of the new user
     * @param password password for the new user
     * @param first first name of the new user; may be null
     * @param mi middle initial of the new user; may be null
     * @param last last name of the new user; may be null
     * @param namePrivacy access level of the new user's name; may be null
     * @param email email address of the new user
     * @param emailPrivacy access level of the new user's email address
     * @return id of the new user
     * @throws SQLException if there is a database error
     */
    public long call(
            String username,
            String password,
            String first,
            String mi,
            String last,
            AccessLevel namePrivacy,
            String email,
            AccessLevel emailPrivacy)
            throws SQLException {
        statement.setString(2, username);
        statement.setString(3, password);
        statement.setString(4, first);
        statement.setString(5, mi);
        statement.setString(6, last);
        statement.setString(7, namePrivacy.name());
        statement.setString(8, email);
        statement.setString(9, namePrivacy.name());
        statement.executeUpdate();
        return statement.getLong(1);
    }
}
