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
            Connection c = getConnection();
            System.out.println("Yay, we connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a connection from the properties specified in the file
     * {@value #DATABASE_PROPERTIES_FILE}.
     *
     * @return the database connection
     * @throws Exception if an error occurs
     */
    private static Connection getConnection() throws Exception {
        Properties props = new Properties();
        try (
            FileInputStream in = new FileInputStream(DATABASE_PROPERTIES_FILE)
        ) {
            props.load(in);
        } catch (IOException e) {
            throw new Exception(
                "Failed to read " + DATABASE_PROPERTIES_FILE, e
            );
        }

        try {
            String drivers = props.getProperty("jdbc.drivers");
            if (drivers != null) System.setProperty("jdbc.drivers", drivers);

            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new Exception(
                "Failed to establish database connection", e
            );
        }
    }
    
    /** Database properties file name */
    private static final String DATABASE_PROPERTIES_FILE = "database.properties";
}
