package resources

import io.restassured.response.Response

class ToDo extends HTTPClient {

    final toDos = '/todos'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createToDo(int userId, String title, boolean completed) {
        post(toDos, testDataBuilder.userToDo(userId, title, completed))
    }

}
