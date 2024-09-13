package resources

import io.restassured.response.Response

class Comment extends HTTPClient {

    final comments = '/comments'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createComment(int postId, int id, String name, String email, String body) {
        post(comments, testDataBuilder.comment(postId, id, name, email, body))
    }

}
