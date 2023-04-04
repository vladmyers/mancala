package com.bol.mancala.exception;

import com.bol.mancala.util.LocalDateTimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract response sent by the server
 */
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class RestResponse<T> {

    @Builder.Default
    private UUID uuid = UUID.randomUUID();

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTimeUtil.nowUtc();

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T result;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private RestResponseError error;

    public static <T> RestResponse<T> success() {
        return RestResponse.<T>builder().build();
    }

    public static <T> RestResponse<T> success(T result) {
        return RestResponse.<T>builder().result(result).build();
    }

    public static <T> RestResponse<T> error(Throwable e) {
        return error(e, (String) null);
    }

    public static <T> RestResponse<T> error(Throwable e, ErrorMessage errorMessage) {
        return error(e, errorMessage.getMessage(), null);
    }

    public static <T> RestResponse<T> error(Throwable e, String displayMessage) {
        return error(e, displayMessage, null);
    }

    public static <T> RestResponse<T> error(Throwable e, String displayMessage, Set<String> detailSet) {
        return RestResponse.<T>builder()
                .error(RestResponseError.builder()
                        .message(e.getMessage())
                        .displayMessage(displayMessage)
                        .detailSet(detailSet)
                        .build())
                .build();
    }

}
