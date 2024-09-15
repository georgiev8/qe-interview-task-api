package resources

import io.restassured.response.Response

class CommentClient extends HTTPClient {
    final comments = '/comments'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createComment(int postId, String name, String email, String body) {
        post(comments, testDataBuilder.comment(postId, name, email, body))
    }

    void createPostCommentAndAssertSuccess(int postId, String name, String email, String body) {
        Response postResponse = this.createComment(postId, name, email, body)
        CommentAssertions.assertCommentSuccessfulCreation(postResponse, postId, name, email, body)
    }
}

class CommentAssertions {
    static void assertCommentSuccessfulCreation(Response response, int postId, String name, String email, String body) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("postId") == postId
        assert response.jsonPath().getString("name") == name
        assert response.jsonPath().getString("email") == email
        assert response.jsonPath().getString("body") == body
    }
}
