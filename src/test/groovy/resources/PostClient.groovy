package resources

import io.restassured.response.Response

class PostClient extends HTTPClient {
    final posts = '/posts'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createPost(int userId, int id, String title, String body) {
        post(posts, testDataBuilder.post(userId, id, title, body))
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
