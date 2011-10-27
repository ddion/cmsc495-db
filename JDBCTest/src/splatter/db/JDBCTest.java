// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db
// File:     JDBCTest.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a JDBCTest class containing the main entry point of
//           the application.
package splatter.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import splatter.db.api.*;
import splatter.db.test.Reset;

/**
 * The main entry point of the application.
 * 
 * @author msongy
 */
public class JDBCTest {

    /**
     * Main entry point of the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Splatter db = Splatter.getInstance();
            try (
                    Connection c = db.connect("splatter_test", "T3st5pl@t7er!")) {
                System.out.println("Yay, we connected!");
                c.setAutoCommit(false);
                try (
                        Reset reset = new Reset(c);
                        CreateUser createUser = new CreateUser(c);
                        Login login = new Login(c);
                        RetrieveUser retrieveUser = new RetrieveUser(c);
                        Logout logout = new Logout(c)) {
                    reset.call();
                    long id = createUser.call(
                            "msongy", "waxon!",
                            "Michael", "P", "Songy", AccessLevel.ALL,
                            "msongy@sbcglobal.net", AccessLevel.ALL);
                    System.out.printf("Added user %d%n", id);
                    id = createUser.call(
                            "wsongy", "waxoff!",
                            "Wanda", "B", "Songy", AccessLevel.FOLLOWERS,
                            "wsongy@sbcglobal.net", AccessLevel.FOLLOWERS);
                    System.out.printf("Added user %d%n", id);
                    Login.Results r = login.call("wsongy", "waxoff!");
                    System.out.printf(
                            "Logged in user %d, auth token = %s%n",
                            r.getId(), r.getAuthToken());
                    processRetrieveUser(
                            retrieveUser,
                            retrieveUser.call(r.getId(), r.getAuthToken()));
                    logout.call(r.getId(), r.getAuthToken());
                    System.out.printf("Logged out user %d%n", r.getId());
                    c.commit();
                }               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    static void processRetrieveUser(RetrieveUser statement, ResultSet resultSet)
            throws SQLException {
        RetrieveUser.RowVisitor visitor = new RetrieveUser.RowVisitor() {
            public void visit(
                long id,
                Timestamp createdTime,
                Timestamp updatedTime,
                String username,
                String password,
                String first,
                String mi,
                String last,
                AccessLevel namePrivacy,
                String email,
                AccessLevel emailPrivacy) {
                System.out.printf(
                        "Retrieved user:%n" +
                        "  id = %d%n" +
                        "  createdTime = %s%n" +
                        "  updatedTime = %s%n" +
                        "  username = %s%n" +
                        "  password = %s%n" +
                        "  first = %s%n" +
                        "  mi = %s%n" +
                        "  last = %s%n" +
                        "  namePrivacy = %s%n" +
                        "  email = %s%n" +
                        "  emailPrivacy = %s%n",
                        id,
                        createdTime.toString(), updatedTime.toString(),
                        username, password,
                        first, mi, last, namePrivacy.name(),
                        email, emailPrivacy.name());
            }
        };
        
        while (resultSet.next()) {
            statement.visitRow(resultSet, visitor);
        }
    }
}
