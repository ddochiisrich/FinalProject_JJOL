package com.example.project_jjol.util;

import org.apache.commons.text.StringEscapeUtils;

public class CrossSiteScripting {

    // XSS 방어를 위한 기본적인 sanitize 메서드
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        // Apache Commons Text 라이브러리를 사용하여 HTML을 이스케이프
        return StringEscapeUtils.escapeHtml4(input);
    }
}
