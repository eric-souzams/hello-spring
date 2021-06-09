package com.dio.jacksonandbinder.enums;

import java.util.stream.Stream;

public enum Raca {

    HUMANO("Humano"),
    ELFO("Elff"),
    ORC("Orc"),
    ANAO("Anão");

    private String value;

    Raca(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Raca of(String value) {
        return Stream.of(Raca.values())
                .filter(it -> it.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
