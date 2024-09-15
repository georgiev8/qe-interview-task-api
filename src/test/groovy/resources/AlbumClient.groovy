package resources

import io.restassured.response.Response

class AlbumClient extends HTTPClient {
    final albums = '/albums'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createAlbum(int userId, String title) {
        post(albums, testDataBuilder.album(userId, title))
    }

    int createAlbumAndReturnId(int userId, String title) {
        Response albumResponse = this.createAlbum(userId, title)
        AlbumAssertions.assertAlbumSuccessfulCreation(albumResponse, userId, title)

        return albumResponse.jsonPath().getInt("id")
    }
}

class AlbumAssertions {
    static void assertAlbumSuccessfulCreation(Response response, int userId, String title) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("userId") == userId
        assert response.jsonPath().getString("title") == title
    }
}
