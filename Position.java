package org.example;

import java.util.Objects;

public class Position extends Pair<Integer, Integer> {

    public Position(Integer i, Integer j) {
        super(i, j);
    }


    public Integer i() {
        return val1;
    }

    public Integer j() {
        return val2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Position position)) return false;

        return Objects.equals(this.val1, position.val1) && Objects.equals(this.val2, position.val2);
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + val1.hashCode();
        result = 31 * result + val2;
        return result;
    }
}
