// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Package:  jdbctest
// File:     JDBCTest.java
// Platform: JDK 7
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Blah.
package jdbctest;

/**
 *
 * @author msongy
 */
public class JDBCTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            SplatterDB db = SplatterDB.getInstance();
            try (
                SplatterDBConnection c = db.connect("splatter_test", "T3st5pl@t7er!");
            ) {
               System.out.println("Yay, we connected!");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    
}
