package requests

import org.junit.jupiter.api.Test
import io.restassured.response.Response

import resources.UserClient
import resources.ToDoClient
import resources.PostClient
import resources.CommentClient
import resources.AlbumClient
import resources.PhotoClient

import resources.UserAssertions
import resources.ToDoAssertions
import resources.PostAssertions
import resources.CommentAssertions
import resources.AlbumAssertions
import resources.PhotoAssertions

class UserTests {
    UserClient userClient = new UserClient()
    ToDoClient toDoClient = new ToDoClient()
    PostClient postClient = new PostClient()
    CommentClient commentClient = new CommentClient()
    AlbumClient albumClient = new AlbumClient()
    PhotoClient photoClient = new PhotoClient()

    @Test
    void test_createUserAndEntities() {
        /** STEP 1 */
        /** create a user */
        Response userResponse = userClient.createUser(
                "Test User",
                "test_user",
                "testuser@example.com"
        )
        /** assert successful user creation */
        UserAssertions.assertUserSuccessfulCreation(
                userResponse,
                "Test User",
                "test_user",
                "testuser@example.com"
        )
        /** store the userId from the response in a variable */
        int userId = userResponse.jsonPath().getInt("id")

        /** STEP 2 */
        /** create a post for the user from step 1 */
        Response postResponse = postClient.createPost(
                userId,
                1,
                "Test Post Title",
                "This is the body of the test post."
        )
        /** assert successful post creation */
        PostAssertions.assertPostSuccessfulCreation(
                postResponse,
                userId,
                "Test Post Title",
                "This is the body of the test post."
        )
        /** store the postId from the response in a variable */
        int postId = postResponse.jsonPath().getInt("id")

        /** STEP 3 */
        /** create a comment for the user's post from step 2 */
        Response commentResponse = commentClient.createComment(
                postId,
                1,
                "Test Commenter",
                "commenter@example.com",
                "This is a test comment."
        )
        /** assert successful comment creation */
        CommentAssertions.assertCommentSuccessfulCreation(
                commentResponse,
                postId,
                "Test Commenter",
                "commenter@example.com",
                "This is a test comment."
        )

        /** STEP 4 */
        /** create a to-do for the user from step 1 */
        Response toDoResponse = toDoClient.createToDo(
                userId,
                "Test To-Do",
                false
        )
        /** assert successful to-do creation */
        ToDoAssertions.assertToDoSuccessfulCreation(
                toDoResponse,
                userId,
                "Test To-Do",
                false
        )

        /** STEP 5 */
        /** create an album for the user from step 1 */
        Response albumResponse = albumClient.createAlbum(
                userId,
                1,
                "Test Album Title"
        )
        /** assert successful album creation */
        AlbumAssertions.assertAlbumSuccessfulCreation(
                albumResponse,
                userId,
                "Test Album Title"
        )
        /** store the albumId from the response in a variable */
        int albumId = albumResponse.jsonPath().getInt("id")

        /** STEP 6 */
        /** create a photo in the album from step 5 */
        Response photoResponse = photoClient.createPhoto(
                albumId,
                1,
                "Test Photo",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
        )
        /** assert successful photo creation */
        PhotoAssertions.assertPhotoSuccessfulCreation(
                photoResponse,
                albumId,
                "Test Photo",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
        )
    }

    @Test
    void test_create100UsersAndToDosPerformance() {
        /** initialize lists to store the users and their to-dos */
        List<Map<String, Object>> users = new ArrayList<>()
        List<Map<String, Object>> toDos = new ArrayList<>()
        /** initialize a set to store the generated user IDs */
        Set<Integer> userIds = new HashSet<>()

        /** trigger the timer */
        long startTime = System.currentTimeMillis()

        /** loop to create 100 users and a to-do for each of them */
        for (int i = 0; i < 100; i++) {
            /** generate a unique userId for each user */
            int userId = i

            while (userIds.contains(userId)) {
                userId++
            }
            userIds.add(userId)

            /** create the user and assert its creation */
            Response userResponse = userClient.createUser(
                    "User $i",
                    "user$i",
                    "user$i@example.com"
            )
            UserAssertions.assertUserSuccessfulCreation(
                    userResponse,
                    "User $i",
                    "user$i",
                    "user$i@example.com"
            )
            /** store the user data within its list reference */
            users.add([
                    id      : userId,
                    name    : "User $i",
                    username: "user$i",
                    email   : "user$i@example.com"
            ])

            /** create a to-do for each created user and assert its creation */
            Response toDoResponse = toDoClient.createToDo(
                    userId,
                    "To-Do for User $i",
                    false
            )
            ToDoAssertions.assertToDoSuccessfulCreation(
                    toDoResponse,
                    userId,
                    "To-Do for User $i",
                    false
            )
            /** store the to-do data within its list reference */
            toDos.add([
                    userId   : userId,
                    title    : "To-Do for User $i",
                    completed: false
            ])
        }

        /** stop the timer */
        long endTime = System.currentTimeMillis()

        /** calculate the total time taken and print it in seconds */
        long totalTime = endTime - startTime

        println "Total time taken to create 100 users and 100 to-dos: ${totalTime / 1000} seconds"
    }
}
