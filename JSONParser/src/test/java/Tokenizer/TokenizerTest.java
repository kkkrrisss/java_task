package Tokenizer;

import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    static final String JSON_STR = """
            {
                "name": "loc1",
                "pos_x": 0.5,
                "pos_y": -0.5,
                "neighbors": [
                    "loc2", "loc3"
                ]
            }
            """;

    @org.junit.jupiter.api.Test
    void tokenize() {
        Tokenizer tokenizer = new Tokenizer(JSON_STR);
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(21, tokens.size());
        assertEquals(TokenType.LeftBrace, tokens.get(0).type);
        assertEquals(TokenType.String, tokens.get(1).type);
        assertEquals(TokenType.Colon, tokens.get(2).type);
        assertEquals(TokenType.String, tokens.get(3).type);
        assertEquals(TokenType.Comma, tokens.get(4).type);
        assertEquals(TokenType.String, tokens.get(5).type);
        assertEquals(TokenType.Colon, tokens.get(6).type);
        assertEquals(TokenType.Number, tokens.get(7).type);
        assertEquals(TokenType.Comma, tokens.get(8).type);
        assertEquals(TokenType.String, tokens.get(9).type);
        assertEquals(TokenType.Colon, tokens.get(10).type);
        assertEquals(TokenType.Number, tokens.get(11).type);
        assertEquals(TokenType.Comma, tokens.get(12).type);
        assertEquals(TokenType.String, tokens.get(13).type);
        assertEquals(TokenType.Colon, tokens.get(14).type);
        assertEquals(TokenType.LeftBracket, tokens.get(15).type);
        assertEquals(TokenType.String, tokens.get(16).type);
        assertEquals(TokenType.Comma, tokens.get(17).type);
        assertEquals(TokenType.String, tokens.get(18).type);
        assertEquals(TokenType.RightBracket, tokens.get(19).type);
        assertEquals(TokenType.RightBrace, tokens.get(20).type);
    }
}