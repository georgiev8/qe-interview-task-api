package resources

import io.restassured.response.Response

class User extends HTTPClient {

    final users = '/users'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createUser(String name, String username, String email) {
        post(users, testDataBuilder.user(name, username, email))
    }

}
