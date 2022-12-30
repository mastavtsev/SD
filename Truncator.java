package IHT;

/**
 * Интерфейс для обрезания пути к файлу в зависимоти от корневого файла.
 */
public class Truncator {
    public static String truncatePathByRootPath(String rootDirectoryPath, String path) {
        return path.substring(rootDirectoryPath.length() + 1);
    }
}
