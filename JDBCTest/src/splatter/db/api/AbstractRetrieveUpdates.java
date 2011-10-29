// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db.api
// File:     AbstractRetrieveUpdates.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a base class for retrieve updates stored function call for
//           the Splatter database.
package splatter.db.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import splatter.db.SplatterCallableStatement;

/**
 * Base class for retrieve updates callable statements
 * 
 * @author msongy
 */
public abstract class AbstractRetrieveUpdates
        extends SplatterCallableStatement {
    
    /**
     * Interface for visiting a row of the result set returned by
     * a retrieve updates function.
     */
    public interface RowVisitor
    {
        /**
         * Called for a visited row.
         * 
         * @param updateId update id
         * @param userId id of the owning user
         * @param createdTime time the update record was created
         * @param updatedTime time the update record was last updated
         * @param posted <code>true</code> if the update has been posted;
         *        <code>false</code> otherwise
         * @param message the update message
         */
        void visit(
                long updateId, long userId,
                Timestamp createdTime, Timestamp updatedTime,
                boolean posted, String message);
    }
    
    /**
     * Constructs a <code>AbstractRetreiveUpdates</code> statement.
     * 
     * @param statement JDBC prepared statement
     * @throws SQLException if there is a database error
     */
    public AbstractRetrieveUpdates(PreparedStatement statement)
            throws SQLException {
        super(statement);
    }
    
    /**
     * Visits the current row of the supplied result set.
     * 
     * @param resultSet the result set whose current row will be visited
     * @param visitor the visitor callback
     * @throws SQLException if there is a database error
     */
    public void visitRow(ResultSet resultSet, RowVisitor visitor)
            throws SQLException {
        visitor.visit(
            resultSet.getLong(1),      // updateId
            resultSet.getLong(2),      // userId
            resultSet.getTimestamp(3), // createdTime
            resultSet.getTimestamp(4), // updatedTime
            resultSet.getBoolean(5),   // posted
            resultSet.getString(6));   // message
    }
}
