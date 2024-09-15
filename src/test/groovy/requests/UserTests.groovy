package requests

import org.junit.jupiter.api.Test

import resources.UserClient
import resources.ToDoClient
import resources.PostClient
import resources.CommentClient
import resources.AlbumClient
import resources.PhotoClient

class UserTests {
    UserClient userClient = new UserClient()
    ToDoClient toDoClient = new ToDoClient()
    PostClient postClient = new PostClient()
    AlbumClient albumClient = new AlbumClient()
    PhotoClient photoClient = new PhotoClient()
    CommentClient commentClient = new CommentClient()

    @Test
    void test_createUserAndEntities() {
        /** create a user and return its ID */
        int userId = userClient.createUserAndReturnId("test User", "test_user", "testuser@example.com")
        /** create a post by using the userId's reference and return its ID */
        int postId = postClient.createPostAndReturnId(userId, "test post title", "this is the body of the test post.")
        /** create a comment by using the postId's reference */
        commentClient.createPostCommentAndAssertSuccess(postId, "a guy who comments", "commenter@example.com", "this is a test comment.")
        /** create a to-do by using the userId's reference */
        toDoClient.createUserToDoAndAssertSuccess(userId, "test to-do title", false)
        /** create an album by using the userId's reference and return its ID */
        int albumId = albumClient.createAlbumAndReturnId(userId, "test album title")
        /** create a photo by using the albumId's reference */
        photoClient.createAlbumPhotoAndAssertSuccess(albumId, "test photo title", "https://via.placeholder.com/600/92c952", "https://via.placeholder.com/150/92c952")
    }

    @Test
    void test_validateUserCreationIntegrity() {
        /** initialize variable to randomly returns a test scenario */
        Random random = new Random()
        int testScenario = random.nextInt(3) + 1

        /** initialize a list to store the user data (simulated local DB) */
        List<Map<String, Object>> users = new ArrayList<>()

        /** simulate scenarios based on the randomly generated number */
        switch (testScenario) {
            case 1:
                /** correct persistence */
                /** create a test data object */
                Map<String, Object> correctlyPersistedUserTestData = [
                        name    : "correctly persisted user",
                        username: "correctly_persisted_user",
                        email   : "correctlyPersisted@example.com"
                ]
                /** create a user using the test data */
                userClient.createUserAndAssertSuccess(
                        (String) correctlyPersistedUserTestData.name,
                        (String) correctlyPersistedUserTestData.username,
                        (String) correctlyPersistedUserTestData.email
                )
                /** add the user to the local list (simulated local DB) */
                Map<String, Object> user = [
                        name    : (String) correctlyPersistedUserTestData.name,
                        username: (String) correctlyPersistedUserTestData.username,
                        email   : (String) correctlyPersistedUserTestData.email
                ]

                users.add(user)

                /** assert that the user is present within the list (simulated local DB) */
                assert users.size() == 1
                assert users.get(0).name == (String) correctlyPersistedUserTestData.name
                assert users.get(0).username == (String) correctlyPersistedUserTestData.username
                assert users.get(0).email == (String) correctlyPersistedUserTestData.email

                println "Scenario 1: Correct persistence"

                break

            case 2:
                /** failed persistence */
                /** create a test data */
                Map<String, Object> notPersistedUserTestData = [
                        name    : "not persisted user",
                        username: "not_persisted_user",
                        email   : "notPersisted@example.com"
                ]
                /** create a user using the test data */
                userClient.createUserAndAssertSuccess(
                        (String) notPersistedUserTestData.name,
                        (String) notPersistedUserTestData.username,
                        (String) notPersistedUserTestData.email
                )
                /** simulate the failure to persist within the local DB (the user is not added due to some sporadic DB issue) */
                boolean persistenceSuccess = false /** simulating a DB issue */

                /** simulate checking if the user was added to the list (simulated local DB) */
                if (!persistenceSuccess) {
                    assert users.size() == 0

                    println "User creation was successful, but persistence failed."
                }

                println "Scenario 2: Failed persistence"

                break

            case 3:
                /** duplicated persistence */
                /** create a test data */
                Map<String, Object> duplicatedUserTestData = [
                        name    : "duplicated user",
                        username: "duplicated_user",
                        email   : "duplicated@example.com"
                ]
                /** create two users using the same test data */
                userClient.createUserMultipleTimes(
                        2,
                        (String) duplicatedUserTestData.name,
                        (String) duplicatedUserTestData.username,
                        (String) duplicatedUserTestData.email
                )
                /** simulate adding both users to the local list (simulated local DB) */
                List<Map<String, Object>> duplicatedUser = [
                        [
                                name    : (String) duplicatedUserTestData.name,
                                username: (String) duplicatedUserTestData.username,
                                email   : (String) duplicatedUserTestData.email
                        ],
                        [
                                name    : (String) duplicatedUserTestData.name,
                                username: (String) duplicatedUserTestData.username,
                                email   : (String) duplicatedUserTestData.email
                        ]
                ]

                users.addAll(duplicatedUser)

                /** define the expected duplicated user object */
                Map<String, Object> expectedDuplicatedUserData = [
                        name    : (String) duplicatedUserTestData.name,
                        username: (String) duplicatedUserTestData.username,
                        email   : (String) duplicatedUserTestData.email
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
            /** create a user */
            userClient.createUserAndAssertSuccess(
                    (String) userTestData.name,
                    (String) userTestData.username,
                    (String) userTestData.email
            )
            /** store the user data within its list reference */
            users.add([
                    id      : userId,
                    name    : (String) userTestData.name,
                    username: (String) userTestData.username,
                    email   : (String) userTestData.email
            ])

            /** define a to-do test data object */
            Map<String, Object> toDoTestData = [
                    title      : "To-Do for User $i",
                    isCompleted: false
            ] as Map<String, Object>
            /** create a to-do for each user */
            toDoClient.createUserToDoAndAssertSuccess(
                    userId,
                    (String) toDoTestData.title,
                    (Boolean) toDoTestData.isCompleted
            )
            /** store the to-do data within its list reference */
            toDos.add([
                    userId   : userId,
                    title    : (String) toDoTestData.title,
                    completed: (Boolean) toDoTestData.isCompleted
            ])
        }
        /** stop the timer */
        long endTime = System.currentTimeMillis()
        /** calculate the total time taken and print it in seconds */
        long totalTime = endTime - startTime

        println "Total time taken to create 100 users and 100 to-dos: ${totalTime / 1000} seconds"
    }
}
