package io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileProcessor {

  /**
   * Разбивает файл на части указанного размера
   * @param sourcePath путь к исходному файлу
   * @param outputDir директория для сохранения частей
   * @param partSize размер каждой части в байтах
   * @return список путей к созданным частям
   */
  public List<Path> splitFile(String sourcePath, String outputDir, int partSize) throws IOException {
    List<Path> partPaths = new ArrayList<>();
    Path sourceFile = Paths.get(sourcePath);

    if (!Files.exists(sourceFile)) {
      throw new IOException("Source file does not exist: " + sourcePath);
    }

    Path outputDirectory = Paths.get(outputDir);
    Files.createDirectories(outputDirectory);

    String fileName = sourceFile.getFileName().toString();
    long fileSize = Files.size(sourceFile);

    try (FileChannel sourceChannel = FileChannel.open(sourceFile, StandardOpenOption.READ)) {
      ByteBuffer buffer = ByteBuffer.allocate(partSize);
      int partNumber = 1;
      long bytesRead;

      while ((bytesRead = sourceChannel.read(buffer)) > 0) {
        buffer.flip();

        String partFileName = String.format("%s.part%d", fileName, partNumber);
        Path partFile = outputDirectory.resolve(partFileName);

        try (FileChannel partChannel = FileChannel.open(partFile,
            StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
          partChannel.write(buffer);
        }

        partPaths.add(partFile);
        partNumber++;
        buffer.clear();
      }
    }

    return partPaths;
  }

  /**
   * Объединяет части файла обратно в один файл
   * @param partPaths список путей к частям файла (в правильном порядке)
   * @param outputPath путь для результирующего файла
   */
  public void mergeFiles(List<Path> partPaths, String outputPath) throws IOException {
    if (partPaths == null || partPaths.isEmpty()) {
      throw new IOException("Part paths list is empty or null");
    }

    Path outputFile = Paths.get(outputPath);

    try (FileChannel outputChannel = FileChannel.open(outputFile,
        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

      ByteBuffer buffer = ByteBuffer.allocate(8192);

      for (Path partPath : partPaths) {
        if (!Files.exists(partPath)) {
          throw new IOException("Part file not found: " + partPath);
        }

        try (FileChannel partChannel = FileChannel.open(partPath, StandardOpenOption.READ)) {
          while (partChannel.read(buffer) > 0) {
            buffer.flip();
            outputChannel.write(buffer);
            buffer.clear();
          }
        }
      }
    }
  }
}