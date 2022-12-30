package IHT;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aggregator {
    /**
     * Агрегатор файлов в корневой и вложенных директориях.
     *
     * @param root     Директория, в которой осуществляется поиск.
     * @param allFiles Контейнер для агрегации файлов.
     */
    public static void getAllFiles(File root, Map<String, Reader> allFiles, String rootDirectoryPath) {

        final File[] filesList = root.listFiles();

        if (filesList != null) {
            for (File file : filesList) {
                if (file.isDirectory())
                    getAllFiles(file, allFiles, rootDirectoryPath);
                if (file.isFile()) {
                    allFiles.put(Truncator.truncatePathByRootPath(rootDirectoryPath,
                            file.getPath()), new FileReader(file));
                }
            }
        }
    }


    /**
     * Находит независимые корневые файлы. Файлы, которые не от кого не зависят.
     * То есть в них нет 'requires'.
     *
     * @param collection Коллекция всех найденных файлов в кореновой и вложенных директориях.
     * @param dependent  Файлы, котороые зависят от других фалов.
     * @return Список независимых корневых файлов.
     */
    public static List<Reader> findRoots(Map<String, Reader> collection, Set<Reader> dependent) {

        List<Reader> roots = new ArrayList<>();

        for (var file : collection.values()) {
            if (!dependent.contains(file)) {
                roots.add(file);
            }
        }
        return roots;
    }

}
