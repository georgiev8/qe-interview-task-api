package resources

import utils.*
import database.*
import io.restassured.response.Response

class PhotoClient extends HTTPClient implements Database<Map<String, Object>> {
    final String photos = '/photos'

    Response createPhoto(int albumId, String title, String url, String thumbnailUrl) {
        Response response = post(photos, TestDataBuilder.buildPhoto(albumId, title, url, thumbnailUrl))
        assertPhotoSuccessfulCreation(response, albumId, title, url, thumbnailUrl)
        /** add the created photo to our embedded DB under the "photos" key
         * getMap("") is used to convert the JSON response to a Map object
         * */
        addToDB("photos", response.jsonPath().getMap(""))

        return response
    }

    void assertPhotoPresenceInDB(int albumId, String title, String url, String thumbnailUrl) {
        /** retrieve all photos from DB */
        List<Map<String, Object>> photos = getFromDB("photos")

        /** assert that the photo is in the list of photos */
        assert photos.find {
            it.get("albumId") == albumId && it.get("title") == title && it.get("url") == url && it.get("thumbnailUrl") == thumbnailUrl
        } != null

        // "find" does the job faster
        // photos.containsAll([
        //      [albumId: albumId, title: title, url: url, thumbnailUrl: thumbnailUrl]
        // ])
    }

    static void assertPhotoSuccessfulCreation(Response response, int albumId, String title, String url, String thumbnailUrl) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("albumId") == albumId
        assert response.jsonPath().getString("title") == title
        assert response.jsonPath().getString("url") == url
        assert response.jsonPath().getString("thumbnailUrl") == thumbnailUrl
    }
}
