// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     PostUpdate.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.POST_UPDATE
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.POST_UPDATE function.
 * 
 * @author msongy
 */
public class PostUpdate
        extends SplatterCallableStatement {

    /**
     * Constructs a <code>PostUpdate</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public PostUpdate(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ call SPLATTER_API.POST_UPDATE(?, ?, ?) }"));
    }
    
    /**
     * Calls the SPLATTER_API.POST_UPDATE function.
     * 
     * @param userId id of the user posting the update
     * @param authToken auth session token for <code>userId</code>
     * @param updateId id of the update to post
     * @throws SQLException if there is a database error
     */
    public void call(
            long userId,
            String authToken,
            long updateId)
            throws SQLException {
        statement.setLong(1, userId);
        statement.setString(2, authToken);
        statement.setLong(3, updateId);
        statement.executeUpdate();        
    }
}
