package com.guiwoo.weather.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
@Getter
public enum DateErrorCode {
    DATE_PAST_ERROR(HttpStatus.FORBIDDEN,"❌ 너무 과거의 데이터 입니다."),
    DATE_FUTURE_ERROR(HttpStatus.BAD_REQUEST, "❌ 미래 의 데이터 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
