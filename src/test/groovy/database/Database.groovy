package database

/**
 * Database interface, simulating an embedded database
 * */
interface Database<T> {
    /**
     * HashMap memory (key-value pairs), storing different types of resources (users, todos, etc...)
     * the key should be a string ("users", "todos", etc...) and the value should be a list of objects of type T
     * T represents a generic type, which means it can store different types of data (user data, post data, etc...)
     * */
    Map<String, List<T>> memory = new HashMap<>()

    /**
     * HashSet userIds, storing the unique user IDs for our performance test
     * */
    Set<Integer> userIds = new HashSet<>()

    /**
     * add a resource (user, to-do, etc...) within the DB
     * the "key" is the resource type (like "users"), and "value" is the actual resource object
     * */
    default void addToMemory(String key, T value) {
        /** if the key doesn't exist in the memory map, initialize an empty ArrayList for it */
        if (!memory.containsKey(key)) {
            memory.put(key, new ArrayList<>())
        }
        /** add the value to the list of values for the given key */
        memory.get(key).add(value)
    }

    /**
     * get a resource (user, to-do, etc...) from the DB
     * the "key" is the resource type ("users", etc...)
     * */
    default List<T> getFromMemory(String key) {
        return memory.get(key)
    }
}
