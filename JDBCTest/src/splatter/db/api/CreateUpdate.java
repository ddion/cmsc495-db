// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/28/2011
// Package:  splatter.db.api
// File:     CreateUpdate.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.CREATE_UPDATE
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.CREATE_UPDATE function.
 * 
 * @author msongy
 */
public class CreateUpdate
        extends SplatterCallableStatement {

    /**
     * Constructs a <code>CreateUpdate</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public CreateUpdate(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.CREATE_UPDATE(?, ?, ?) }"));
        ((CallableStatement)statement).registerOutParameter(1, Types.BIGINT);
    }
    
    /**
     * Calls the SPLATTER_API.CREATE_UPDATE function.
     * 
     * @param userId id of the creating user
     * @param authToken auth session token for <code>userId</code>
     * @param message the update message
     * @return id of the new update
     * @throws SQLException if there is a database error
     */
    public long call(
            long userId,
            String authToken,
            String message)
            throws SQLException {
        statement.setLong(2, userId);
        statement.setString(3, authToken);
        statement.setString(4, message);
        statement.executeUpdate();
        return ((CallableStatement)statement).getLong(1);
    }
}