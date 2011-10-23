// Package: jdbctest
// File: JDBCTest.java
// Author: Michael Songy (msongy)
// Project:
// Purpose:
package jdbctest;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
                SplatterDBConnection c = db.connect("splatter_user", "5pl@t!");
            ) {
               System.out.println("Yay, we connected!");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    
}
