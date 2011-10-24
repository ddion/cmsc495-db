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

import splatter.db.api.CreateUser;

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
                    SplatterConnection c = db.connect("splatter_test", "T3st5pl@t7er!")) {
                System.out.println("Yay, we connected!");
                c.getConnection().setAutoCommit(false);
                try (
                        CreateUser cu = c.getCreateUser()) {
                    long id = cu.call(
                            "msongy1", "ween69",
                            "Michael", "P", "Songy", Splatter.AccessLevel.ALL,
                            "msongy@sbcglobal.net", Splatter.AccessLevel.ALL);
                    System.out.printf("Added user %d%n", id);
                    id = cu.call(
                            "wsongy", "lovely",
                            "Wanda", "B", "Songy", Splatter.AccessLevel.ALL,
                            "wsongy@att.net", Splatter.AccessLevel.ALL);
                    System.out.printf("Added user %d%n", id);
                    c.getConnection().commit();
                }               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    
}
