package resources

class TestDataBuilder {

    /** <--- userToDo builder method ---> */
    static String userToDo(int userId, String title, boolean completed) {
        return """{
            "userId": $userId,
            "title": "$title",
            "completed": $completed
        }"""
    }

    /** <--- user builder method ---> */
    static String user(String name, String userName, String email) {
        return """{
            "name": "$name",
            "username": "$userName",
            "email": "$email",
            "address": {
                "street": "Kulas Light",
                "suite": "Apt. 556",
                "city": "Gwenborough",
                "zipcode": "92998-3874",
                "geo": {
                    "lat": "-37.3159",
                    "lng": "81.1496"
                }
            },
            "phone": "1-770-736-8031 x56442",
            "website": "hildegard.org",
            "company": {
                "name": "Romaguera-Crona",
                "catchPhrase": "Multi-layered client-server neural-net",
                "bs": "harness real-time e-markets"
            }
        }"""
    }

    /** <--- photo builder method ---> */
    static String photo(int albumId, int id, String title, String url, String thumbnailUrl) {
        return """{
            "albumId": $albumId,
            "id": $id,
            "title": "$title",
            "url": "$url",
            "thumbnailUrl": "$thumbnailUrl"
        }"""
    }

    /** <--- album builder method ---> */
    static String album(int userId, int id, String title) {
        return """{
            "userId": $userId,
            "id": $id,
            "title": "$title"
        }"""
    }

    /** <--- comment builder method ---> */
    static String comment(int postId, int id, String name, String email, String body) {
        return """{
            "postId": $postId,
            "id": $id,
            "name": "$name",
            "email": "$email",
            "body": "$body"
        }"""
    }

    /** <--- post builder method ---> */
    static String post(int userId, int id, String title, String body) {
        return """{
            "userId": $userId,
            "id": $id,
            "title": "$title",
            "body": "$body"
        }"""
    }
}