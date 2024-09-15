package resources

import io.restassured.response.Response

class PhotoClient extends HTTPClient {
    final photos = '/photos'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createPhoto(int albumId, String title, String url, String thumbnailUrl) {
        post(photos, testDataBuilder.photo(albumId, title, url, thumbnailUrl))
    }

    void createAlbumPhotoAndAssertSuccess(int albumId, String title, String url, String thumbnailUrl) {
        Response userResponse = this.createPhoto(albumId, title, url, thumbnailUrl)

        PhotoAssertions.assertPhotoSuccessfulCreation(userResponse, albumId, title, url, thumbnailUrl)
    }
}

class PhotoAssertions {
    static void assertPhotoSuccessfulCreation(Response response, int albumId, String title, String url, String thumbnailUrl) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("albumId") == albumId
        assert response.jsonPath().getString("title") == title
        assert response.jsonPath().getString("url") == url
        assert response.jsonPath().getString("thumbnailUrl") == thumbnailUrl
    }
}
