package resources

import io.restassured.response.Response

class Post extends HTTPClient {

    final posts = '/posts'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createPost(int userId, int id, String title, String body) {
        post(posts, testDataBuilder.post(userId, id, title, body))
    }

}
