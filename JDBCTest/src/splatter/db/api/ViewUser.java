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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * A prepared call to the SPLATTER_API.VIEW_USER function.
 * 
 * @author msongy
 */
public class ViewUser
        extends AbstractFindUser {
    
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
        ((CallableStatement)statement).registerOutParameter(1, Types.OTHER);
    }
    
    /**
     * Calls the SPLATTER_API.VIEW_USER function.
     * 
     * @param viewerId id of the searching user
     * @param authToken auth session token for <code>viewerId</code>
     * @param userId id of the user to view
     * @returns a result set
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
        return (ResultSet)((CallableStatement)statement).getObject(1);
    }
}
