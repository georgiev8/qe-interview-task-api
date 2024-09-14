package requests

import io.restassured.response.Response
import org.junit.jupiter.api.Test
import resources.HTTPClient
import resources.TestDataBuilder

class UserTests {

    @Test
    void test_createUserAndEntities() {
        // Step 1: Create a user
        String userBody = TestDataBuilder.user("Test User", "testuser", "testuser@example.com")
        // send a POST request to create a user
        Response userResponse = HTTPClient.post("/users", userBody)
        // assert the response status code is 201
        assert userResponse.statusCode() == 201
        // assert the user details in the response
        assert userResponse.jsonPath().getString("name") == "Test User"
        assert userResponse.jsonPath().getString("username") == "testuser"
        assert userResponse.jsonPath().getString("email") == "testuser@example.com"
        // get the user ID from the response
        int userId = userResponse.jsonPath().getInt("id")
        // some logs for debugging
        println "Created user with ID: $userId"
        userResponse.prettyPrint()

        // Step 2: Create a post for the user
        String postBody = TestDataBuilder.post(userId, 1, "Test Post Title", "This is the body of the test post.")
        // send a POST request to create a post for the previously created user
        Response postResponse = HTTPClient.post("/posts", postBody)
        // assert the response status code is 201
        assert postResponse.statusCode() == 201
        // assert the post details in the response
        assert postResponse.jsonPath().getInt("userId") == userId
        assert postResponse.jsonPath().getString("title") == "Test Post Title"
        assert postResponse.jsonPath().getString("body") == "This is the body of the test post."
        // get the post ID from the response
        int postId = postResponse.jsonPath().getInt("id")
        // some logs for debugging
        println "Created post with ID: $postId"
        postResponse.prettyPrint()

        // Step 3: Create a comment for the post
        String commentBody = TestDataBuilder.comment(postId, 1, "Test Commenter", "commenter@example.com", "This is a test comment.")
        // send a POST request to create a comment on the previously created post
        Response commentResponse = HTTPClient.post("/comments", commentBody)
        // assert the response status code is 201
        assert commentResponse.statusCode() == 201
        // assert the comment details in the response
        assert commentResponse.jsonPath().getInt("postId") == postId
        assert commentResponse.jsonPath().getString("name") == "Test Commenter"
        assert commentResponse.jsonPath().getString("email") == "commenter@example.com"
        assert commentResponse.jsonPath().getString("body") == "This is a test comment."
        // some logs for debugging
        println "Created comment on post ID: $postId"
        commentResponse.prettyPrint()

        // Step 4: Create a to-do for the user
        String toDoBody = TestDataBuilder.userToDo(userId, "Test To-Do", false)
        // send a POST request to create a to-do for the previously created user
        Response toDoResponse = HTTPClient.post("/todos", toDoBody)
        // assert the response status code is 201
        assert toDoResponse.statusCode() == 201
        // assert the to-do details in the response
        assert toDoResponse.jsonPath().getInt("userId") == userId
        assert toDoResponse.jsonPath().getString("title") == "Test To-Do"
        assert !toDoResponse.jsonPath().getBoolean("completed")
        // some logs for debugging
        println "Created to-do for user ID: $userId"
        toDoResponse.prettyPrint()

        // Step 5: Create an album for the user
        String albumBody = TestDataBuilder.album(userId, 1, "Test Album Title")
        // send a POST request to create an album for the previously created user
        Response albumResponse = HTTPClient.post("/albums", albumBody)
        // assert the response status code is 201
        assert albumResponse.statusCode() == 201
        // assert the album details in the response
        assert albumResponse.jsonPath().getInt("userId") == userId
        assert albumResponse.jsonPath().getString("title") == "Test Album Title"
        // get the album ID from the response
        int albumId = albumResponse.jsonPath().getInt("id")
        // some logs for debugging
        println "Created album with ID: $albumId"
        albumResponse.prettyPrint()

        // Step 6: Create a photo in the album
        String photoBody = TestDataBuilder.photo(albumId, 1, "Test Photo", "https://via.placeholder.com/600/92c952", "https://via.placeholder.com/150/92c952")
        // send a POST request to create a photo in the previously created album
        Response photoResponse = HTTPClient.post("/photos", photoBody)
        // assert the response status code is 201
        assert photoResponse.statusCode() == 201
        // assert the photo details in the response
        assert photoResponse.jsonPath().getInt("albumId") == albumId
        assert photoResponse.jsonPath().getString("title") == "Test Photo"
        assert photoResponse.jsonPath().getString("url") == "https://via.placeholder.com/600/92c952"
        assert photoResponse.jsonPath().getString("thumbnailUrl") == "https://via.placeholder.com/150/92c952"
        // some logs for debugging
        println "Created photo in album ID: $albumId"
        photoResponse.prettyPrint()
    }

    @Test
    void test_create100UsersAndToDosPerformance() {
        // start time
        long startTime = System.currentTimeMillis()

        // loop to create 100 users and a to-do for each user
        for (int i = 1; i <= 100; i++) {
            // Create a user
            String userBody = TestDataBuilder.user("User $i", "user$i", "user$i@example.com")
            Response userResponse = HTTPClient.post("/users", userBody)
            assert userResponse.statusCode() == 201
            int userId = userResponse.jsonPath().getInt("id")
            println "Created user with ID: $userId"

            // create a to-do for the created user
            String toDoBody = TestDataBuilder.userToDo(userId, "To-Do for User $i", false)
            Response toDoResponse = HTTPClient.post("/todos", toDoBody)
            assert toDoResponse.statusCode() == 201
            println "Created to-do for user ID: $userId"
        }

        // end time
        long endTime = System.currentTimeMillis()

        // calculate the total time taken in seconds
        long totalTime = endTime - startTime
        // print the total time taken
        println "Total time taken to create 100 users and 100 to-dos: ${totalTime / 1000} seconds"
    }
}
