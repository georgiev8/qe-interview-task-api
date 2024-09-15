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
    void test_validateUserCreationIntegrity() {
        /** initialize variable to randomly returns a test scenario */
        Random random = new Random()
        int scenario = random.nextInt(3) + 1

        /** initialize a list to store the user data (simulated local DB) */
        List<Map<String, Object>> users = new ArrayList<>()

        /** simulate scenarios based on the randomly generated number */
        switch (scenario) {
            case 1:
                /** correct persistence scenario */

                /** create a test data for scenario 1 */
                Map<String, Object> correctlyPersistedUserTestData = [
                        name    : "correctly persisted user",
                        username: "correctly_persisted_user",
                        email   : "correctlyPersisted@example.com"
                ]

                /** create a user and assert its successful creation */
                Response userResponse = userClient.createUser(
                        (String)correctlyPersistedUserTestData.name,
                        (String)correctlyPersistedUserTestData.username,
                        (String)correctlyPersistedUserTestData.email
                )
                UserAssertions.assertUserSuccessfulCreation(
                        userResponse,
                        (String)correctlyPersistedUserTestData.name,
                        (String)correctlyPersistedUserTestData.username,
                        (String)correctlyPersistedUserTestData.email
                )

                /** add the user to the local list (simulated local DB) */
                Map<String, Object> user = [
                        name    : (String)correctlyPersistedUserTestData.name,
                        username: (String)correctlyPersistedUserTestData.username,
                        email   : (String)correctlyPersistedUserTestData.email
                ]
                users.add(user)

                /** assert that the user is in the list (simulated local DB) */
                assert users.size() == 1
                assert users.get(0).name == (String)correctlyPersistedUserTestData.name
                assert users.get(0).username == (String)correctlyPersistedUserTestData.username
                assert users.get(0).email == (String)correctlyPersistedUserTestData.email

                println "Scenario 1: Correct persistence"

                break

            case 2:
                /** failed persistence scenario */

                /** create a test data */
                Map<String, Object> notPersistedUserTestData = [
                        name    : "not persisted user",
                        username: "not_persisted_user",
                        email   : "notPersisted@example.com"
                ]

                /** create a user */
                Response userResponse = userClient.createUser(
                        (String)notPersistedUserTestData.name,
                        (String)notPersistedUserTestData.username,
                        (String)notPersistedUserTestData.email
                )

                /** simulate the API response being successful */
                assert userResponse.statusCode() == 201
                /** simulate the failure to persist within the local DB (do not add the user) */
                boolean persistenceSuccess = false /** simulating a DB issue */

                /** simulate checking if the user was added to the list (simulated local DB) */
                if (!persistenceSuccess) {
                    assert users.size() == 0

                    println "User creation was successful, but persistence failed."
                }

                println "Scenario 2: Failed persistence"

                break

            case 3:
                /** duplicated persistence scenario */

                /** create a test data */
                Map<String, Object> duplicatedUserTestData = [
                        name    : "duplicated user",
                        username: "duplicated_user",
                        email   : "duplicated@example.com"
                ]

                /** create a user and assert its successful creation */
                Response userResponse = userClient.createUser(
                        (String)duplicatedUserTestData.name,
                        (String)duplicatedUserTestData.username,
                        (String)duplicatedUserTestData.email
                )
                UserAssertions.assertUserSuccessfulCreation(
                        userResponse,
                        (String)duplicatedUserTestData.name,
                        (String)duplicatedUserTestData.username,
                        (String)duplicatedUserTestData.email
                )

                /** create a duplicated user and assert its successful creation */
                Response duplicatedUserResponse = userClient.createUser(
                        (String)duplicatedUserTestData.name,
                        (String)duplicatedUserTestData.username,
                        (String)duplicatedUserTestData.email
                )
                UserAssertions.assertUserSuccessfulCreation(
                        duplicatedUserResponse,
                        (String)duplicatedUserTestData.name,
                        (String)duplicatedUserTestData.username,
                        (String)duplicatedUserTestData.email
                )

                /** simulate adding both users to the local list (simulated local DB) */
                List<Map<String, Object>> duplicatedUser = [
                        [
                                name    : (String)duplicatedUserTestData.name,
                                username: (String)duplicatedUserTestData.username,
                                email   : (String)duplicatedUserTestData.email
                        ],
                        [
                                name    : (String)duplicatedUserTestData.name,
                                username: (String)duplicatedUserTestData.username,
                                email   : (String)duplicatedUserTestData.email
                        ]
                ]
                users.addAll(duplicatedUser)

                /** define the expected duplicated user object */
                Map<String, Object> expectedDuplicatedUserData = [
                        name    : (String)duplicatedUserTestData.name,
                        username: (String)duplicatedUserTestData.username,
                        email   : (String)duplicatedUserTestData.email
                ]

                /** assert that both users are present in the list (simulated local DB) */
                assert users.size() == 2
                assert users.get(0) == expectedDuplicatedUserData
                assert users.get(1) == expectedDuplicatedUserData

                println "Scenario 3: Duplicate persistence"

                break
        }
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

            /** define a user test data object */
            Map<String, Object> userTestData = [
                    name    : "User $i",
                    username: "user$i",
                    email   : "user$i@example.com"
            ]

            /** create the user and assert its creation */
            Response userResponse = userClient.createUser(
                    (String)userTestData.name,
                    (String)userTestData.username,
                    (String)userTestData.email
            )
            UserAssertions.assertUserSuccessfulCreation(
                    userResponse,
                    (String)userTestData.name,
                    (String)userTestData.username,
                    (String)userTestData.email
            )
            /** store the user data within its list reference */
            users.add([
                    id      : userId,
                    name    : (String)userTestData.name,
                    username: (String)userTestData.username,
                    email   : (String)userTestData.email
            ])

            /** define a to-do test data object */
            Map<String, Object> toDoTestData = [
                    title: "To-Do for User $i",
                    isCompleted: false
            ] as Map<String, Object>

            /** create a to-do for each created user and assert its creation */
            Response toDoResponse = toDoClient.createToDo(
                    userId,
                    (String)toDoTestData.title,
                    (Boolean)toDoTestData.isCompleted
            )
            ToDoAssertions.assertToDoSuccessfulCreation(
                    toDoResponse,
                    userId,
                    (String)toDoTestData.title,
                    (Boolean)toDoTestData.isCompleted
            )
            /** store the to-do data within its list reference */
            toDos.add([
                    userId   : userId,
                    title    : (String)toDoTestData.title,
                    completed: (Boolean)toDoTestData.isCompleted
            ])
        }

        /** stop the timer */
        long endTime = System.currentTimeMillis()

        /** calculate the total time taken and print it in seconds */
        long totalTime = endTime - startTime

        println "Total time taken to create 100 users and 100 to-dos: ${totalTime / 1000} seconds"
    }
}
