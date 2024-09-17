package resources

import utils.*
import database.*
import io.restassured.response.Response

class ToDoClient extends HTTPClient implements Database<Map<String, Object>> {
    final String todos = '/todos'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createToDo(int userId, String title, boolean completed) {
        Response response = post(todos, testDataBuilder.userToDo(userId, title, completed))
        assertToDoSuccessfulCreation(response, userId, title, completed)
        /** add the created to-do to our embedded DB under the "todos" key
         * getMap("") is used to convert the JSON response to a Map object
         * */
        addToDB("todos", response.jsonPath().getMap(""))

        return response
    }

    void assertToDoInMemory(int userId, String title, boolean completed) {
        /** retrieve all todos from memory */
        List<Map<String, Object>> todos = getFromDB("todos")

        /** assert that the to-do is in the list of todos */
        assert todos.find {
            it.get("userId") == userId && it.get("title") == title && it.get("completed") == completed
        } != null

        // "find" does the job faster
        // todos.containsAll([
        //      [userId: userId, title: title, completed: completed]
        // ])
    }

    static void assertToDoSuccessfulCreation(Response response, int userId, String title, boolean completed) {
        assert response.statusCode() == 201
        assert response.jsonPath().getInt("userId") == userId
        assert response.jsonPath().getString("title") == title
        assert response.jsonPath().getBoolean("completed") == completed
    }
}
