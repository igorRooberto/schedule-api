package com.api.schedule_v1.exception.message;

import java.time.Instant;

public record ErrorMessage(
        Instant timeStamp,
        int httpStatus,
        String error,
        String message,
        String path
) {
}
