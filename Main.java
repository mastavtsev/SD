package IHT;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Enter path to root directory. \n" +
                "Note that it should look like : D:/Folder1/Folder1-1");

        String rootPath;
        Scanner scanner = new Scanner(System.in);
        rootPath = scanner.next();

        DependencyFileScanner fileScanner = new DependencyFileScanner(rootPath);
        fileScanner.scan();
    }
}