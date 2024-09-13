package resources

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response

class HTTPClient {

    final static baseURI = 'https://jsonplaceholder.typicode.com'

    static {
        RestAssured.baseURI = baseURI
    }

    /** <--- post method ---> */
    static Response post(String resourceURL, String body) {
        return RestAssured
                .given()
                .body(body)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .post(resourceURL)
    }

    /** <--- get method ---> */
    static Response get(String resourceURL) {
        return RestAssured
                .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .get(resourceURL)
    }

    /** <--- getById method ---> */
    static Response getById(String resourceURL, int id) {
        return RestAssured
                .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .get(resourceURL + "/" + id)
    }

    /** <--- put method ---> */
    static Response put(String resourceURL, String body) {
        return RestAssured
                .given()
                .body(body)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .put(resourceURL)
    }

    /** <--- patch method ---> */
    static Response patch(String resourceURL, String body) {
        return RestAssured
                .given()
                .body(body)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .patch(resourceURL)
    }

    /** <--- delete method ---> */
    static Response delete(String resourceURL) {
        return RestAssured
                .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .delete(resourceURL)
    }

}
