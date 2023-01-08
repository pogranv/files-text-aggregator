import java.util.ArrayList;

/**
 * Исключение для случая, когда зависимость файлов неверна.
 */
public class IncorrectFilesDependeceException extends Exception {

    /**
     * Публичный конструктор.
     * @param message Сообщение исключения.
     * @param nodesInCycle Список вершин, которые образуют цикл.
     */
    public IncorrectFilesDependeceException(String message, ArrayList<String> nodesInCycle) {
        super(message);
        this.nodesInCycle = nodesInCycle;
    }

    /**
     * Возвращает список вершин, которые образуют цикл.
     * @return Список вершин, которые образуют цикл.
     */
    public ArrayList<String> getNodesInCycle() {
        return nodesInCycle;
    }

    /**
     * Список вершин, которые образуют цикл.
     */
    private ArrayList<String> nodesInCycle = new ArrayList<>();
}
