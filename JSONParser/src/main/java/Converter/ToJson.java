package Converter;

import java.util.*;

public class ToJson {
    /**
     * Serializes an object to JSON string
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj.getClass().isArray()) {
            return arrayToJson(obj);
        }
        if (obj instanceof List<?>) {
            return listToJson((List<Object>) obj);
        }
        if (obj instanceof Map<?, ?>) {
            return mapToJson((Map<String, Object>) obj);
        }
        if (obj instanceof Set<?>) {
            return setToJson((Set<?>) obj);
        }
        if (obj instanceof String) {
            return "\"" + obj + "\"";
        }
        if (obj instanceof Number) {
            return obj.toString();
        }
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        return objectToJson(obj);
    }


    /**
     * Serializes an array to JSON string
     * @param obj
     * @return
     */
    public static String arrayToJson(Object obj) {
        return listToJson(arrayToList(obj));
    }

    /**
     * Serializes a list to JSON string
     * @param list
     * @return
     * @param <T>
     */
    public static <T> String listToJson(List<T> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (!list.isEmpty()) {
            sb.append(toJson(list.get(0)));
        }
        for (int i = 1; i < list.size(); ++i) {
            sb.append(", ");
            sb.append(toJson(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Serializes a map to JSON string
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        var keys = map.keySet().stream().toList();
        if (!keys.isEmpty()) {
            sb.append(toJson(keys.get(0)));
            sb.append(": ");
            sb.append(toJson(map.get(keys.get(0))));
        }
        for (int i = 1; i < keys.size(); ++i) {
            sb.append(", ");
            sb.append(toJson(keys.get(i)));
            sb.append(": ");
            sb.append(toJson(map.get(keys.get(i))));
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Serializes a set to JSON string
     * @param set
     * @return
     * @param <T>
     */
    public static <T> String setToJson(Set<T> set) {
        return listToJson(set.stream().toList());
    }

    /**
     * Serializes object to JSON string
     * @param obj
     * @return
     * @param <T>
     */
    public static <T> String objectToJson(T obj) {
        Map<String, Object> map = objectToMap(obj);
        return mapToJson(map);
    }

    /**
     * Converts an object to map representation
     * @param obj
     * @return
     * @param <T>
     */
    private static <T> Map<String, Object> objectToMap(T obj) {
        Map<String, Object> map = new HashMap<>();
        for (var f : obj.getClass().getDeclaredFields()) {
            boolean accessible = f.canAccess(obj);
            if (!accessible) {
                f.setAccessible(true);
            }

            String key = f.getName();
            try {
                Object value = f.get(obj);
                map.put(key, value);
            } catch (IllegalAccessException e) {
                throw new ConversionException(e.getMessage());
            } finally {
                if (!accessible) {
                    f.setAccessible(false);
                }
            }
        }

        return map;
    }

    private static <T> List<T> arrayToList(Object obj) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) obj;
        return Arrays.stream(array).toList();
    }
}
