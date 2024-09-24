package resources

import utils.*
import database.*
import io.restassured.response.Response

class PostClient extends HTTPClient implements Database<Map<String, Object>> {
    final String posts = '/posts'

    Response createPost(int userId, String title, String body) {
        Response response = post(posts, TestDataBuilder.buildPost(userId, title, body))
        assertPostSuccessfulCreation(response, userId, title, body)
        /** add the created post to our embedded DB under the "posts" key
         * getMap("") is used to convert the JSON response to a Map object
         * */
        addToDB("posts", response.jsonPath().getMap(""))

        return response
    }

    void assertPostPresenceInDB(int userId, String title, String body) {
        /** retrieve all posts from DB */
        List<Map<String, Object>> posts = getFromDB("posts")

        /** assert that the post is in the list of posts */
        assert posts.find {
            it.get("userId") == userId && it.get("title") == title && it.get("body") == body
        } != null

        // "find" does the job faster
        // posts.containsAll([
        //      [userId: userId, title: title, body: body]
        // ])
    }

    static void assertPostSuccessfulCreation(Response response, int userId, String title, String body) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("userId") == userId
        assert response.jsonPath().getString("title") == title
        assert response.jsonPath().getString("body") == body
    }
}
