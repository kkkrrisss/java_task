package Data;

public class TestStrings {
    public static final String JSON1 = """
            {
                "name": "loc1",
                "x": 0.5,
                "y": -0.5,
                "neighbors": [
                    "loc2", "loc3"
                ]
            }
            """;

    public static final String JSON2 = """
            [
                "a", "b", "c"
            ]
            """;

    public static final String JSON3 = """
            "abc"
            """;

    public static final String JSON4 = """
            {
                "float1": 0.5,
                "float2": -0.5,
                "string": "string",
                "bool": true,
                "map3": {"Key2": [{"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}, {"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}, {"name": null, "map": null}], "Key1": [{"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}, {"name": null, "map": null}, {"name": null, "map": null}], "Key3": [{"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}, {"name": null, "map": null}, {"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}]}, 
                "map2": {"Key2": [4, 5, 6], "Key1": [1, 2, 3], "Key3": [7, 8, 9]}, 
                "map1": {"Key2": {"name": null, "map": null}, "Key1": {"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}, "Key3": {"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}}, 
                "list": ["1", "2", "3"], 
                "inner": {"name": "name", "map": {"Key2": {"name": null}, "Key1": {"name": "name"}, "Key3": {"name": "name"}}}, 
                "int2": -10, 
                "int1": 10, 
                "aNull": null, 
                "array": ["4", "5", "6"]
            }
            """;
}
