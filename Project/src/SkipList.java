import java.util.Random;

public class SkipList<T extends Comparable<T>> {

    private static final double P = 0.5; // Вероятность повышения уровня
    private final Random random = new Random();
    private Node<T> head;

    private static class Node<T> {
        T data;
        Node<T>[] next;
        int level;

        Node(T data, int level) {
            this.data = data;
            this.level = level;
            this.next = new Node[level];
        }
    }

    public SkipList() {
        head = new Node<>(null, 32); // Максимальный уровень - настраиваемый
        for (int i = 0; i < head.level; i++) {
            head.next[i] = null;
        }
    }


    public boolean contains(T data) {
        Node<T> current = head;
        for (int i = head.level - 1; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].data.compareTo(data) < 0) {
                current = current.next[i];
            }
        }
        current = current.next[0];
        return current != null && current.data.equals(data);
    }

    public void insert(T data) {
        Node<T>[] update = new Node[head.level];
        Node<T> current = head;

        for (int i = head.level - 1; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].data.compareTo(data) < 0) {
                current = current.next[i];
            }
            update[i] = current;
        }
        current = current.next[0];

        if (current != null && current.data.equals(data)) return;

        int newLevel = randomLevel();
        if (newLevel > head.level) {
            for (int i = head.level; i < newLevel; i++) {
                update[i] = head;
            }
            head.level = newLevel;
        }

        Node<T> newNode = new Node<>(data, newLevel);
        for (int i = 0; i < newLevel; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
    }

    public void delete(T data) {
        Node<T>[] update = new Node[head.level];
        Node<T> current = head;

        for (int i = head.level - 1; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].data.compareTo(data) < 0) {
                current = current.next[i];
            }
            update[i] = current;
        }
        current = current.next[0];

        if (current == null || !current.data.equals(data)) return;

        for (int i = 0; i < head.level; i++) {
            if (update[i].next[i] != current) break;
            update[i].next[i] = current.next[i];
        }


        while (head.level > 1 && head.next[head.level -1] == null) {
            head.level--;
        }
    }

    private int randomLevel() {
        int level = 1;
        while (random.nextDouble() < P && level < 32) {
            level++;
        }
        return level;
    }
}