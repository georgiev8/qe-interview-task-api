package resources

import io.restassured.response.Response

class ToDoClient extends HTTPClient {
    final toDos = '/todos'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createToDo(int userId, String title, boolean completed) {
        post(toDos, testDataBuilder.userToDo(userId, title, completed))
    }
}

class ToDoAssertions {
    static void assertToDoSuccessfulCreation(Response response, int userId, String title, boolean completed) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("userId") == userId
        assert response.jsonPath().getString("title") == title
        assert response.jsonPath().getBoolean("completed") == completed
    }
}
