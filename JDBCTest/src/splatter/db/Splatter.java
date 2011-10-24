// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db
// File:     Splatter.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a Splatter singleton class that acts as a factory for
//           SplatterConnection objects.
package splatter.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A singleton that is a factory for SplatterDBConnection objects.
 * 
 * @author msongy
 */
public class Splatter {
    
    /**
     * Enumeration corresponding to the SPLATTER_API.ACCESS_LEVEL enumeration.
     */
    public enum AccessLevel {
        /** No access */
        NONE,
        /** Only followers have access */
        FOLLOWERS,
        /** Everyone has access */
        ALL
    }
    
    /**
     * Returns the single <code>SplatterDB</code> instance.
     * 
     * @return the <code>SplatterDB</code> instance
     * @throws Exception if the SplatterDB cannot be created
     */
    public static Splatter getInstance() throws Exception {
        if (instance == null) {
            instance = new Splatter();
        }
        return instance;
    }
    
    /**
     * Creates a connection to the Splatter database.
     * 
     * @param username Username to connect with
     * @param password Password to connect with
     * @return a new connection to the Splatter database
     * @throws Exception if a connection cannot be created
     */
    public SplatterConnection connect(String username, String password)
            throws Exception {
        try {
            return new SplatterConnection(
                    DriverManager.getConnection(url, username, password));
        } catch (SQLException e) {
            throw new Exception(
                "Failed to establish database connection", e
            );
        }
    }

    /**
     * Constructs a <code>SplatterDB</code> object.
     * 
     * @throws Exception when there is a problem reading the database properties.
     */
    private Splatter() throws Exception {
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
    private static Splatter instance;
}
