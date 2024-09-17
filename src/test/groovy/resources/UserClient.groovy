package resources

import utils.*
import database.*
import io.restassured.response.Response

class UserClient extends HTTPClient implements Database<Map<String, Object>> {
    final String users = '/users'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createUser(String name, String username, String email) {
        Response response = post(users, testDataBuilder.user(name, username, email))
        assertUserSuccessfulCreation(response, name, username, email)
        /** add the created user to our embedded DB under the "users" key
         * getMap("") is used to convert the JSON response to a Map object
         * */
        addToDB("users", response.jsonPath().getMap(""))

        return response
    }

    void assertUserInMemory(String name, String username, String email) {
        /** retrieve all users from memory */
        List<Map<String, Object>> users = getFromDB("users")

        /** assert that the user is in the list of users */
        assert users.find {
            it.get("name") == name && it.get("username") == username && it.get("email") == email
        } != null

        // "find" does the job faster
        // users.containsAll([
        //      [name: name, username: username, email: email]
        // ])
    }

    static void assertUserSuccessfulCreation(Response response, String name, String username, String email) {
        assert response.statusCode() == 201
        assert response.jsonPath().getString("name") == name
        assert response.jsonPath().getString("username") == username
        assert response.jsonPath().getString("email") == email
    }
}
