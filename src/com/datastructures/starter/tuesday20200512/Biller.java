package com.datastructures.starter.tuesday20200512;

import java.util.Objects;

public class Biller {
    private String first;
    private String second;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biller biller = (Biller) o;
        return first.equals(biller.first) &&
                second.equals(biller.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
