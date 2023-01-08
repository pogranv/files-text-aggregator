import java.io.File;
import java.util.ArrayList;

public class FilesFinder {
    /**
     * Список путей файлов.
     */
    private final ArrayList<String> files = new ArrayList<>();

    /**
     * Директория, в которой будут искаться файлы.
     */
    private final String directoryPath;

    /**
     * Публичный конструктор.
     * @param directoryPath Директория, в которой будут искаться файлы.
     */
    public FilesFinder(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Находит все файлы, находящиеся в директории и всех ее папках/подпапках.
     * Директория задается в конструкторе.
     * @return Список путей файлов.
     */
    public ArrayList<String> getFiles() {
        files.clear();
        findFiles(directoryPath);
        return files;
    }

    /**
     * Находит все файлы, находящиеся в директории и всех ее папках/подпапках.
     */
    private void findFiles(String directoryPath) {
        File currentFolder = new File(directoryPath);
        File[] currentFiles = currentFolder.listFiles();
        if (currentFiles == null) {
            return;
        }
        for (var file : currentFiles) {
            if (file.isFile()) {
                files.add(file.toString());
            }
            if (file.isDirectory()) {
                findFiles(file.toString());
            }
        }
    }
}
