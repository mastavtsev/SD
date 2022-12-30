package IHT;

/**
 * Интерфейс-граф для отображения зависимостей между файлами.
 */
public interface Graph {
    void addEdge(Reader parent, Reader child);
    StringBuilder DFS();
}
