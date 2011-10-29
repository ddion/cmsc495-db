// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     ViewUserUpdates.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.VIEW_USER_UPDATES
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.LimitedResultSet;

/**
 * A prepared call to the SPLATTER_API.VIEW_USER_UPDATES function.
 * 
 * @author msongy
 */
public class ViewUserUpdates
        extends AbstractViewUpdates {
    
    /**
     * Constructs a <code>ViewUserUpdates</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public ViewUserUpdates(Connection connection)
            throws SQLException {
        super(connection.prepareStatement(
                "select * from SPLATTER_API.VIEW_USER_UPDATES(?, ?, ?, ?, ?)"));
    }
    
    /**
     * Calls the SPLATTER_API.VIEW_FOLLOWED_UPDATED function.
     * 
     * @param viewerId id of the viewing user
     * @param authToken auth session token for <code>viewerId</code>
     * @param ownerId id of the user whose updates are being viewed
     * @param count number of updates to retrieve starting from
     *        <code>offset</code>; 0 to retrieve all updates
     * @param offset offset of first record to retrieve, starting from 0
     * @throws SQLException if there is a database error
     */
    public LimitedResultSet call(
            long viewerId,
            String authToken,
            long ownerId,
            int offset, int count)
            throws SQLException {
        statement.setLong(1, viewerId);
        statement.setString(2, authToken);
        statement.setLong(3, ownerId);
        statement.setInt(4, offset);
        if (count <= 0) {
            statement.setNull(5, Types.INTEGER);
        } else {
            statement.setInt(5, count);
        }
        statement.execute();
        return new LimitedResultSet(
                statement.executeQuery());
    }
}
