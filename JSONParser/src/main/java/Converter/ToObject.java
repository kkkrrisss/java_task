package Converter;

import java.lang.reflect.*;
import java.util.*;

public class ToObject {
    /**
     * Converts a number to a specific type
     *
     * @param number
     * @param type
     * @return
     */
    public static Object convertNumber(Number number, Class<?> type) {
        if (type == float.class || type == Float.class) {
            return number.floatValue();
        } else if (type == double.class || type == Double.class) {
            return number.doubleValue();
        } else if (type == int.class || type == Integer.class) {
            return number.intValue();
        } else if (type == long.class || type == Long.class) {
            return number.longValue();
        } else if (type == byte.class || type == Byte.class) {
            return number.byteValue();
        } else if (type == short.class || type == Short.class) {
            return number.shortValue();
        } else {
            throw new ConversionException("Source field type is number, destination one is not number");
        }
    }

    /**
     * Converts a map read from JSON to a specific type
     * @param map
     * @param type
     * @return
     * @param <T>
     */
    public static <T> T convertMapToObject(Map<String, Object> map, Class<T> type) {
        Set<String> fieldNames = new HashSet<>();
        for (var field : type.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }

        // Check keys: all names should be present in Class<T>
        for (var key : map.keySet()) {
            if (!fieldNames.contains(key)) {
                String message = String.format("Field %s does not present in class %s", key, type.getName());
                throw new ConversionException(message);
            }
        }

        T obj;
        try {
            obj = type.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ConversionException(e.getMessage());
        }

        for (var field : type.getDeclaredFields()) {
            boolean accessible = field.canAccess(obj);
            if (!accessible) {
                field.setAccessible(true);
            }

            Object fieldValue = null;

            if (map.containsKey(field.getName())) {
                Object value = map.get(field.getName());
                ParameterizedType parameterizedType = null;
                if (field.getGenericType() instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) field.getGenericType();
                }
                fieldValue = convertValue(value, field.getType(), parameterizedType);
            }

            try {
                field.set(obj, fieldValue);
            } catch (IllegalAccessException e) {
                throw new ConversionException(e.getMessage());
            }

            if (!accessible) {
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Converts a value to a type
     * @param value
     * @param type
     * @param parameterizedType
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Object convertValue(Object value, Class<?> type, ParameterizedType parameterizedType) {
        if (value == null) {
            return null;
        }
        if (type == boolean.class || type == Boolean.class) {
            return value;
        }
        if (Types.isString(type)) {
            return value;
        }
        if (Types.isNumber(type) || Types.isPrimitiveNumber(type)) {
            return convertNumber((Number) value, type);
        }
        if (type.isArray()) {
            return convertToArray((List<?>) value, type.componentType());
        }
        if (Types.isList(type)) {
            List<Object> list = (List<Object>) type.cast(value);
            list.replaceAll(o -> convertGenericValue(o, parameterizedType));
            return value;
        }

        if (!Types.isMap(value)) {
            throw new ConversionException("Internal error");
        }
        Map<String, Object> map = (Map<String, Object>) value;

        if (Types.isMap(type)) {
            for (var key : map.keySet()) {
                Object mapValue = map.get(key);
                map.put(key, convertGenericValue(mapValue, parameterizedType));
            }
            return value;
        }

        // Complex object
        return convertMapToObject(map, type);
    }

    /**
     * Conversion for collection items
     *
     * @param value a value to convert
     * @param parameterizedType a parametrized type of item
     * @return
     */
    private static Object convertGenericValue(Object value, ParameterizedType parameterizedType) {
        Class<?> valueClassType;
        Type valueType;
        if (Types.isMapEntry(parameterizedType)) {
            valueType = parameterizedType.getActualTypeArguments()[1];
        } else {
            valueType = parameterizedType.getActualTypeArguments()[0];
        }

        if (valueType instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) valueType;
            valueType = ((ParameterizedType) valueType).getRawType();
        }

        try {
            valueClassType = Class.forName(valueType.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new ConversionException(e.getMessage());
        }
        return convertValue(value, valueClassType, parameterizedType);
    }

    /**
     * Converts a list to an array
     *
     * @param objects   a list to convert
     * @param itemClass an item class
     * @param <T>       an item type
     * @return an array
     */
    private static <T> T[] convertToArray(List<?> objects, Class<T> itemClass) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(itemClass, objects.size());
        for (int i = 0; i < objects.size(); ++i) {
            array[i] = itemClass.cast(objects.get(i));
        }
        return array;
    }
}
