package Parser;

import Tokenizer.Token;
import Tokenizer.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Object parse() throws ParserException {
        if (tokens.isEmpty()) {
            throw new ParserException("Empty json.");
        }

        if (peek().type == TokenType.LeftBrace) {
            return parseObject();
        } else if (peek().type == TokenType.LeftBracket) {
            return parseArray();
        } else {
            return parseValue();
        }
    }

    private Object parseObject() throws ParserException {
        Map<String, Object> obj = new HashMap<>();

        accept(TokenType.LeftBrace);
        while (peek().type != TokenType.RightBrace) {
            Token key = accept(TokenType.String);
            accept(TokenType.Colon);
            Object value = parseValue();
            obj.put(key.value, value);

            if (peek().type == TokenType.Comma) {
                accept(TokenType.Comma);
            } else if (peek().type != TokenType.RightBrace) {
                throw new ParserException("Invalid syntax");
            }
        }
        accept(TokenType.RightBrace);

        return obj;
    }

    private Object parseArray() throws ParserException {
        List<Object> array = new ArrayList<>();

        accept(TokenType.LeftBracket);
        while (peek().type != TokenType.RightBracket) {
            array.add(parseValue());

            if (peek().type == TokenType.Comma) {
                accept(TokenType.Comma);
            } else if (peek().type != TokenType.RightBracket) {
                throw new ParserException("Invalid syntax");
            }
        }
        accept(TokenType.RightBracket);

        return array;
    }

    private Object parseValue() throws ParserException {
        Token token = peek();
        return switch (token.type) {
            case Number -> {
                accept(token.type);
                yield parseNumber(token.value);
            }
            case String -> {
                accept(token.type);
                yield token.value;
            }
            case True, False -> {
                accept(token.type);
                yield Boolean.parseBoolean(token.value);
            }
            case Null -> {
                accept(token.type);
                yield null;
            }
            case LeftBrace -> parseObject();
            case LeftBracket -> parseArray();
            default -> throw new ParserException("Unexpected value type: " + token.type);
        };
    }

    private static Number parseNumber(String s) throws ParserException {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e1) {
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e2) {
                throw new ParserException("No conversion for number " + s);
            }
        }
    }

    private Token accept(TokenType type) throws ParserException {
        Token token = next();
        if (token.type != type) {
            String message = String.format("Expected type %s but type %s is found.", type.toString(), token.type.toString());
            throw new ParserException(message);
        }
        return token;
    }

    private boolean isEOF() {
        return pos >= tokens.size();
    }

    private Token next() throws ParserException {
        if (isEOF()) {
            throw new ParserException("Index out of bounds.");
        }
        return tokens.get(pos++);
    }

    public Token peek() throws ParserException {
        if (isEOF()) {
            throw new ParserException("Index out of bounds.");
        }
        return tokens.get(pos);
    }
}
