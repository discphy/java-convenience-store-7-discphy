package store.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static store.common.constant.ErrorMessage.*;

public class MapUtils {

    public static int getInt(Map<String, String> map, String key) {
        try {
            return Integer.parseInt(map.get(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_PARSE_INTEGER.message());
        }
    }

    public static long getLong(Map<String, String> map, String key) {
        try {
            return Long.parseLong(map.get(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_PARSE_LONG.message());
        }
    }

    public static LocalDate getLocalDate(Map<String, String> map, String key) {
        try {
            return LocalDate.parse(map.get(key));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(NOT_PARSE_LOCALDATE.message());
        }
    }
}
