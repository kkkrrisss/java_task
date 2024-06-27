# JSONParser
- Do not use external libraries
- Read JSON string
  - To Java Object
  - To Map<String, Object>
  - *To specified class*
- Convert Java object to JSON string
- Library should support
  - Classes with fields (primitives, boxing types, null, arrays, classes)
  - Arrays
  - Collections
- Limitations (you may skip implementation)
  - Cyclic dependencies
  - non-representable in JSON types

The library was written using Java 20.

## The composition of the program

The program consists of several modules.

- The «Tokenizer» package contains classes for the initial processing of a JSON string, which divide the string into separate tokens and simultaneously check the correctness of the string.

- The «Parser» package contains a class of the same name that parses a JSON string using a list of tokens obtained by scanning the string. The parser returns one of three types of objects: either a dictionary, if the string had the form "{"key1": value1, ...}", or a list, if the string had the form "[value1, ...]", or an object of a simple type (string, number, etc.), if the string it had the form "value".

- The «Converter» package contains classes for various transformations of objects. The «toJSON» class implements static functions for serializing various types of objects into a JSON string. The `Tobject' class implements static functions for inverse transformations.

- The «Json Parser» class provides a user interface for working with the library.

## Use
- Object serialization is very simple:
    ```
    String json = JsonParser.toJson(someObject);
    ```

- Deserialization of JSON into the dictionary 'Map<String, Object>' (if possible, the string should have the form "{"key1": value1, ...}"):
    ```
    Map<String, Object> map = JsonParser.toMap(someJsonString);
    ```

- Deserializing JSON to the list 'List<Object>' (if possible, the string should have the form "[value1, ...]"):
    ```
    List<Object> list = JsonParser.toList(someJsonString);
    ```
  
- The same string in the form of an array can also be converted to an array 'Object[]' and to a set 'Set<Object>':
    ```
    Object[] arr = JsonParser.toArray(someJsonString);
    Set<Object> set = JsonParser.toSet(someJsonString);
    ```
- Deserializing JSON into a simple object (for example, a string or a number if the string has the form "value"):
    ```
    String str = (String) JsonParser.toObject(someJsonString);
    Integer num = JsonParser.toNumber(someJsonString, Integer.class);
    ```

- Deserializing JSON into a specific class:
    ```
    SomeClass obj = JsonParser.toInstanceOf(someJsonString, SomeClass.class);
    ```
## Test
The project is equipped with unit tests to test all aspects of the library's functioning. The main test performs serialization and deserialization of an object of a rather complex class with many fields containing both objects of nested classes and various nested collections, and checks that the restored object exactly matches the original one.
