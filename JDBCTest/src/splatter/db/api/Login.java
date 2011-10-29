// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db.api
// File:     Login.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a class that represents a SPLATTER_API.LOGIN
//           stored function call for the Splatter database.
package splatter.db.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import splatter.db.SplatterCallableStatement;

/**
 * A prepared call to the SPLATTER_API.LOGIN function.
 * 
 * @author msongy
 */
public class Login
        extends SplatterCallableStatement {

    /**
     * Results from a login call.
     */
    public static class Results {
        
        /**
         * Constructs login results.
         * 
         * @param id user id
         * @param authToken session token used for authentication
         */
        private Results(long id, String authToken) {
            this.id = id;
            this.authToken = authToken;
        }
        
        /**
         * Retrieves the user id.
         * 
         * @return user id
         */
        public long getId() {
            return id;
        }
        
        /**
         * Retrieves the authentication session token.
         * 
         * @return authentication session token
         */
        public String getAuthToken() {
            return authToken;
        }
        
        /** Session user id */
        private long id;
        /** Session authentication token */
        private String authToken;
    }
    
    /**
     * Constructs a <code>Login</code> statement.
     * 
     * @param connection JDBC connection
     * @throws SQLException if there is an error preparing the statement
     */
    public Login(Connection connection)
            throws SQLException {
        super(connection.prepareCall(
                "{ call SPLATTER_API.LOGIN(?, ?, ?, ?) }"));
        CallableStatement callableStatement = ((CallableStatement)statement);
        callableStatement.registerOutParameter(1, Types.BIGINT);
        callableStatement.registerOutParameter(2, Types.VARCHAR);
    }
    
    /**
     * Calls the SPLATTER_API.LOGIN function.
     * 
     * @param username username of the user to log in
     * @param password password of the user to log in
     * @return results containing a user id and session authentication token
     * @throws SQLException if there is a database error
     */
    public Results call(
            String username,
            String password)
            throws SQLException {
        statement.setString(3, username);
        statement.setString(4, password);
        statement.executeUpdate();
        CallableStatement callableStatement = (CallableStatement)statement;
        long id = callableStatement.getLong(1);
        String authToken = callableStatement.getString(2);
        return new Results(id, authToken);
    }
}
