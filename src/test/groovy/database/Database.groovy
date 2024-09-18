package database

/**
 * Database interface, simulating an embedded database
 * */
interface Database<T> {
    /**
     * HashMap "database" (key-value pairs), storing different types of resources (users, todos, etc...)
     * the key should be a string ("users", "todos", etc...) and the value should be a list of objects of type T
     * T represents a generic type, which means it can store different types of data (user data, post data, etc...)
     * */
    Map<String, List<T>> database = new HashMap<>()

    /**
     * HashSet userIds, storing the unique user IDs for our performance test
     * */
    Set<Integer> userIds = new HashSet<>()

    /**
     * add a resource (user, to-do, etc...) within the DB
     * the "key" is the resource type (like "users"), and "value" is the actual resource object
     * */
    default void addToDB(String key, T value) {
        /** if the key doesn't exist in the database map, initialize an empty ArrayList for it */
        if (!database.containsKey(key)) {
            database.put(key, new ArrayList<>())
        }
        /** add the value to the list of values for the given key */
        database.get(key).add(value)
    }

    /**
     * get a resource (user, to-do, etc...) from the DB
     * the "key" is the resource type ("users", etc...)
     * */
    default List<T> getFromDB(String key) {
        return database.get(key)
    }
}
