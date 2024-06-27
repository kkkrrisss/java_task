package Data;

import java.util.*;

public class TestClass {
    public static class InnerClass {
        public static class InnerInnerClass {
            private String name;

            public InnerInnerClass() {
            }

            public InnerInnerClass(boolean create) {
                if (create) {
                    name = "name";
                }
            }

            @Override
            public String toString() {
                return "InnerInnerClass{" +
                        "name='" + name + '\'' +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof InnerInnerClass that)) return false;
                return Objects.equals(name, that.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name);
            }
        }

        private String name;

        private Map<String, InnerInnerClass> map;

        public InnerClass() {
        }

        public InnerClass(boolean create) {
            if (create) {
                name = "name";
                map = new HashMap<>();
                map.put("Key1", new InnerInnerClass(true));
                map.put("Key2", new InnerInnerClass(false));
                map.put("Key3", new InnerInnerClass(true));
            }
        }

        @Override
        public String toString() {
            return "InnerClass{" +
                    "name='" + name + '\'' +
                    ", map=" + map +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof InnerClass that)) return false;
            return Objects.equals(name, that.name) && Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, map);
        }
    }

    private String string;
    private boolean bool;
    private int int1;
    private Integer int2;
    private float float1;
    private Float float2;
    private List<String> list;
    private String[] array;
    private Map<String, InnerClass> map1;
    private Map<String, List<Integer>> map2;
    private Map<String, List<InnerClass>> map3;
    private Object aNull;
    private InnerClass inner;

    public TestClass() {
    }

    public TestClass(boolean create) {
        if (create) {
            string = "string";
            bool = true;
            int1 = 10;
            int2 = -10;
            float1 = 0.5f;
            float2 = -0.5f;
            list = Arrays.stream(new String[]{"1", "2", "3"}).toList();
            array = new String[]{"4", "5", "6"};
            map1 = new HashMap<>();
            map1.put("Key1", new InnerClass(true));
            map1.put("Key2", new InnerClass(false));
            map1.put("Key3", new InnerClass(true));
            map2 = new HashMap<>();
            map2.put("Key1", Arrays.stream(new Integer[]{1, 2, 3}).toList());
            map2.put("Key2", Arrays.stream(new Integer[]{4, 5, 6}).toList());
            map2.put("Key3", Arrays.stream(new Integer[]{7, 8, 9}).toList());
            map3 = new HashMap<>();
            map3.put("Key1", Arrays.stream(new InnerClass[]{new InnerClass(true), new InnerClass(), new InnerClass()}).toList());
            map3.put("Key2", Arrays.stream(new InnerClass[]{new InnerClass(true), new InnerClass(true), new InnerClass()}).toList());
            map3.put("Key3", Arrays.stream(new InnerClass[]{new InnerClass(true), new InnerClass(), new InnerClass(true)}).toList());
            aNull = null;
            inner = new InnerClass(true);
        }
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "string='" + string + '\'' +
                ", bool=" + bool +
                ", int1=" + int1 +
                ", int2=" + int2 +
                ", float1=" + float1 +
                ", float2=" + float2 +
                ", list=" + list +
                ", array=" + Arrays.toString(array) +
                ", map1=" + map1 +
                ", map2=" + map2 +
                ", map3=" + map3 +
                ", aNull=" + aNull +
                ", inner=" + inner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestClass testClass)) return false;
        return bool == testClass.bool && int1 == testClass.int1 && Float.compare(float1, testClass.float1) == 0 && Objects.equals(string, testClass.string) && Objects.equals(int2, testClass.int2) && Objects.equals(float2, testClass.float2) && Objects.equals(list, testClass.list) && Arrays.equals(array, testClass.array) && Objects.equals(map1, testClass.map1) && Objects.equals(map2, testClass.map2) && Objects.equals(map3, testClass.map3) && Objects.equals(aNull, testClass.aNull) && Objects.equals(inner, testClass.inner);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(string, bool, int1, int2, float1, float2, list, map1, map2, map3, aNull, inner);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}
