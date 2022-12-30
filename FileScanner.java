package IHT;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс для сканирования файлов.
 */
public class FileScanner {

    /**
     * Корневая директория.
     */
    protected final File rootDirectory;

    /**
     * Граф для топологической сортировки.
     */
    protected final FileGraph fileGraph;


    public FileScanner(String rootPathString) {
        rootDirectory = new File(rootPathString);
        fileGraph = new FileGraph(rootDirectory.getPath());
    }


    /**
     * Сканирует файл на наличие директив по паттерну.
     * В случае обнаружения фиксирует зависимость в словаре и сохраняет зависимое значение.
     *
     * @param pattern    Паттерн, регулярное выражение, для обнаружения директивы.
     * @param collection Коллекция всех найденных файлов в кореновой и вложенных директориях.
     * @return Пару значений - словарь зависимостей и множество зависимых значений.
     */

    public Pair<Map<Reader, List<Reader>>, Set<Reader>> scanByPattern(Pattern pattern, Map<String, Reader> collection) {

        Set<Reader> dependent = new HashSet<>();
        Map<Reader, List<Reader>> edges = new HashMap<>();

        // Проход по всем найденным файлам.
        for (var fileReader : collection.values()) {
            Matcher matcher = pattern.matcher(fileReader.getTextData());

            // Цикл по соответсвиям найденным паттерном.
            while (matcher.find()) {
                var key = collection.get(matcher.group());
                if (!edges.containsKey(key)) {
                    edges.put(key, new LinkedList<>());
                }
                edges.get(key).add(fileReader);
                dependent.add(fileReader);
            }
        }

        return new Pair<>(edges, dependent);
    }


    /**
     * Обнаружение зависимотей файлов на основе require.
     * Установка значений edges и roots в объекте fileGraph.
     */
    protected void findDependenciesByRequire() {

        Map<String, Reader> allFiles = new HashMap<>();

        // Поиск файлов в корневой и вложенных директориях,
        // запись найденных значений в allFiles внутри метода.
        Aggregator.getAllFiles(rootDirectory, allFiles, rootDirectory.getPath());

        final Pattern pattern = Pattern.compile("(?<=require ')(.*?)(?=')");
        var patternScannerResult = scanByPattern(pattern, allFiles);

        fileGraph.edges = patternScannerResult.val1();
        fileGraph.roots = Aggregator.findRoots(allFiles, patternScannerResult.val2());
    }

}
