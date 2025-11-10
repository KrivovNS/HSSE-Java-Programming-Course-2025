package io;

import io.TextFileAnalyzer.AnalysisResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextFileAnalyzerTest {

  @Test
  void testAnalyzeFile() throws IOException {
    TextFileAnalyzer analyzer = new TextFileAnalyzer();

    // Создаем временный тестовый файл
    Path testFile = Files.createTempFile("test", ".txt");
    Files.write(testFile, Arrays.asList("Hello world!", "This is test. "));

    AnalysisResult result = analyzer.analyzeFile(testFile.toString());

    assertEquals(2, result.getLineCount());
    assertEquals(5, result.getWordCount());
    assertEquals(26, result.getCharCount());

    assertEquals(1, result.getCharFrequency().get('H'));
    assertEquals(2, result.getCharFrequency().get('e'));
    assertEquals(4, result.getCharFrequency().get(' '));

    Files.deleteIfExists(testFile);
  }

  @Test
  void testSaveAnalysisResult() throws IOException {
    TextFileAnalyzer analyzer = new TextFileAnalyzer();

    HashMap<Character, Long> frequency = new HashMap<>();
    frequency.put('a', 5L);
    frequency.put('b', 3L);
    TextFileAnalyzer.AnalysisResult result = new TextFileAnalyzer.AnalysisResult(2L, 5L, 20L, frequency);

    Path outputFile = Files.createTempFile("analysis", ".txt");
    analyzer.saveAnalysisResult(result, outputFile.toString());

    assertTrue(Files.exists(outputFile));
    assertTrue(Files.size(outputFile) > 0);

    try (BufferedReader reader = new BufferedReader(new FileReader(outputFile.toFile()))) {
      StringBuilder fileContent = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        fileContent.append(line).append("\n");
      }

      String content = fileContent.toString();

      assertTrue(content.contains("Analysis result"));

      assertTrue(content.contains("Line count: 2"));
      assertTrue(content.contains("Word count: 5"));
      assertTrue(content.contains("Character count: 20"));
      assertTrue(content.contains("Character frequency: {a=5, b=3"));
    }

    Files.deleteIfExists(outputFile);
  }
}
