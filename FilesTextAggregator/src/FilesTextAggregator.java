import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Класс склеивает содержимое заданных текстовых файлов
 * в заданном порядке.
 */
public class FilesTextAggregator {

    /**
     * Выводит имена файлов в заданном порядке.
     * @param files список путей или названий файлов, которые будут выведены.
     */
    public void printFileNames(ArrayList<String> files) {
        for (var filePath : files) {
            System.out.println(filePath);
        }
    }

    /**
     * Склеивает содержимое файлов в заданном порядке и выводит все в консоль.
     * @param files список путей до файлов, содержимое которых нужно склеить.
     */
    public void printFilesText(ArrayList<String> files) {
        System.out.println("Текст файлов:");
        System.out.println();
        for (var filePath : files) {
            File file = new File(filePath);
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (IOException ignored) { }
        }
    }
}
