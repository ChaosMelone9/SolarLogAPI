package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
/**
 * This Class includes functions to get the content of a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetFileContent {

    public static String fileContent(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    public static List<String> fileContentAsList(Path path) throws IOException {
        return Arrays.asList(fileContent(path).split("\n"));
    }
}
