package resources

import io.restassured.response.Response

class UserClient extends HTTPClient {
    final users = '/users'

    TestDataBuilder testDataBuilder = new TestDataBuilder()

    Response createUser(String name, String username, String email) {
        post(users, testDataBuilder.user(name, username, email))
    }

    void createUserAndAssertSuccess(String name, String username, String email) {
        Response userResponse = this.createUser(name, username, email)
        UserAssertions.assertUserSuccessfulCreation(userResponse, name, username, email)
    }

    void createUserMultipleTimes(int times, String name, String username, String email) {
        for (int i = 0; i < times; i++) {
            this.createUserAndAssertSuccess(name, username, email)
        }
    }

    int createUserAndReturnId(String name, String username, String email) {
        Response userResponse = this.createUser(name, username, email)
        UserAssertions.assertUserSuccessfulCreation(userResponse, name, username, email)

        return userResponse.jsonPath().getInt("id")
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
