package tests

import database.*
import resources.*
import org.junit.jupiter.api.Test
import io.restassured.response.Response

class QAInterviewTests implements Database<Map<String, Object>> {
    UserClient userClient = new UserClient()
    ToDoClient toDoClient = new ToDoClient()
    PostClient postClient = new PostClient()
    AlbumClient albumClient = new AlbumClient()
    PhotoClient photoClient = new PhotoClient()
    CommentClient commentClient = new CommentClient()
    DatabaseClient databaseClient = new DatabaseClient()

    @Test
    void test_createUser_andItsRelatedResources() {
        Response userResponse = userClient.createUser(
                "test user",
                "test_user",
                "testuser@example.com"
        )
        /** store the "userId" for the related resources */
        int userId = userResponse.jsonPath().getInt("id")
        userClient.assertUserPresenceInDB(
                "test user",
                "test_user",
                "testuser@example.com"
        )

        /** use the "userId" to create a post for that user */
        Response postResponse = postClient.createPost(
                userId,
                "test post title",
                "this is the body of the test post"
        )
        /** store the "postId" for the related resources */
        int postId = postResponse.jsonPath().getInt("id")
        postClient.assertPostPresenceInDB(
                userId,
                "test post title",
                "this is the body of the test post"
        )

        /** use the "postId" to create a comment for that post */
        commentClient.createComment(
                postId,
                "someone who comments",
                "commenter@example.com",
                "this is a test comment"
        )
        commentClient.assertCommentPresenceInDB(
                postId,
                "someone who comments",
                "commenter@example.com",
                "this is a test comment"
        )

        /** use the "userId" to create a to-do for that user */
        toDoClient.createToDo(
                userId,
                "test to-do title",
                false
        )
        toDoClient.assertToDoPresenceInDB(
                userId,
                "test to-do title",
                false
        )

        /** use the "userId" to create an album for that user */
        Response albumResponse = albumClient.createAlbum(
                userId,
                "test album title"
        )
        /** store the "albumId" for the related resources */
        int albumId = albumResponse.jsonPath().getInt("id")
        albumClient.assertAlbumPresenceInDB(
                userId,
                "test album title"
        )

        /** use the "albumId" to create a photo for that album */
        photoClient.createPhoto(
                albumId,
                "test photo title",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
        )
        photoClient.assertPhotoPresenceInDB(
                albumId,
                "test photo title",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
        )
    }

    @Test
    void test_validateUserCreationIntegrity_forNotPersistedCase() {
        Response userResponse = userClient.createUser(
                "test user",
                "test_user",
                "testuser@example.com"
        )
        int userId = userResponse.jsonPath().getInt("id")

        databaseClient.simulateMissingEntityInDB(
                "users",
                "username",
                "test_user"
        )

        Response postResponse = postClient.createPost(
                userId,
                "test post title",
                "this is the body of the test post"
        )
        int postId = postResponse.jsonPath().getInt("id")

        databaseClient.simulateMissingEntityInDB(
                "posts",
                "title",
                "test post title"
        )

        commentClient.createComment(
                postId,
                "someone who comments",
                "commenter@example.com",
                "this is a test comment"
        )

        databaseClient.simulateMissingEntityInDB(
                "comments",
                "email",
                "commenter@example.com"
        )

        toDoClient.createToDo(
                userId,
                "test to-do title",
                false
        )

        databaseClient.simulateMissingEntityInDB(
                "todos",
                "title",
                "test to-do title"
        )

        Response albumResponse = albumClient.createAlbum(
                userId,
                "test album title"
        )
        int albumId = albumResponse.jsonPath().getInt("id")

        databaseClient.simulateMissingEntityInDB(
                "albums",
                "title",
                "test album title"
        )

        photoClient.createPhoto(
                albumId,
                "test photo title",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
        )

        databaseClient.simulateMissingEntityInDB(
                "photos",
                "title",
                "test photo title"
        )
    }

    @Test
    void test_validateUserCreationIntegrity_forDuplicatedCase() {
        Response userResponse = userClient.createUser(
                "test user",
                "test_user",
                "testuser@example.com"
        )
        int userId = userResponse.jsonPath().getInt("id")

        databaseClient.simulateDuplicatedEntityInDB(
                "users",
                "username",
                "test_user"
        )

        Response postResponse = postClient.createPost(
                userId,
                "test post title",
                "this is the body of the test post"
        )
        int postId = postResponse.jsonPath().getInt("id")

        databaseClient.simulateDuplicatedEntityInDB(
                "posts",
                "title",
                "test post title"
        )

        commentClient.createComment(
                postId,
                "someone who comments",
                "commenter@example.com",
                "this is a test comment"
        )

        databaseClient.simulateDuplicatedEntityInDB(
                "comments",
                "email",
                "commenter@example.com"
        )

        toDoClient.createToDo(
                userId,
                "test to-do title",
                false
        )

        databaseClient.simulateDuplicatedEntityInDB(
                "todos",
                "title",
                "test to-do title"
        )

        Response albumResponse = albumClient.createAlbum(
                userId,
                "test album title"
        )
        int albumId = albumResponse.jsonPath().getInt("id")

        databaseClient.simulateDuplicatedEntityInDB(
                "albums",
                "title",
                "test album title"
        )

        photoClient.createPhoto(
                albumId,
                "test photo title",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
        )

        databaseClient.simulateDuplicatedEntityInDB(
                "photos",
                "title",
                "test photo title"
        )
    }

    @Test
    void test_create100UsersAndToDos_validatingThePerformance() {
        /** get the current time in milliseconds */
        long startTime = System.currentTimeMillis()

        for (int i = 0; i < 100; i++) {
            /**
             * in a perfect production scenario,
             * each user creation should return a unique "userId" in our DB
             * for the sake of this test, we will simulate the uniqueness within our DB,
             * using our "userIds" HashSet
             * */
            int userId = i

            while (userIds.contains(userId)) {
                userId++
            }

            /** define a user test data object */
            Map<String, Object> userTestData = [
                    name    : "User $i",
                    username: "user$i",
                    email   : "user$i@example.com"
            ]

            userClient.createUser(
                    (String) userTestData.name,
                    (String) userTestData.username,
                    (String) userTestData.email
            )
            userClient.assertUserPresenceInDB(
                    (String) userTestData.name,
                    (String) userTestData.username,
                    (String) userTestData.email
            )

            /** define a to-do test data object */
            Map<String, Object> toDoTestData = [
                    title      : "To-Do for User $i",
                    isCompleted: false
            ] as Map<String, Object>

            /** simulating a to-do creation for each created userId */
            toDoClient.createToDo(
                    userId,
                    (String) toDoTestData.title,
                    (Boolean) toDoTestData.isCompleted
            )
            toDoClient.assertToDoPresenceInDB(
                    userId,
                    (String) toDoTestData.title,
                    (Boolean) toDoTestData.isCompleted
            )
        }

        /** get the current time in milliseconds again */
        long endTime = System.currentTimeMillis()

        /** calculate the total time taken and print it in seconds */
        println "Total time taken to create 100 users and 100 to-dos: ${(endTime - startTime) / 1000} seconds"
    }
}
