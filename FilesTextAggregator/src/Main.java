import java.util.ArrayList;

public class Main {
    /**
     * Путь до корневой папки.
     */
    private final static String ROOTDIRECTORY = "root";

    /**
     * Код для склеивания содержимого файлов в корневой папке root с учетом зависимостей.
     */

    public static void main(String[] args) {
        var filesFinder = new FilesFinder(ROOTDIRECTORY);
        var files = filesFinder.getFiles();
        var filesDependeceBuilder = new FilesDependeceBuilder();
        ArrayList<String> sortFiles;
        try {
            sortFiles = filesDependeceBuilder.getSortFilesList(files);
        } catch (IncorrectFilesDependeceException e) {
            System.out.println(e.getMessage());
            for (var file : e.getNodesInCycle()) {
                System.out.println(file);
            }
            return;
        } catch (RequiredFileNotExistException e) {
            System.out.println(e.getMessage());
            return;
        }
        var filesTextAggregator = new FilesTextAggregator();
        System.out.println("====================================");
        System.out.println("Список файлов с учетом зависимостей:");
        filesTextAggregator.printFileNames(sortFiles);
        System.out.println("====================================");
        filesTextAggregator.printFilesText(sortFiles);
    }
}