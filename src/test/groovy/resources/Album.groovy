package resources

import io.restassured.response.Response

class Album extends HTTPClient {

    final albums = '/albums'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createAlbum(int userId, int id, String title) {
        post(albums, testDataBuilder.album(userId, id, title))
    }

}
