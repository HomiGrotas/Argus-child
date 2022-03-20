package com.company.utils;

public enum States
{
    OPENED("opened"),
    CLOSED("closed");

    private final String desc;

    States(String desc){
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}