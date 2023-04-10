package com.bol.mancala.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link LocalDateTimeUtil}
 */
class LocalDateTimeUtilTest {

    private static final LocalDateTime datetime = LocalDateTime.of(2023, 4, 1, 10, 0);

    private static final ZoneOffset offsetTurkey = ZoneOffset.of("+03:00");

    @Test
    void nowUtc() {
        assertThat(LocalDateTimeUtil.nowUtc()).isCloseToUtcNow(within(1, ChronoUnit.SECONDS));
    }

    @Test
    void toUtc() {
        assertEquals(datetime.minusHours(3), LocalDateTimeUtil.toUtc(datetime, offsetTurkey));
    }

}