// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     RetrieveUpdate.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.RETRIEVE_UPDATES
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * A prepared call to the SPLATTER_API.RETRIEVE_UPDATE function.
 * 
 * @author msongy
 */
public class RetrieveUpdate
        extends AbstractRetrieveUpdates {
    
    /**
     * Constructs a <code>RetrieveUpdate</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public RetrieveUpdate(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ ? = call SPLATTER_API.RETRIEVE_UPDATE(?, ?, ?) }"));
        ((CallableStatement)statement).registerOutParameter(1, Types.OTHER);
    }
    
    /**
     * Calls the SPLATTER_API.RETRIEVE_UPDATE function.
     * 
     * @param userId id of the user who owns the update
     * @param authToken auth session token for <code>userId</code>
     * @param updateId id of the update to retrieve
     * @throws SQLException if there is a database error
     */
    public ResultSet call(
            long userId,
            String authToken,
            long updateId)
            throws SQLException {
        statement.setLong(2, userId);
        statement.setString(3, authToken);
        statement.setLong(4, updateId);
        statement.execute();
        return (ResultSet)((CallableStatement)statement).getObject(1);
    }    
}
