package store.file;


import store.constant.FileName;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static store.constant.ErrorMessage.*;

public class FileReader {

    public static List<String> read(FileName fileName) {
        Path filePath = getFilePathByFileName(fileName.getFileName());

        return readAllLines(filePath);
    }

    private static List<String> readAllLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_READ_FILE.message());
        }
    }

    private static Path getFilePathByFileName(String fileName) {
        URL resource = getResourceByFileName(fileName);

        return getFilePathByResource(resource);
    }

    private static Path getFilePathByResource(URL resource) {
        URI uri = getUriByResource(resource);

        return getFilePathByUri(uri);
    }

    private static Path getFilePathByUri(URI uri) {
        return Paths.get(uri);
    }

    private static URI getUriByResource(URL resource) {
        try {
            return resource.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(INVALID_URI_OF_RESOURCE.message());
        }
    }

    private static URL getResourceByFileName(String fileName) {
        URL resource = FileReader.class.getClassLoader().getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException(NOT_EXIST_FILE_RESOURCE.message());
        }

        return resource;
    }
}
