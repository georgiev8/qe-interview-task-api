package resources

import io.restassured.response.Response

class AlbumClient extends HTTPClient {
    final albums = '/albums'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createAlbum(int userId, int id, String title) {
        post(albums, testDataBuilder.album(userId, id, title))
    }
}

class AlbumAssertions {
    static void assertAlbumSuccessfulCreation(Response response, int userId, String title) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("userId") == userId
        assert response.jsonPath().getString("title") == title
    }
}
