package resources

import utils.*
import database.*
import io.restassured.response.Response

class CommentClient extends HTTPClient implements Database<Map<String, Object>> {
    final String comments = '/comments'

    Response createComment(int postId, String name, String email, String body) {
        Response response = post(comments, TestDataBuilder.buildComment(postId, name, email, body))
        assertCommentSuccessfulCreation(response, postId, name, email, body)
        /** add the created comment to our embedded DB under the "comments" key
         * getMap("") is used to convert the JSON response to a Map object
         * */
        addToDB("comments", response.jsonPath().getMap(""))

        return response
    }

    void assertCommentPresenceInDB(int postId, String name, String email, String body) {
        /** retrieve all comments from DB */
        List<Map<String, Object>> comments = getFromDB("comments")

        /** assert that the comment is in the list of comments */
        assert comments.find {
            it.get("postId") == postId && it.get("name") == name && it.get("email") == email && it.get("body") == body
        } != null

        // "find" does the job faster
        // comments.containsAll([
        //      [postId: postId, name: name, email: email, body: body]
        // ])
    }

    static void assertCommentSuccessfulCreation(Response response, int postId, String name, String email, String body) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("postId") == postId
        assert response.jsonPath().getString("name") == name
        assert response.jsonPath().getString("email") == email
        assert response.jsonPath().getString("body") == body
    }
}
