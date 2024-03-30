package com.udacity.webcrawler.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Writer;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to write a {@link CrawlResult} to file.
 */
public final class CrawlResultWriter {
  private final CrawlResult result;

  /**
   * Creates a new {@link CrawlResultWriter} that will write the given {@link CrawlResult}.
   */
  public CrawlResultWriter(CrawlResult result) {
    this.result = Objects.requireNonNull(result);
  }

  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Path}.
   *
   * <p>If a file already exists at the path, the existing file should not be deleted; new data
   * should be appended to it.
   *
   * @param path the file path where the crawl result data should be written.
   */
  public void write(Path path) {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(path);
    // TODO: Fill in this method.
    File outputFile = new File(path.toString());

    try(Writer writer = outputFile.isFile() ? Files.newBufferedWriter(path, StandardOpenOption.APPEND) :
                                              Files.newBufferedWriter(path, StandardOpenOption.WRITE)){
      write(writer);
    }
    catch(Exception ex){
      System.out.println(ex);
    }
  }

  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Writer}.
   *
   * @param writer the destination where the crawl result data should be written.
   */
  @JsonDeserialize(builder = CrawlResult.Builder.class)
  public void write(Writer writer) {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(writer);
    // TODO: Fill in this method.
    ObjectMapper objMapper = new ObjectMapper();
    objMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

    try{
      objMapper.writeValue(writer, this.result);
    }
    catch(Exception ex){
      System.out.println(ex);
    }
  }
}
