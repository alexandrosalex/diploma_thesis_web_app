package com.diplomawebapp.model;

public enum Role {
	PROFESSOR("Professor"),
    STUDENT("Student");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
