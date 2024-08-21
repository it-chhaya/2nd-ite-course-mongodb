package dev.chanchhaya.course.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtil {

    public static String init(String input) {

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase(Locale.ENGLISH);

        String slug = normalized.replaceAll("\\s+", "-");
        return slug.replaceAll("-{2,}", "-"); // Remove consecutive hyphens
    }

}
