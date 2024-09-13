package requests

import resources.Post
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class PostsTests extends Post {

    @Test
    void test_createPost() {
        Response response = createPost(3, 3, "test post title", "test post body")

        response.statusCode() == 201
        response.prettyPrint()
    }

}
