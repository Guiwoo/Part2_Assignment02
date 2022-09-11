package com.guiwoo.weather.error;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateException extends RuntimeException{
    private DateErrorCode errorCode;
    private String errorMessage;

    public DateException(DateErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

    public DateException(String message, DateErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public DateException(String message, Throwable cause, DateErrorCode errorCode) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public DateException(Throwable cause, DateErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }
}
