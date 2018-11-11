package model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum CardValue implements Serializable {

    _6("6", 1), _7("7", 2), _8("8", 3), _9("9", 4), _10("10", 5), J("j", 6), Q("q", 7), K("k",8), A("a", 9);

    private String name;
    private int value;

    CardValue(String name, int value) {
        this.name = name;
        this.value = value;
    }

}
