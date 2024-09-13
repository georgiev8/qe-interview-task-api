package requests

import resources.Album
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class AlbumsTests extends Album {

    @Test
    void test_createAlbum() {
        Response response = createAlbum(1, 1, "test album title")

        response.statusCode() == 201
        response.prettyPrint()
    }

}
