package com.bol.mancala.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Utility methods and constants to use {@link LocalDateTime}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalDateTimeUtil {

    /** Returns LocalDateTime in UTC */
    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    /**
     * Converts date time with the specified timezone to UTC
     *
     * @param localDateTime datetime to convert to UTC
     * @param zoneOffset    zone offset
     * @return date and time in UTC
     */
    public static LocalDateTime toUtc(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return localDateTime.atOffset(zoneOffset).atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

}
