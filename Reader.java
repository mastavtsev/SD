package IHT;


/**
 * Интерфейс-оболочка для просматриваемых файлов.
 */
public interface Reader {
    String getTextData();

    String getName();

    String getPath();

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();
}
