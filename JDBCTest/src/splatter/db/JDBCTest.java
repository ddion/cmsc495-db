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
                        Login login = new Login(c)) {
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
                    c.commit();
                }               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    
}
