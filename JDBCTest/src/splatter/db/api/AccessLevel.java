// Course:   CMSC495 - Trends and Projects in Computer Science
// Project:  Splatter - Team 1
// Author:   Michael Songy (msongy)
// Date:     10/23/2011
// Package:  splatter.db
// File:     AccessLevel.java
// Platform: JDK 7
//           JUnit 4.8.2
//           NetBeans IDE 7.0.1
//           PostgreSQL 9.1
//           Ubuntu 11.10
// Purpose:  Provides an enumeration corresponding to the
//           SPLATTER_API.ACCESS_LEVEL enumeration.
package splatter.db.api;

/**
 *
 * Enumeration corresponding to the SPLATTER_API.ACCESS_LEVEL enumeration.
 *
 * @author msongy
 */
public enum AccessLevel {
    /** No access */
    NONE,
    /** Only followers have access */
    FOLLOWERS,
    /** Everyone has access */
    ALL;
    
    static AccessLevel fromString(String s) {
        if (s != null) {
            return valueOf(s.toUpperCase());
        } else {
            return null;
        }
    }            
}
