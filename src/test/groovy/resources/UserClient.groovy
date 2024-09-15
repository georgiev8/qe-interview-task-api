package resources

import io.restassured.response.Response

class UserClient extends HTTPClient {
    final users = '/users'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createUser(String name, String username, String email) {
        post(users, testDataBuilder.user(name, username, email))
    }
}

class UserAssertions {
    static void assertUserSuccessfulCreation(Response userResponse, String name, String username, String email) {
        assert userResponse.statusCode() == 201
        assert userResponse.jsonPath().getString("name") == name
        assert userResponse.jsonPath().getString("username") == username
        assert userResponse.jsonPath().getString("email") == email
    }
}
