package com.moderocky.misk.enums;

public enum KeyType {
    FORWARDS("W"),
    LEFT("A"),
    BACKWARDS("S"),
    RIGHT("D"),
    JUMP("SPACEBAR"),
    SNEAK("SHIFT"),
    NONE("NONE");

    String keyname;

    KeyType(String keyname) {
        this.keyname = keyname;
    }

    public String getKeyname() {
        return keyname;
    }
}

