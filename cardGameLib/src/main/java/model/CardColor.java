package model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum CardColor implements Serializable {
    S("s"), H("h"), D("d"), C("c");

    private String name;

    CardColor(String name) {
        this.name = name;
    }
}
