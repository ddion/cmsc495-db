// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/29/2011
// Package:  splatter.db
// File:     LimitedResultSet.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that contains a result set and total row count
//           count for functions that limit results for pagination.
package splatter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A limited result set with a total row count.
 * Used for functions that allow limiting results for pagination.
 * 
 * @author msongy
 */
public class LimitedResultSet
        implements AutoCloseable {
    
    /**
     * Constructs a prepared call.
     * 
     * @param call the JDBC prepared call to wrap
     * @throws IllegalArgumentException if <code>connection</code> is null
     */
    public LimitedResultSet(ResultSet outerResultSet)
            throws IllegalArgumentException, SQLException {
        if (outerResultSet == null) {
            throw new IllegalArgumentException("resultSet is null");
        }
        
        this.outerResultSet = outerResultSet;
        
        // Get the count
        outerResultSet.next();
        try (ResultSet countResultSet = (ResultSet)outerResultSet.getObject(1)) {
            countResultSet.next();
            this.total = countResultSet.getInt(1);
        }
        
        // Get the results
        outerResultSet.next();
        this.resultSet = (ResultSet)outerResultSet.getObject(1);
    }
    
    /**
     * Returns the result set.
     * 
     * @return the result set
     */
    public ResultSet getResultSet() {
        return resultSet;
    }
    
    /**
     * Returns the total row count for pagination.
     * 
     * @return the total row count
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Closes the result set, releasing all resources.
     * 
     * @throws Exception if the connection is already closed
     */
    public void close() throws Exception {
        resultSet.close();
        outerResultSet.close();
    }
    
    /** The outer JDBC result set */
    private final ResultSet outerResultSet;
    
    /** The JDBC result set */
    private final ResultSet resultSet;
    
    /** The total row count */
    private final int total;
}
