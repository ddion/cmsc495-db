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
                        DeleteUser deleteUser = new DeleteUser(c);
                        Login login = new Login(c);
                        RetrieveUser retrieveUser = new RetrieveUser(c);
                        UpdateUser updateUser = new UpdateUser(c);
                        Logout logout = new Logout(c);
                        ViewUser viewUser = new ViewUser(c);
                        FindUserByName findUserByName = new FindUserByName(c);
                        FindUserByUsername findUserByUsername = new FindUserByUsername(c);
                        FindUserByEmail findUserByEmail = new FindUserByEmail(c);
                        CreateUpdate createUpdate = new CreateUpdate(c);
                        RetrieveUpdate retrieveUpdate = new RetrieveUpdate(c);
                        RetrieveUpdates retrieveUpdates = new RetrieveUpdates(c);
                        SaveUpdate saveUpdate = new SaveUpdate(c);
                        PostUpdate postUpdate = new PostUpdate(c);
                        DeleteUpdate deleteUpdate = new DeleteUpdate(c);
                        ViewFollowedUpdates viewFollowedUpdates = new ViewFollowedUpdates(c);
                        ViewUserUpdates viewUserUpdates = new ViewUserUpdates(c)) {
                    // Reset the database for testing
                    reset.call();
                    
                    // Create the first user
                    long id1 = createUser.call(
                            "msongy", "waxon!",
                            "Michael", "P", "Songy", AccessLevel.ALL,
                            "msongy@sbcglobal.net", AccessLevel.ALL);
                    System.out.printf("Added user %d%n", id1);
                    
                    // Log the first user in
                    Login.Results r1 = login.call("msongy", "waxon!");
                    System.out.printf(
                            "Logged in user %d, auth token = %s%n",
                            r1.getId(), r1.getAuthToken());
                    
                    // Delete the first user
                    deleteUser.call(r1.getId(), r1.getAuthToken());
                    System.out.printf("Deleted user %d%n", r1.getId());
                    
                    // Re-create the first user
                    id1 = createUser.call(
                            "msongy", "waxon!",
                            "Michael", "P", "Songy", AccessLevel.ALL,
                            "msongy@sbcglobal.net", AccessLevel.ALL);
                    
                    // Log the first user in
                    r1 = login.call("msongy", "waxon!");
                    System.out.printf(
                            "Logged in user %d, auth token = %s%n",
                            r1.getId(), r1.getAuthToken());
                    
                    // Create the second user
                    long id2 = createUser.call(
                            "wsongy", "waxoff!",
                            "Wanda", "B", "Songy", AccessLevel.FOLLOWERS,
                            "wsongy@sbcglobal.net", AccessLevel.FOLLOWERS);
                    System.out.printf("Added user %d%n", id2);
                    
                    // Log the second user in
                    Login.Results r2 = login.call("wsongy", "waxoff!");
                    System.out.printf(
                            "Logged in user %d, auth token = %s%n",
                            r2.getId(), r2.getAuthToken());
                    
                    // Have the second user view her account
                    retrieveUser(retrieveUser, r2.getId(), r2.getAuthToken());
                    
                    // Have the second user update her account
                    updateUser.call(
                            r2.getId(), r2.getAuthToken(),
                            "wsongy", "waxoff!",
                            "Wanda", "B", "Songy", AccessLevel.FOLLOWERS,
                            "wsongy@chimpokomon.net", AccessLevel.FOLLOWERS);
                    
                    // Have the second user view her updated account
                    retrieveUser(retrieveUser, r2.getId(), r2.getAuthToken());
                    
                    // Have the second user view the first user
                    viewUser(viewUser, r2.getId(), r2.getAuthToken(), id1);
                    
                    // Have the second user find the first user by name,
                    // username, and email
                    findUserByName(findUserByName, r2.getId(), r2.getAuthToken(), null, null, "Songy");
                    findUserByUsername(findUserByUsername, r2.getId(), r2.getAuthToken(), "msongy");
                    findUserByEmail(findUserByEmail, r2.getId(), r2.getAuthToken(), "msongy@sbcglobal.net");
                    
                    // Have the second user create an update
                    long updateId = createUpdate.call(
                            r2.getId(), r2.getAuthToken(),
                            "Stand back, I'm going to try science!");
                    System.out.printf("Created update %d%n", updateId);

                    // Have the second user retrieve the created update
                    retrieveUpdate(
                            retrieveUpdate,
                            r2.getId(), r2.getAuthToken(),
                            updateId);
                    
                    // Post some more updates to test paging
                    for (int i = 0; i < 10; ++i) {
                        long id = createUpdate.call(
                                r2.getId(), r2.getAuthToken(),
                                "Test Update " + i);
                        System.out.printf("Created update %d%n", id);
                    }
                    
                    // Have the second user save her first update
                    saveUpdate.call(
                            r2.getId(), r2.getAuthToken(),
                            updateId,
                            "Ha ha! I'm still doing science!");
                    System.out.printf("Created update %d%n", updateId);
                    
                    // Have the second user retrieve her updates 5 at a time
                    retrieveUpdates(
                                retrieveUpdates,
                                r2.getId(), r2.getAuthToken(),
                                5);
                    
                    // Have the second user post her first update
                    postUpdate.call(
                            r2.getId(), r2.getAuthToken(),
                            updateId);
                    System.out.printf("Posted update %d%n", updateId);
                    
                    // Have the second user delete her first update
                    deleteUpdate.call(
                            r2.getId(), r2.getAuthToken(),
                            updateId);
                    System.out.printf("Deleted update %d%n", updateId);
                    
                    // Have the second user create and post another update
                    updateId = createUpdate.call(
                            r2.getId(), r2.getAuthToken(),
                            "I'm back to doing science!");
                    System.out.printf("Created update %d%n", updateId);
                    
                    // Have the second user post her update
                    postUpdate.call(
                            r2.getId(), r2.getAuthToken(),
                            updateId);
                    System.out.printf("Posted update %d%n", updateId);
                    
                    // Have the first user view followed updates
                    viewFollowedUpdates(
                            viewFollowedUpdates,
                            r1.getId(), r1.getAuthToken(),
                            0);
                    
                    // Have the first user view the second user's updates
                    viewUserUpdates(
                            viewUserUpdates,
                            r1.getId(), r1.getAuthToken(),
                            r2.getId(),
                            3);
                    
                    // Logout the second user
                    logout.call(r2.getId(), r2.getAuthToken());
                    System.out.printf("Logged out user %d%n", r2.getId());
                    
                    // Commit all the changes
                    c.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    static void retrieveUser(
            RetrieveUser statement,
            long id, String authToken)
            throws SQLException {
        RetrieveUser.RowVisitor visitor = new RetrieveUser.RowVisitor() {
            public void visit(
                long id,
                Timestamp createdTime, Timestamp updatedTime,
                String username, String password,
                String first, String mi, String last, AccessLevel namePrivacy,
                String email, AccessLevel emailPrivacy) {
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

        try (ResultSet resultSet = statement.call(id, authToken)) {
            while (resultSet.next()) {
                statement.visitRow(resultSet, visitor);
            }
        }
    }
    
    static void viewUser(
            ViewUser statement,
            long viewerId, String authToken,
            long userId)
            throws SQLException {
        System.out.printf("User %d viewing user %d%n", viewerId, userId);
        try (ResultSet resultSet = statement.call(viewerId, authToken, userId)) {
            processFindUserResults(statement, resultSet);
        }
    }
    
    static void findUserByName(
            FindUserByName statement,
            long viewerId, String authToken,
            String first, String mi, String last)
            throws SQLException {
        System.out.printf(
                "User %d searching for user by name:%n" +
                "  first = %s%n" +
                "  mi = %s%n" +
                "  last = %s%n",
                viewerId,
                first != null ? first : "<null>",
                mi != null ? mi : "<null>",
                last != null ? last : "<null>");
        try (ResultSet resultSet = statement.call(viewerId, authToken, first, mi, last)) {
            processFindUserResults(statement, resultSet);
        }
    }
    
    static void findUserByUsername(
            FindUserByUsername statement,
            long viewerId,
            String authToken,
            String username)
            throws SQLException {
        System.out.printf(
                "User %d searching for user by username: %s%n",
                viewerId, username);
        try (ResultSet resultSet = statement.call(viewerId, authToken, username)) {
            processFindUserResults(statement, resultSet);
        }
    }
    
    static void findUserByEmail(
            FindUserByEmail statement,
            long viewerId,
            String authToken,
            String email)
            throws SQLException {
        System.out.printf(
                "User %d searching for user by email: %s%n",
                viewerId, email);
        try (ResultSet resultSet = statement.call(viewerId, authToken, email)) {
            processFindUserResults(statement, resultSet);
        }
    }
    
    static void processFindUserResults(
            AbstractFindUser statement,
            ResultSet resultSet)
            throws SQLException {
        
        AbstractFindUser.RowVisitor visitor = new AbstractFindUser.RowVisitor() {
            public void visit(
                long id, String username,
                String first, String mi, String last, boolean namePrivacy,
                String email, boolean emailPrivacy,
                boolean viewerFollowingUser, boolean userFollowingViewer) {
                System.out.printf(
                        "Found user:%n" +
                        "  id = %d%n" +
                        "  username = %s%n" +
                        "  first = %s%n" +
                        "  mi = %s%n" +
                        "  last = %s%n" +
                        "  namePrivacy = %b%n" +
                        "  email = %s%n" +
                        "  emailPrivacy = %b%n" +
                        "  viewerFollowingUser = %b%n" +
                        "  userFollowingViewer = %b%n",
                        id,
                        username,
                        first, mi, last, namePrivacy,
                        email, emailPrivacy,
                        viewerFollowingUser, userFollowingViewer);
            }
        };
        while (resultSet.next()) {
            statement.visitRow(resultSet, visitor);
        }
    }
    
    static void retrieveUpdate(
            RetrieveUpdate statement,
            long userId, String authToken,
            long updateId)
            throws SQLException {
        System.out.printf(
                "User %d retrieving update %d%n",
                userId, updateId);
        try (ResultSet resultSet = statement.call(userId, authToken, updateId)) {
            processRetrieveUpdatesResults(statement, resultSet);
        }
    }
    
    private interface PageFunc {
        
        LimitedResultSet call(int offset, int count) throws Exception;
        void processResult(ResultSet results) throws Exception;
    }
    
    private static void paginate(PageFunc func, int pageSize) throws Exception {
        int numPages = 0;
        int currentPage = 0;
        do {
            try (LimitedResultSet r = func.call(currentPage * pageSize, pageSize)) {
                int total = r.getTotal();
                numPages = pageSize > 0 
                         ? (total - 1) / pageSize
                         : 0;
                System.out.printf("Page %d of %d%n", currentPage + 1, numPages + 1);
                func.processResult(r.getResultSet());
            }
        } while (currentPage++ < numPages);
    }
    
    static void retrieveUpdates(
            final RetrieveUpdates statement,
            final long userId, final String authToken,
            final int pageSize)
            throws Exception {
        System.out.printf(
                "User %d retrieving updates with page size of %d%n",
                userId, pageSize);
        paginate(
            new PageFunc() {
                public LimitedResultSet call(int offset, int count) throws Exception {
                    return statement.call(
                            userId, authToken,
                            offset, count);
                }

                public void processResult(ResultSet results) throws Exception {
                    processRetrieveUpdatesResults(statement, results);
                }
            }, pageSize);
    }
    
    static void processRetrieveUpdatesResults(
            AbstractRetrieveUpdates statement,
            ResultSet resultSet)
            throws SQLException {
        
        AbstractRetrieveUpdates.RowVisitor visitor = new AbstractRetrieveUpdates.RowVisitor() {
            public void visit(
                long updateId, long userId,
                Timestamp createdTime, Timestamp updatedTime,
                boolean posted, String message) {
                System.out.printf(
                        "Retrieved update:%n" +
                        "  updateId = %d%n" +
                        "  userId = %d%n" +
                        "  createdTime = %s%n" +
                        "  updatedTime = %s%n" +
                        "  posted = %b%n" +
                        "  message = %s%n",
                        updateId, userId,
                        createdTime.toString(), updatedTime.toString(),
                        posted, message);
            }
        };
        
        while (resultSet.next()) {
            statement.visitRow(resultSet, visitor);
        }
    }
    
    static void viewFollowedUpdates(
            final ViewFollowedUpdates statement,
            final long viewerId, final String authToken,
            final int pageSize)
            throws Exception {
        System.out.printf(
                "User %d viewing updates with page size of %d%n",
                viewerId, pageSize);
        paginate(
            new PageFunc() {
                public LimitedResultSet call(int offset, int count) throws Exception {
                    return statement.call(
                            viewerId, authToken,
                            offset, count);
                }

                public void processResult(ResultSet results) throws Exception {
                    processViewUpdatesResults(statement, results);
                }
            }, pageSize);
    }
    
    static void viewUserUpdates(
            final ViewUserUpdates statement,
            final long viewerId, final String authToken,
            final long ownerId,
            final int pageSize)
            throws Exception {
        System.out.printf(
                "User %d viewing user %d updates with page size of %d%n",
                viewerId, ownerId, pageSize);
        paginate(
            new PageFunc() {
                public LimitedResultSet call(int offset, int count) throws Exception {
                    return statement.call(
                            viewerId, authToken,
                            ownerId,
                            offset, count);
                }

                public void processResult(ResultSet results) throws Exception {
                    processViewUpdatesResults(statement, results);
                }
            }, pageSize);
    }
    
    static void processViewUpdatesResults(
            AbstractViewUpdates statement,
            ResultSet resultSet)
            throws SQLException {
        
        AbstractViewUpdates.RowVisitor visitor = new AbstractViewUpdates.RowVisitor() {
            public void visit(
                long updateId,
                long userId, String username,
                Timestamp postedTime, String message) {
                System.out.printf(
                        "Retrieved update:%n" +
                        "  updateId = %d%n" +
                        "  userId = %d%n" +
                        "  username = %s%n" +
                        "  postedTime = %s%n" +
                        "  message = %s%n",
                        updateId,
                        userId, username,
                        postedTime.toString(), message);
            }
        };
        
        while (resultSet.next()) {
            statement.visitRow(resultSet, visitor);
        }
    }
}
