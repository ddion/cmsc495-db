// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     RetrieveUpdates.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.RETRIEVE_UPDATES
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.LimitedResultSet;

/**
 * A prepared call to the SPLATTER_API.RETRIEVE_UPDATES function.
 * 
 * @author msongy
 */
public class RetrieveUpdates
        extends AbstractRetrieveUpdates {
    
    /**
     * Constructs a <code>RetrieveUpdates</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public RetrieveUpdates(Connection connection)
            throws SQLException {
        super(connection.prepareStatement(
                "select * from SPLATTER_API.RETRIEVE_UPDATES(?, ?, ?, ?)"));
    }
    
    /**
     * Calls the SPLATTER_API.RETRIEVE_UPDATES function.
     * 
     * @param userId id of the user who owns the update
     * @param authToken auth session token for <code>userId</code>
     * @param offset offset of first record to retrieve, starting from 0
     * @param count number of updates to retrieve starting from
     *        <code>offset</code>; 0 to retrieve all updates
     * @throws SQLException if there is a database error
     */
    public LimitedResultSet call(
            long userId,
            String authToken,
            int offset, int count)
            throws SQLException {
        statement.setLong(1, userId);
        statement.setString(2, authToken);
        statement.setInt(3, offset);
        if (count <= 0) {
            statement.setNull(4, Types.INTEGER);
        } else {
            statement.setInt(4, count);
        }
        statement.execute();
        return new LimitedResultSet(
                statement.executeQuery());
    }
}