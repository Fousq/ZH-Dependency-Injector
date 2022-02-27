package kz.zhanbolat.di.util;

import java.util.Objects;

public class PairGeneric<T, K> {
    private T key;
    private K value;

    public PairGeneric(T key, K value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public K getValue() {
        return value;
    }

    public void setValue(K value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairGeneric<?, ?> that = (PairGeneric<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "PairGeneric{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
