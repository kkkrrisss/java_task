import Converter.ToJson;
import Data.TestClass;
import Data.TestStrings;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void toMap() {
        Map<String, Object> map = JsonParser.toMap(TestStrings.JSON1);

        assertTrue(map.containsKey("name"));
        assertEquals("loc1", map.get("name"));

        assertTrue(map.containsKey("x"));
        assertEquals(0.5, map.get("x"));
        assertTrue(map.containsKey("y"));
        assertEquals(-0.5, map.get("y"));

        assertTrue(map.containsKey("neighbors"));
        Object obj2 = map.get("neighbors");
        assertInstanceOf(List.class, obj2);
        List<String> arr = (List<String>) obj2;
        assertEquals(2, arr.size());
        assertEquals("loc2", arr.get(0));
        assertEquals("loc3", arr.get(1));
    }

    @Test
    void toList() {
        List<Object> list = JsonParser.toList(TestStrings.JSON2);
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    void toSet() {
        Set<Object> set = JsonParser.toSet(TestStrings.JSON2);
        assertEquals(3, set.size());
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
    }

    @Test
    void toArray() {
        Object[] arr = JsonParser.toArray(TestStrings.JSON2);
        assertEquals(3, arr.length);
        assertEquals("a", arr[0]);
        assertEquals("b", arr[1]);
        assertEquals("c", arr[2]);
    }

    @Test
    void toNumber() {
        Object obj1 = JsonParser.toNumber("5", Integer.class);
        assertInstanceOf(Integer.class, obj1);
        assertEquals(5, obj1);

        Object obj2 = JsonParser.toNumber("5", Double.class);
        assertInstanceOf(Double.class, obj2);
        assertEquals(5.0, obj2);
    }

    @Test
    void toObject() {
        Object obj = JsonParser.toObject(TestStrings.JSON3);
        assertInstanceOf(String.class, obj);
        assertEquals("abc", obj);
    }

    @Test
    void toInstanceOf() {
        TestClass obj = JsonParser.toInstanceOf(TestStrings.JSON4, TestClass.class);
        assertInstanceOf(TestClass.class, obj);
        System.out.println(obj);
    }

    @Test
    void toJson() {
        TestClass testClass1 = new TestClass(true);
        String json = JsonParser.toJson(testClass1);
        System.out.println(json);
        TestClass testClass2 = JsonParser.toInstanceOf(json, TestClass.class);
        // Исходный и восстановленный объекты должны быть идентичны
        assertEquals(testClass1,testClass2);
    }
}