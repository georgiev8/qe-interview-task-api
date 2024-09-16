package utils

class TestDataBuilder {
    /** userToDo data builder */
    static String userToDo(int userId, String title, boolean completed) {
        return """{
            "userId": $userId,
            "title": "$title",
            "completed": $completed
        }"""
    }

    /** user data builder */
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

    /** photo data builder */
    static String photo(int albumId, String title, String url, String thumbnailUrl) {
        return """{
            "albumId": $albumId,
            "title": "$title",
            "url": "$url",
            "thumbnailUrl": "$thumbnailUrl"
        }"""
    }

    /** album data builder */
    static String album(int userId, String title) {
        return """{
            "userId": $userId,
            "title": "$title"
        }"""
    }

    /** comment data builder */
    static String comment(int postId, String name, String email, String body) {
        return """{
            "postId": $postId,
            "name": "$name",
            "email": "$email",
            "body": "$body"
        }"""
    }

    /** post data builder */
    static String post(int userId, String title, String body) {
        return """{
            "userId": $userId,
            "title": "$title",
            "body": "$body"
        }"""
    }
}