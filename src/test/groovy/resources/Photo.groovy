package resources

import io.restassured.response.Response

class Photo extends HTTPClient {

    final photos = '/photos'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createPhoto(int albumId, int id, String title, String url, String thumbnailUrl) {
        post(photos, testDataBuilder.photo(albumId, id, title, url, thumbnailUrl))
    }

}
