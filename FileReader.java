package IHT;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class FileReader implements Reader {
    /**
     * Файл, обёрткой для которого является FileReader.
     */
    private final File file;

    /**
     * Построчный список содержания файла.
     */
    private List<String> allLines;

    /**
     * Содержание файла.
     */
    private String textData;

    /**
     * Получение построчного содержания файла.
     * @param file  Файл из которго происходит считывание.
     * @return      Построчный список содержания файла.
     * @throws IOException Если происходит ошибка ввода-вывода при чтении из файла
     * или считывается искаженная или неотображаемая последовательность байтов
     */
    public static List<String> getTextFromFile(File file) throws IOException {
        Path path = file.toPath();
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }


    /**
     * Конструктор класса.
     * @param filePathString Строковый путь к файлу.
     */
    public FileReader(String filePathString) {

        file = new File(filePathString);

        try {
            allLines = getTextFromFile(file);
        } catch (IOException e) {
            System.out.println("Sorry, file " + file.getPath() + " is not found!");
        }
    }

    /**
     * Конструктор класса.
     * @param newFile Файл, обёрткой для которого является FileReader.
     */
    public FileReader(File newFile) {

        this.file = newFile;

        try {
            allLines = getTextFromFile(file);
        } catch (IOException e) {
            System.out.println("Sorry, file " + file.getPath() + " is not found!");
        }
    }

    /**
     * @return Текстовое содержание файла.
     */
    public String getTextData() {
        if (textData != null) {
            return textData;
        }

        textData = String.join("\n", allLines);
        return textData;
    }

    /**
     * @return Имя файла.
     */
    public final String getName() {
        return file.getName();
    }


    /**
     * @return Путь к файлу.
     */
    public final String getPath() {
        return file.getPath();
    }


    /**
     * Проверка элементов на равенстов.
     * @param obj Элемент для сравнения.
     * @return Значение равенства.
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FileReader reader = (FileReader) obj;
        return Objects.equals(getPath(), reader.getPath());
    }

    /**
     * @return Значения хеш-кода.
     */
    @Override
    public final int hashCode() {
        return (getPath()).hashCode();
    }

}
