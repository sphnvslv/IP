package Lab3;
import java.util.*;
abstract class AbstractStorage<T> {
    public abstract void addItem(T item);
    public abstract void removeItem(String id);
    public abstract T findItem(String id);
    public abstract void displayAll();
}