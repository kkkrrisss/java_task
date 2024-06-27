package Parser;

import Data.TestClass;
import org.junit.jupiter.api.Test;
import Data.TestStrings;
import Tokenizer.Tokenizer;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        Tokenizer tokenizer = new Tokenizer(TestStrings.JSON1);
        Parser parser = new Parser(tokenizer.tokenize());
        Object obj = parser.parse();
        assertInstanceOf(Map.class, obj);
        Map<String, Object> map = (Map<String, Object>)obj;

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
}