/**
 * Исключение для случая, когда запрашиваемого файла не существует.
 */
public class RequiredFileNotExistException extends Exception {
    /**
     * Публичный конструктор.
     * @param message Сообщение исключения.
     */
    public RequiredFileNotExistException(String message) {
        super(message);
    }
}