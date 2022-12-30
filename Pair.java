package IHT;

/**
 * Класс для хранения двух значений.
 * @param <T>
 * @param <N>
 */
public class Pair<T, N> {
    private final T val1;
    private final N val2;

    /**
     * Конструктор класса.
     */
    public Pair(T val1, N val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    /**
     * @return Первое значение пары.
     */
    public final T val1() {
        return val1;
    }

    /**
     * @return Второе значение пары.
     */
    public final N val2() {
        return val2;
    }
}
