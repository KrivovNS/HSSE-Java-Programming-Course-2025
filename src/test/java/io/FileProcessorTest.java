package io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

  @Test
  void testSplitAndMergeFile() throws IOException {
    FileProcessor processor = new FileProcessor();

    // Создать тестовый файл
    Path testFile = Files.createTempFile("test", ".dat");
    byte[] testData = new byte[1500]; // 1.5KB данных
    new Random().nextBytes(testData);
    Files.write(testFile, testData);

    // Разбить на части по 500 байт
    String outputDir = Files.createTempDirectory("parts").toString();
    List<Path> parts = processor.splitFile(testFile.toString(), outputDir, 500);

    // Должно быть 3 части
    assertEquals(3, parts.size());

    // Проверка размеров файлов
    assertEquals(500, Files.size(parts.get(0)));
    assertEquals(500, Files.size(parts.get(1)));
    assertEquals(500, Files.size(parts.get(2)));

    // Проверка существования файлов
    assertTrue(Files.exists(parts.get(0)));
    assertTrue(Files.exists(parts.get(1)));
    assertTrue(Files.exists(parts.get(2)));

    // Проверить имена файлов
    assertTrue(parts.get(0).toString().endsWith(".part1"));
    assertTrue(parts.get(1).toString().endsWith(".part2"));
    assertTrue(parts.get(2).toString().endsWith(".part3"));

    // Объединить обратно
    Path mergedFile = Files.createTempFile("merged", ".dat");
    processor.mergeFiles(parts, mergedFile.toString());

    // Проверить что исходный и объединенный файлы идентичны
    assertArrayEquals(Files.readAllBytes(testFile), Files.readAllBytes(mergedFile),
        "Original and merged files should be identical");

    // Очистка
    Files.deleteIfExists(testFile);
    Files.deleteIfExists(mergedFile);
    for (Path part : parts) {
      Files.deleteIfExists(part);
    }
  }
}