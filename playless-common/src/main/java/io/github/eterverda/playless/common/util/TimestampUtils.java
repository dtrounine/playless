package io.github.eterverda.playless.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public final class TimestampUtils {
    private static final AtomicReference<SimpleDateFormat> TIMESTAMP_FORMAT = new AtomicReference<>();

    private TimestampUtils() {
    }

    public static String zulu(long timestamp) {
        final SimpleDateFormat format = obtainFormat();
        final String result = format.format(new Date(timestamp));
        releaseFormat(format);
        return result;
    }

    public static long zulu(String timestamp) {
        final SimpleDateFormat format = obtainFormat();
        try {
            return format.parse(timestamp).getTime();

        } catch (ParseException e) {
            throw new IllegalArgumentException("Cannot parse date " + timestamp, e);

        } finally {
            releaseFormat(format);
        }
    }

    private static SimpleDateFormat obtainFormat() {
        final SimpleDateFormat usedFormat = TIMESTAMP_FORMAT.getAndSet(null);
        final SimpleDateFormat format = usedFormat != null ? usedFormat : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format;
    }

    private static void releaseFormat(SimpleDateFormat format) {
        TIMESTAMP_FORMAT.set(format);
    }
}
