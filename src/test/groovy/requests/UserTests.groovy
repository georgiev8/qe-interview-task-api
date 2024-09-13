package requests

import resources.User
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class UserTests extends User {

    @Test
    void test_createUser() {
        Response response = createUser("test name", "test username", "email@example.com")

        response.statusCode() == 201
        response.prettyPrint()
    }

}
