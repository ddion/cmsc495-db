// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  jdbctest
// File:     SplatterDBTest.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides a fixture for testing the Splatter database.
package splatter.db;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test fixture for the Splatter database.
 * 
 * @author msongy
 */
public class SplatterDBTest {
   
    /**
     * Constructs the test fixture.
     */
    public SplatterDBTest() {
    }

    /**
     * Called before any of the test cases have run.
     * 
     * @throws Exception if there is an error setting up the class
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * Called after all of the test cases have run.
     * 
     * @throws Exception if there is an error tearing down the class
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    /**
     * Called before each test case has run.
     */
    @Before
    public void setUp() {
    }
    
    /**
     * Called after each test case has run.
     */
    @After
    public void tearDown() {
    }
    
    /**
     * A test.
     */
    @Test
    public void hello()
    {
    }
}
