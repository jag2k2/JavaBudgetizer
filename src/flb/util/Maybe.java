package flb.util;

import java.util.*;

public class Maybe<T> implements Iterable<T> {
    private final ArrayList<T> values = new ArrayList<>();

    public Maybe() {
    }

    public Maybe(T theValue) {
        values.add(theValue);
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        Maybe<T> maybeToCompare = (Maybe<T>)toCompare;
        return this.values.equals(maybeToCompare.values);
    }
}
