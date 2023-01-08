import java.util.*;

/**
 * Класс, реализующий работу с графом.
 * Позволяет добавлять ребра в уже построенный граф, строить топологическую сортировку,
 * а также получать вершины, образующие цикл в построенном графе.
 * @param <T> Тип представления вершин.
 */
public class Graph<T> {
    /**
     * Граф, хранящийся в виде списка смежности.
     */
    private final HashMap<T, ArrayList<T>> graph;

    /**
     * Множество вершин, образующих цикл.
     */
    private final HashSet<T> nodesInCycle;

    /**
     * Публичный конструктор.
     */
    public Graph() {
        graph = new HashMap<>();
        nodesInCycle = new HashSet<>();
    }

    /**
     * Добавляет вершину в граф.
     * @param node Вершина, которую нужно добавить в граф.
     */
    public void addNode(T node) {
        if (!graph.containsKey(node)) {
            graph.put(node, new ArrayList<>());
        }
    }

    /**
     * Добавляет ребро в граф.
     * @param from Откуда идет ребро.
     * @param to Куда идет ребро.
     */
    public void addEdge(T from, T to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new ArrayList<>());
        }
        if (!graph.containsKey(to)) {
            graph.put(to, new ArrayList<>());
        }
        graph.get(from).add(to);
    }

    /**
     * Возвращает топологическую сортировку текущего графа.
     * @return Топологическая сортировка текущего графа.
     */
    public ArrayList<T> getTopologicalSort() {
        ArrayList<T> topologicalSort = new ArrayList<>();
        HashSet<T> visitedNodes = new HashSet<>();
        for (var node : graph.keySet()) {
            if (!visitedNodes.contains(node)) {
                buildTopologicalSort(node, visitedNodes, topologicalSort);
            }
        }
        return topologicalSort;
    }

    /**
     * Возвращает названия вершин, которые образуют цикл(ы) в графе (если такие есть),
     * иначе возвращает пустой список.
     * @return Список названий вершин.
     */
    public ArrayList<T> getNodesInCycle() {
        hasCycle();
        return new ArrayList<>(nodesInCycle);
    }

    /**
     * Строит топологическую сортировку текущего графа.
     * @param currentNode Вершина, в которой сейчас находится рекурсивный алгоритм.
     * @param visitedNodes Множество вершин, которые уже посещены.
     * @param topologicalSort Список вершин, из которых алгоритм уже вышел.
     */
    private void buildTopologicalSort(T currentNode, HashSet<T> visitedNodes, ArrayList<T> topologicalSort) {
        visitedNodes.add(currentNode);
        for (var toNode : graph.get(currentNode)) {
            if (!visitedNodes.contains(toNode)) {
                buildTopologicalSort(toNode, visitedNodes, topologicalSort);
            }
        }
        topologicalSort.add(currentNode);
    }

    /**
     * Проверяет наличие циклов в текущем графе.
     * Если цикл(ы) есть, то вершины, образующие его, можно получить
     * с помощью метода getNodesInCycle().
     * @return True, если граф имеет цикл(ы), инчае False.
     */
    public boolean hasCycle() {
        HashSet<T> visitedNodes = new HashSet<>();
        HashSet<T> waitingNodes = new HashSet<>();
        boolean hasCycleFlag = false;
        for (var node : graph.keySet()) {
            if (!visitedNodes.contains(node)) {
                hasCycleFlag = hasCycleFlag || checkHasCycle(node, visitedNodes, waitingNodes);
            }
        }
        return hasCycleFlag;
    }

    /**
     * Проверяет наличие циклов в текущем графе.
     * @param currentNode Вершина, в которой сейчас находится рекурсивный алгоритм.
     * @param visitedNodes  Множество вершин, которые уже посещены.
     * @param waitingNodes  Множество вершин, в которые алгоритм уже зашел, но еще не вышел.
     * @return True, если алгоритм нашел цикл, иначе False.
     */
    private boolean checkHasCycle(T currentNode, HashSet<T> visitedNodes, HashSet<T> waitingNodes) {
        waitingNodes.add(currentNode);
        boolean hasCycle = false;
        for (var toNode : graph.get(currentNode)) {
            if (waitingNodes.contains(toNode)) {
                hasCycle = true;
                nodesInCycle.addAll(waitingNodes);
                continue;
            }
            if (!visitedNodes.contains(toNode)) {
                hasCycle = hasCycle || checkHasCycle(toNode, visitedNodes, waitingNodes);
            }
        }
        waitingNodes.remove(currentNode);
        visitedNodes.add(currentNode);
        return hasCycle;
    }
}
