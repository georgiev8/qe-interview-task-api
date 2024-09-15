package resources

import io.restassured.response.Response

class PostClient extends HTTPClient {
    final posts = '/posts'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createPost(int userId, String title, String body) {
        post(posts, testDataBuilder.post(userId, title, body))
    }

    int createPostAndReturnId(int userId, String title, String body) {
        Response postResponse = this.createPost(userId, title, body)
        PostAssertions.assertPostSuccessfulCreation(postResponse, userId, title, body)

        return postResponse.jsonPath().getInt("id")
    }
}

class PostAssertions {
    static void assertPostSuccessfulCreation(Response postResponse, int userId, String title, String body) {
        assert postResponse.statusCode() == 201
        assert postResponse.jsonPath().getInt("userId") == userId
        assert postResponse.jsonPath().getString("title") == title
        assert postResponse.jsonPath().getString("body") == body
    }
}
