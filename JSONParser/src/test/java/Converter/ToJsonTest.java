package Converter;

import Data.TestClass;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class ToJsonTest {

    @Test
    void toJson() {
        // Arrays
        Integer[] integers = {1, 2, 3};
        String json = ToJson.toJson(integers);
        System.out.println(json);

        String[] strings = {"1", "2", "3"};
        json = ToJson.toJson(strings);
        System.out.println(json);

        // Lists
        json = ToJson.toJson(Arrays.stream(integers).toList());
        System.out.println(json);

        json = ToJson.toJson(Arrays.stream(strings).toList());
        System.out.println(json);

        // Maps
        Map<String, Object> map = new HashMap<>();
        map.put("Key1", "Value1");
        map.put("Key2", "Value2");
        map.put("Key3", "Value3");
        json = ToJson.toJson(map);
        System.out.println(json);

        // Sets
        json = ToJson.toJson(Arrays.stream(strings).collect(Collectors.toSet()));
        System.out.println(json);

        // Objects
        TestClass testClass = new TestClass(true);
        json = ToJson.toJson(testClass);
        System.out.println(json);
    }

    @Test
    void nullToJson() {
        String json = ToJson.toJson(null);
        System.out.println(json);
    }

    @Test
    void booleanToJson() {
        String json = ToJson.toJson(true);
        System.out.println(json);
    }

    @Test
    void numberToJson() {
        String json = ToJson.toJson(1e100);
        System.out.println(json);
    }

    @Test
    void stringToJson() {
        String json = ToJson.toJson("Hello");
        System.out.println(json);
    }

    @Test
    void arrayToJson() {
        Integer[] integers = {1, 2, 3};
        String json1 = ToJson.arrayToJson(integers);
        System.out.println(json1);

        String[] strings = {"1", "2", "3"};
        String json2 = ToJson.arrayToJson(strings);
        System.out.println(json2);
    }

    @Test
    void listToJson() {
        Integer[] integers = {1, 2, 3};
        String json1 = ToJson.listToJson(Arrays.stream(integers).toList());
        System.out.println(json1);

        String[] strings = {"1", "2", "3"};
        String json2 = ToJson.listToJson(Arrays.stream(strings).toList());
        System.out.println(json2);
    }

    @Test
    void mapToJson() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("Key1", "Value1");
        map1.put("Key2", "Value2");
        map1.put("Key3", "Value3");
        String json1 = ToJson.mapToJson(map1);
        System.out.println(json1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("Key1", 1);
        map2.put("Key2", 2);
        map2.put("Key3", 3);
        String json2 = ToJson.mapToJson(map2);
        System.out.println(json2);
    }

    @Test
    void setToJson() {
        Integer[] integers = {1, 2, 3};
        String json1 = ToJson.setToJson(Arrays.stream(integers).collect(Collectors.toSet()));
        System.out.println(json1);

        String[] strings = {"1", "2", "3"};
        String json2 = ToJson.setToJson(Arrays.stream(strings).collect(Collectors.toSet()));
        System.out.println(json2);
    }

    @Test
    void objectToJson() {
        TestClass testClass = new TestClass(true);
        String json1 = ToJson.objectToJson(testClass);
        System.out.println(json1);
    }
}