package requests

import resources.Comment
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class CommentsTests extends Comment {

    @Test
    void test_createComment() {
        Response response = createComment(1, 1, "test comment name", "example@comment.com", "test comment body")

        response.statusCode() == 201
        response.prettyPrint()
    }

}
