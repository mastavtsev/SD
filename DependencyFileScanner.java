package IHT;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Сканер обнаружения зависимостей файлов.
 */
public class DependencyFileScanner extends FileScanner {

    /**
     * Конструктор объектов класса
     *
     * @param rootPathString Полный путь к корневой директории.
     */
    public DependencyFileScanner(String rootPathString) {
        super(rootPathString);
    }


    /**
     * Сканирование файлов для обнаружения зависимостей файлов.
     */
    public void scan() {

        findDependenciesByRequire();
        StringBuilder resultText = fileGraph.DFS();

        if (resultText != null) {
            try {
                File newFile = new File(rootDirectory.getPath() + "/resulText.txt");
                newFile.createNewFile();

                FileWriter myWriter = new FileWriter(newFile.getPath());
                myWriter.write(resultText.toString());
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }
}



