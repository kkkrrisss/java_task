import Converter.ToObject;
import Converter.ToJson;
import Parser.Parser;
import Tokenizer.Tokenizer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Converter.Types;

public class JsonParser {

    //Serializes an object to JSON string
    public static <T> String toJson(T obj) {
        return ToJson.toJson(obj);
    }

    // Deserializes JSON string to a map, a list or to one of the simple types (String, Boolean, Integer etc.)
    public static Object toObject(String jsonString) {
        Tokenizer tokenizer = new Tokenizer(jsonString);
        Parser parser = new Parser(tokenizer.tokenize());
        Object obj = parser.parse();
        return obj;
    }

    //Deserializes JSON string to a Map<String, Object>

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String jsonString) {
        Object obj = toObject(jsonString);
        if (obj instanceof Map<?, ?>) {
            return (Map<String, Object>) obj;
        } else throw new ClassCastException("Object isn't the map");
    }

    //Deserializes JSON string to a List<Object>
    @SuppressWarnings("unchecked")
    public static List<Object> toList(String jsonString) {
        Object obj = toObject(jsonString);
        if (obj instanceof List<?>) {
            return (List<Object>) obj;
        } else throw new ClassCastException("Object isn't the array");
    }

    // Deserializes JSON string to a Set<Object>
    public static Set<Object> toSet(String jsonString) {
        return new HashSet<>(toList(jsonString));
    }

    //Deserializes JSON string to an array Object[]
    public static Object[] toArray(String jsonString) {
        return toList(jsonString).toArray();
    }

    //Deserializes JSON string to a Number
    public static Object toNumber(String jsonString, Class<?> type) {
        Object obj = toObject(jsonString);
        return ToObject.convertNumber((Number) obj, type);
    }

    // Deserializes JSON string to an object of specific type
    public static <T> T toInstanceOf(String jsonString, Class<T> type) {
        if (type.isPrimitive()) {
            throw new ClassCastException("Can't cast to primitive type");
        }

        if (Types.isString(type)) {
            return type.cast(toObject(jsonString));
        }
        if (Types.isList(type)) {
            return type.cast(toList(jsonString));
        }
        if (Types.isMap(type)) {
            return type.cast(toMap(jsonString));
        }
        if (Types.isSet(type)) {
            return type.cast(toSet(jsonString));
        }
        if (Types.isArray(type)) {
            return type.cast(toArray(jsonString));
        }
        if (Types.isNumber(type)) {
            return type.cast(toNumber(jsonString, type));
        }
        return ToObject.convertMapToObject(toMap(jsonString), type);
    }
}
