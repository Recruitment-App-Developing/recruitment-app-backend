package com.ducthong.TopCV.utility;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageSourceUtil {
    private final MessageSource messageSource;

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, "Default Message", LocaleContextHolder.getLocale());
    }

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, "Default Message", LocaleContextHolder.getLocale());
    }
}
