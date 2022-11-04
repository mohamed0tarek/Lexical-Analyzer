package phase1.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path file = Path.of("src/main/resources/code.txt");
        LexicalAnalyzer le = new LexicalAnalyzer();
        le.analyzeCode(file);
    }
}
