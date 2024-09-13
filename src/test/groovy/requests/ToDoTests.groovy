package requests

import resources.ToDo
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class ToDoTests extends ToDo {

    @Test
    void test_createUserToDo() {
        Response response = createToDo(1, "test todo title", false)

        response.statusCode() == 201
        response.prettyPrint()
    }

}
