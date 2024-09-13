package resources

class TestDataBuilder {

    /** <--- userToDo builder method ---> */
    static String userToDo(int userId, String title, boolean completed) {
        return "  {\n" +
                "    \"userId\": " + userId + ",\n" +
                "    \"title\": \"" + title + "\",\n" +
                "    \"completed\": " + completed + "\n" +
                "  }"
    }

    /** <--- user builder method ---> */
    static String user(String name, String userName, String email) {
        return " {\n" +
                "    \"name\": " + name + ",\n" +
                "    \"username\": " + userName + ",\n" +
                "    \"email\": " + email + ",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Kulas Light\",\n" +
                "      \"suite\": \"Apt. 556\",\n" +
                "      \"city\": \"Gwenborough\",\n" +
                "      \"zipcode\": \"92998-3874\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"-37.3159\",\n" +
                "        \"lng\": \"81.1496\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
                "    \"website\": \"hildegard.org\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"Romaguera-Crona\",\n" +
                "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "      \"bs\": \"harness real-time e-markets\"\n" +
                "    }\n" +
                "  }"
    }

    /** <--- photo builder method ---> */
    static String photo(int albumId, int id, String title, String url, String thumbnailUrl) {
        return " {\n" +
                "    \"albumId\": " + albumId + ",\n" +
                "    \"id\": " + id + ",\n" +
                "    \"title\": \"" + title + "\",\n" +
                "    \"url\": \"" + url + "\",\n" +
                "    \"thumbnailUrl\": \"" + thumbnailUrl + "\"\n" +
                "  }"
    }

    /** <--- album builder method ---> */
    static String album(int userId, int id, String title) {
        return " {\n" +
                "    \"userId\": " + userId + ",\n" +
                "    \"id\": " + id + ",\n" +
                "    \"title\": \"" + title + "\"\n" +
                "  }"
    }

    /** <--- comment builder method ---> */
    static String comment(int postId, int id, String name, String email, String body) {
        return " {\n" +
                "    \"postId\": " + postId + ",\n" +
                "    \"id\": " + id + ",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"body\": \"" + body + "\"\n" +
                "  }"
    }

    /** <--- post builder method ---> */
    static String post(int userId, int id, String title, String body) {
        return " {\n" +
                "    \"userId\": " + userId + ",\n" +
                "    \"id\": " + id + ",\n" +
                "    \"title\": \"" + title + "\",\n" +
                "    \"body\": \"" + body + "\"\n" +
                "  }"
    }
}
