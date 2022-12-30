package IHT;

import java.util.*;

public class FileGraph implements Graph {

    /**
     * Текстовый путь к корневому файлу.
     */
    private final String rootDirectoryPath;

    /**
     * Зависимости между файлами.
     */
    Map<Reader, List<Reader>> edges;

    /**
     * Корневые независимые файлы.
     */
    List<Reader> roots;


    /**
     * Конструктор класса.
     * @param rootDirectoryPath Текстовый путь к корневому файлу.
     */
    public FileGraph(String rootDirectoryPath) {
        edges = new HashMap<>();
        this.rootDirectoryPath = rootDirectoryPath;
    }


    /**
     * Добавить зависимость между файлами.
     * @param parent Файл родитель.
     * @param child Зависимый файл.
     */
    @Override
    public void addEdge(Reader parent, Reader child) {
        if (!edges.containsKey(parent)) {
            edges.put(parent, new LinkedList<>());
        }

        edges.get(parent).add(child);
    }


    /**
     * Цикл проходи по вершинам-ключам и зависимым от них вершинам.
     * Если от зависимой вершины зависит вершина ключ, это проверяется через словарь,
     * то это говорит о наличии цикла.
     * @return Наличие циклической зависимости.
     */
    protected boolean checkForCycle() {

        boolean cycle = false;

        if (edges != null && !edges.isEmpty()) {
            // Проход по всем вершинам-ключам графа, их которых исходят рёбра.
            // То есть файлы, которые требуются в других файлах через require.
            for (var key : edges.keySet()) {

                // Проход по зависимым от ключа вершинам.
                for (var file : edges.get(key)) {
                    if (edges.containsKey(file) && edges.get(file).contains(key)) {
                        cycle = true;
                        break;
                    }
                }
            }
        }

        return cycle;
    }


    /**
     * Рекурсивный обход в глубину.
     * @param node Файл, который просматривается на текущей итерации.
     * @param resultText Строка, агрегирующая содержание файлов.
     * @param visited   Посещённые вершины.
     */
    protected void DFSRecur(Reader node, StringBuilder resultText, Set<Reader> visited) {

        if (!visited.contains(node)) {
            System.out.println( Truncator.truncatePathByRootPath(rootDirectoryPath, node.getPath()));
            resultText.append("\n").append(node.getTextData());
            visited.add(node);

            if (edges.get(node) != null) {
                for (Reader reader : edges.get(node)) {
                    DFSRecur(reader, resultText, visited);
                }
            }
        }
    }


    /**
     * Метод обхода графа в ширину.
     * Вызывает рекурсивный метод для обхода в ширину.
     * @return  Агрегированное содержание файлов.
     */
    public StringBuilder DFS() {
        if (checkForCycle()) {
            System.out.println("There is a cycle in your files!");
            return null;
        } else {
            StringBuilder resultText = new StringBuilder();
            Set<Reader> visited = new HashSet<>();
            for (var root : roots) {
                DFSRecur(root, resultText, visited);
                System.out.println("\n");
            }

            System.out.println(resultText);
            return resultText;
        }
    }
}