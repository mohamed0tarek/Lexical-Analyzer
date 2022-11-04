package phase1.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

class LexicalAnalyzer {

    private final Map<String, Token> allPossibleTokens;

    public LexicalAnalyzer() {
        this.allPossibleTokens = new HashMap<>();
        allPossibleTokens.put("for", Token.KEYWORD);
        allPossibleTokens.put("while", Token.KEYWORD);
        allPossibleTokens.put("do", Token.KEYWORD);
        allPossibleTokens.put("if", Token.KEYWORD);
        allPossibleTokens.put("else", Token.KEYWORD);
        allPossibleTokens.put("print", Token.KEYWORD);
        allPossibleTokens.put("switch", Token.KEYWORD);
        allPossibleTokens.put("case", Token.KEYWORD);
        allPossibleTokens.put("default", Token.KEYWORD);
        allPossibleTokens.put("null", Token.KEYWORD);
        allPossibleTokens.put("system.out.println", Token.PREDEFINED_METHOD);
        allPossibleTokens.put("+", Token.PLUS);
        allPossibleTokens.put("-", Token.MINUS);
        allPossibleTokens.put("*", Token.TIMES);
        allPossibleTokens.put("/", Token.DIVIDE);
        allPossibleTokens.put(".", Token.DOT);
        allPossibleTokens.put(",", Token.COMMA);
        allPossibleTokens.put("=", Token.EQUAL);
        allPossibleTokens.put(":", Token.COLON);
        allPossibleTokens.put(";", Token.SEMICOLON);
        allPossibleTokens.put("(", Token.LEFT_PARENTHESIS);
        allPossibleTokens.put(")", Token.RIGHT_PARENTHESIS);
        allPossibleTokens.put(">=", Token.GREATER_OR_EQUALS);
        allPossibleTokens.put("<=", Token.LOWER_OR_EQUALS);
        allPossibleTokens.put(">", Token.GREATER_THAN);
        allPossibleTokens.put("<", Token.LOWER_THAN);
        allPossibleTokens.put("<>", Token.NOT_EQUALS);
        allPossibleTokens.put(":=", Token.ASSIGNMENT_OPERATOR);
        allPossibleTokens.put("@", Token.AT_SIGN);
        allPossibleTokens.put("{", Token.LEFT_BRACE);
        allPossibleTokens.put("}", Token.RIGHT_BRACE);
    }

    public void analyzeCode(Path path) {
        try {
            List<String> lines = readLazily(path);
            lines.forEach(line -> {
                Map<String, Token> analysisResult = analyzeLine(line.strip());
                analysisResult.forEach((value,token)-> System.out.println(value + " --> " + token));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Token> analyzeLine(String line) {
        Map<String, Token> tokensFounded = new HashMap<>();
        Automata automata = new Automata();
        for (String str : line.split(" ")) {
            if (allPossibleTokens.containsKey(str.toLowerCase()))
                tokensFounded.put(str, allPossibleTokens.get(str.toLowerCase()));
            else
                tokensFounded.put(str, automata.evaluate(str));
        }
        return tokensFounded;
    }

    List<String> readLazily(Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        try (Stream<String> s = Files.lines(path)){
            s.forEach(lines::add);
        }
        return lines;
    }
}

