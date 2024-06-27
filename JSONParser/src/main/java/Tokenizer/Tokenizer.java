package Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String json;
    private int pos = 0;

    public Tokenizer(String jsonString) {
        this.json = jsonString;
    }

    private boolean isWhitespace() {
        return Character.isWhitespace(json.charAt(pos));
    }

    private boolean isDigit() {
        return Character.isDigit(json.charAt(pos));
    }

    private boolean isDelimiter() {
        char c = json.charAt(pos);
        return Character.isWhitespace(c) ||
                c == ',' || c == ':' ||
                c == '[' || c == '{' ||
                c == ']' || c == '}' ||
                c == '\"';
    }


    private boolean isEOF() {
        return pos == json.length();
    }

    private boolean parseNull() {
        if (json.indexOf("null", pos) == pos) {
            pos += "null".length();
            return isEOF() || isDelimiter();
        }
        return false;
    }

    private boolean parseTrue() {
        if (json.indexOf("true", pos) == pos) {
            pos += "true".length();
            return isEOF() || isDelimiter();
        }
        return false;
    }

    private boolean parseFalse() {
        if (json.indexOf("false", pos) == pos) {
            pos += "false".length();
            return isEOF() || isDelimiter();
        }
        return false;
    }

    private String parseString() {
        int start = ++pos;
        while (!isEOF() && json.charAt(pos) != '\"') {
            pos++;
        }
        if (isEOF()) {
            throw new TokenizerException("Unclosed quote");
        }
        return json.substring(start, pos++);
    }

    private boolean isNumber() {
        char c = json.charAt(pos);
        return Character.isDigit(c) || c == '-';
    }

    private String parseNumber() {
        StringBuilder number = new StringBuilder();

        // Optional: sign
        if (json.charAt(pos) == '-') {
            number.append(json.charAt(pos++));
        }

        // Required: one or more digits
        if (isEOF() || !isDigit()) {
            throw new TokenizerException("Not number");
        }
        while (!isEOF() && isDigit()) {
            number.append(json.charAt(pos++));
        }

        // Optional: fractional part
        if (!isEOF() && json.charAt(pos) == '.') {
            number.append(json.charAt(pos++));
            // Required: one or more digits
            if (isEOF() || !isDigit()) {
                throw new TokenizerException("Not number");
            }
            while (!isEOF() && isDigit()) {
                number.append(json.charAt(pos++));
            }
        }
        // Optional: exponent part
        if (!isEOF() && (json.charAt(pos) == 'e' || json.charAt(pos) == 'E')) {
            number.append(json.charAt(pos++));
            // Optional: sign
            if (json.charAt(pos) == '+' || json.charAt(pos) == '-') {
                number.append(json.charAt(pos++));
            }
            // Required: one or more digits
            if (isEOF() || !isDigit()) {
                throw new TokenizerException("Not number");
            }
            while (!isEOF() && isDigit()) {
                number.append(json.charAt(pos++));
            }
        }
        // Required: delimiter after number
        if (!isEOF() && !isDelimiter()) {
            throw new TokenizerException("Not number");
        }
        return number.toString();
    }

    private void skipSpaces() {
        while (!isEOF() && isWhitespace()) {
            pos++;
        }
    }

    public List<Token> tokenize() {
        ArrayList<Token> tokens = new ArrayList<>();

        skipSpaces();
        Token token = null;
        while (!isEOF()) {
            if (json.charAt(pos) == ',') {
                token = new Token(TokenType.Comma, ",");
                pos++;
            } else if (json.charAt(pos) == ':') {
                token = new Token(TokenType.Colon, ":");
                pos++;
            } else if (json.charAt(pos) == '[') {
                token = new Token(TokenType.LeftBracket, "[");
                pos++;
            } else if (json.charAt(pos) == ']') {
                token = new Token(TokenType.RightBracket, "]");
                pos++;
            } else if (json.charAt(pos) == '{') {
                token = new Token(TokenType.LeftBrace, "{");
                pos++;
            } else if (json.charAt(pos) == '}') {
                token = new Token(TokenType.RightBrace, "}");
                pos++;
            } else if (parseNull()) {
                token = new Token(TokenType.Null, "null");
            } else if (parseTrue()) {
                token = new Token(TokenType.True, "true");
            } else if (parseFalse()) {
                token = new Token(TokenType.False, "false");
            } else if (json.charAt(pos) == '\"') {
                String string = parseString();
                token = new Token(TokenType.String, string);
            } else if (isNumber()) {
                String number = parseNumber();
                token = new Token(TokenType.Number, number);
            } else {
                throw new TokenizerException("Invalid token");
            }
            tokens.add(token);
            skipSpaces();
        }

        return tokens;
    }
}
