package resources

import utils.*
import database.*
import io.restassured.response.Response

class AlbumClient extends HTTPClient implements Database<Map<String, Object>> {
    final String albums = '/albums'

    Response createAlbum(int userId, String title) {
        Response response = post(albums, TestDataBuilder.buildAlbum(userId, title))
        assertAlbumSuccessfulCreation(response, userId, title)
        /** add the created album to our embedded DB under the "albums" key
         * getMap("") is used to convert the JSON response to a Map object
         * */
        addToDB("albums", response.jsonPath().getMap(""))

        return response
    }

    void assertAlbumPresenceInDB(int userId, String title) {
        /** retrieve all albums from DB */
        List<Map<String, Object>> albums = getFromDB("albums")

        /** assert that the album is in the list of albums */
        assert albums.find {
            it.get("userId") == userId && it.get("title") == title
        } != null

        // "find" does the job faster
        // albums.containsAll([
        //      [userId: userId, title: title]
        // ])
    }

    static void assertAlbumSuccessfulCreation(Response response, int userId, String title) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("userId") == userId
        assert response.jsonPath().getString("title") == title
    }
}
