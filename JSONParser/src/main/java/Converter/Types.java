package Converter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Types {
    public static <T> boolean isList(T obj) {
        return isList(obj.getClass());
    }

    public static <T> boolean isMap(T obj) {
        return isMap(obj.getClass());
    }

    public static <T> boolean isSet(T obj) {
        return isSet(obj.getClass());
    }

    public static <T> boolean isArray(T obj) {
        return isArray(obj.getClass());
    }

    public static <T> boolean isString(T obj) {
        return isString(obj.getClass());
    }

    public static <T> boolean isNumber(T obj) {
        return isNumber(obj.getClass());
    }

    public static <T> boolean isList(Class<T> type) {
        if (type == java.util.List.class) {
            return true;
        }
        var interfaces = Arrays.stream(type.getInterfaces()).toList();
        return interfaces.contains(java.util.List.class);
    }

    public static <T> boolean isMap(Class<T> type) {
        if (type == java.util.Map.class) {
            return true;
        }
        var interfaces = Arrays.stream(type.getInterfaces()).toList();
        return interfaces.contains(java.util.Map.class);
    }

    public static <T> boolean isSet(Class<T> type) {
        if (type == java.util.Set.class) {
            return true;
        }
        var interfaces = Arrays.stream(type.getInterfaces()).toList();
        return interfaces.contains(java.util.Set.class);
    }

    public static <T> boolean isArray(Class<T> type) {
        return type.isArray();
    }

    public static <T> boolean isString(Class<T> type) {
        return type.equals(String.class);
    }

    public static <T> boolean isNumber(Class<T> type) {
        if (type == Number.class) {
            return true;
        }
        var superclass = type.getSuperclass();
        return superclass != null && superclass.equals(Number.class);
    }

    public static <T> boolean isPrimitiveNumber(Class<T> type) {
        return type == byte.class || type == short.class || type == int.class ||
                type == long.class || type == float.class || type == double.class;
    }

    public static boolean isMapEntry(ParameterizedType type) {
        return type.getActualTypeArguments().length == 2;
    }
}
