package requests

import resources.Photo
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class PhotosTests extends Photo {

    @Test
    void test_createPhoto() {
        Response response = createPhoto(2, 2, "test photo title", "test photo url", "test photo thumbnailUrl")

        response.statusCode() == 201
        response.prettyPrint()
    }

}
