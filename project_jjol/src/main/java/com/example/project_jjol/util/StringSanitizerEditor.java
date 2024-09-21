package com.example.project_jjol.util;

import java.beans.PropertyEditorSupport;

public class StringSanitizerEditor extends PropertyEditorSupport {
    
    @Override
    public void setAsText(String text) {
        // 입력값을 XSS 방어를 위해 sanitize 처리
        setValue(CrossSiteScripting.sanitize(text));
    }
}
