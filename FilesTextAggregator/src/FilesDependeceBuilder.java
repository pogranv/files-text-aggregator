import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс реализует построение зависимостей файлов между собой, осуществляя
 * построение их топологической сортировки.
 */
public class FilesDependeceBuilder {

    /**
     * Получает топологическую сортировку графа зависимостей файлов.
     *
     * @param files Список файлов, по которым нужно построить топологическую сортировку.
     * @return Список файлов, отсортированных топологически.
     * @throws IncorrectFilesDependeceException Выбрасывается в случае, когда граф зависимостей файлов
     *                                          имеет цикл.
     * @throws RequiredFileNotExistException    Выбрасывается в случае, когда в каком-то файле файла,
     *                                          запрошенного с помощью require, не существует.
     */
    public ArrayList<String> getSortFilesList(ArrayList<String> files) throws IncorrectFilesDependeceException, RequiredFileNotExistException {
        var fileGraph = buildFilesGraph(files);
        if (fileGraph.hasCycle()) {
            throw new IncorrectFilesDependeceException("Следующие файлы образуют циклическую зависимость:", fileGraph.getNodesInCycle());
        }
        return fileGraph.getTopologicalSort();
    }

    /**
     * Строит граф зависимостей файлов. Если файл А содержит в себе строчку requre 'File B', то
     * в графе есть ориентированное ребро A --> B.
     * @param files Список файлов, зависимости которых нужно построить.
     * @return Граф зависимостей файлов.
     * @throws RequiredFileNotExistException Выбрасывается в случае, когда запрошенного с помощью require
     * файла не существует.
     */
    private Graph<String> buildFilesGraph(ArrayList<String> files) throws RequiredFileNotExistException {
        var fileGraph = new Graph<String>();
        for (var file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Matcher requireMatcher = Pattern.compile("require '.*'").matcher(line);
                    if (!requireMatcher.matches()) {
                        continue;
                    }
                    Matcher pathMatcher = Pattern.compile("'.*'").matcher(line);
                    if (!pathMatcher.find()) {
                        continue;
                    }
                    String requreFilePath = pathMatcher.group();
                    // обрезка кавычек с двух сторон
                    requreFilePath = requreFilePath.substring(1, requreFilePath.length() - 1);
                    if (!files.contains(requreFilePath)) {
                        throw new RequiredFileNotExistException("В файле "  + file.toString() + " запрошен несуществующий файл " + requreFilePath);
                    }
                    fileGraph.addEdge(file.toString(), requreFilePath);
                }
            } catch (IOException ignored) { }
        }
        return fileGraph;
    }
}
