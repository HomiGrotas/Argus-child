package com.company.utils;

public class Pair<T, V> {
    public T a;
    public V b;

    public Pair(T a, V b)
    {
        this.a = a;
        this.b = b;
    }

    public Pair(T a)
    {
        this.a = a;
        this.b = null;
    }

    public String toString()
    {
        return "a: " + this.a + ", b: " + this.b;
    }
}
