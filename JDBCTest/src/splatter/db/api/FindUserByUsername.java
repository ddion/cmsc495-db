// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/28/2011
// Package:  splatter.db.api
// File:     FindUserByUsername.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.FIND_USER_BY_USERNAME
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * A prepared call to the SPLATTER_API.FIND_USER_BY_USERNAME function.
 * 
 * @author msongy
 */
public class FindUserByUsername
        extends FindUser {

    /**
     * Constructs a <code>FindUserByUsername</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public FindUserByUsername(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.FIND_USER_BY_USERNAME(?, ?, ?) }"));
        statement.registerOutParameter(1, Types.OTHER);
    }
    
    /**
     * Calls the SPLATTER_API.FIND_USER_BY_USERNAME function.
     * 
     * @param viewerId id of the searching user
     * @param authToken auth session token for <code>viewerId</code>
     * @param username username of the user to find
     * @returns a result set
     * @throws SQLException if there is a database error
     */
    public ResultSet call(
            long viewerId,
            String authToken,
            String username)
            throws SQLException {
        statement.setLong(2, viewerId);
        statement.setString(3, authToken);
        statement.setString(4, username);
        statement.execute();
        return (ResultSet)statement.getObject(1);
    }
}
