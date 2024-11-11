package store.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static store.common.constant.ErrorMessage.NOT_EXIST_FILE_DATA;

public class FileMapper {

    private static final int FILE_ROW_LINE_MIN_SIZE = 2;
    private static final String FILE_DATA_DELIMITER = ",";
    private static final String FILE_DATA_NULL = "null";

    public static List<Map<String, String>> map(List<String> lines) {
        validate(lines);

        List<String> keys = setKeys(lines.getFirst());

        return lines.stream()
                .skip(1)
                .map(FileMapper::readLine)
                .map(data -> mapper(keys, data))
                .toList();
    }

    private static void validate(List<String> lines) {
        if (lines.size() <= FILE_ROW_LINE_MIN_SIZE) {
            throw new IllegalArgumentException(NOT_EXIST_FILE_DATA.message());
        }
    }

    private static List<String> setKeys(String firstLine) {
        return readLine(firstLine);
    }

    private static List<String> readLine(String line) {
        return Arrays.stream(line.split(FILE_DATA_DELIMITER)).toList();
    }

    private static Map<String, String> mapper(List<String> keys, List<String> data) {
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), getValue(data.get(i)));
        }

        return map;
    }

    private static String getValue(String value) {
        if (value.equalsIgnoreCase(FILE_DATA_NULL)) {
            return null;
        }

        return value;
    }
}
