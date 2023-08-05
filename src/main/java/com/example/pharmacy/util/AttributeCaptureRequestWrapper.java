package com.example.pharmacy.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Map;

public class AttributeCaptureRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, Object> attributes = new HashMap<>();

    public AttributeCaptureRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributes.put(name, o);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
