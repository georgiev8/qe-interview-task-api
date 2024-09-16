package resources

import database.*

class DatabaseClient implements Database<Map<String, Object>> {
    static void simulateMissingEntityInMemory(String resource, String key, String value) {
        /** retrieve a resource from memory */
        List<Map<String, Object>> resources = getFromMemory(resource)

        /** simulate that entity is not persisted in the DB */
        resources.each { resourceMap ->
            if (resourceMap.get(key) == value) {
                /** "id" becomes NULL due to some bug. All resources have IDs so that's the best key for the job */
                resourceMap.put("id", null)
            }
        }

        /** retrieve the buggy resource from db and catch the bug */
        List<Map<String, Object>> buggyResources = getFromMemory(resource)

        assert buggyResources == resources
    }

    static void simulateDuplicateEntityInMemory(String resource, String key, String value) {
        /** retrieve a resource from memory */
        List<Map<String, Object>> resources = getFromMemory(resource)

        /** simulate that entity is duplicated in the DB */
        resources.each { resourceMap ->
            if (resourceMap.get(key) == value) {
                /** "id" gets duplicated due to some bug. All resources have IDs so that's the best key for the job */
                resourceMap.put("id", resourceMap.get("id").toString().concat(" ").concat(resourceMap.get("id").toString()))
            }
        }

        /** retrieve the buggy resource from db and catch the bug */
        List<Map<String, Object>> buggyResources = getFromMemory(resource)

        assert buggyResources == resources
    }
}
