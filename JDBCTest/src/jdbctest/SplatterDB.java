/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctest;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author msongy
 */
public class SplatterDB {
    
    public static SplatterDB getInstance() throws Exception {
        if (instance == null) {
            instance = new SplatterDB();
        }
        return instance;
    }
    
    /**
     * 
     * @param username
     * @param password
     * @return 
     */
    public SplatterDBConnection connect(String username, String password)
            throws Exception {
        try {
            return new SplatterDBConnection(
                    DriverManager.getConnection(url, username, password));
        } catch (SQLException e) {
            throw new Exception(
                "Failed to establish database connection", e
            );
        }
    }

    /**
     * Constructs a SplatterDB.
     * 
     * @throws Exception when there is a problem reading the database properties.
     */
    private SplatterDB() throws Exception {
        // Try to load the database properties.
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

        // Get the driver class.
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        } else {
            throw new Exception(
                DATABASE_PROPERTIES_FILE + "Missing jdbc.drivers property");
        }
        
        // Get the connection URL.
        url = props.getProperty("jdbc.url");
        if (url == null) {
            throw new Exception(
                DATABASE_PROPERTIES_FILE + "Missing jdbc.url property");
        }
    }
    
    /** Database URL */
    private final String url;
    
    /** Database properties file name */
    private static final String DATABASE_PROPERTIES_FILE = "database.properties";
    
    /** The single instance */
    private static SplatterDB instance;
}
