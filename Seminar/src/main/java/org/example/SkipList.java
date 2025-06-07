package org.example;

import java.util.Random;

public class SkipList {
    private static final int MAX_LEVEL = 16;
    private static final double P = 0.5;
    private final Node head;
    private int level;
    private final Random random;
    public int size;

    private static class Node {
        int value;
        Node[] forward;

        Node(int value, int level) {
            this.value = value;
            this.forward = new Node[level + 1];
        }
    }

    public SkipList() {
        this.level = 0;
        this.size = 0;
        this.head = new Node(Integer.MIN_VALUE, MAX_LEVEL);
        this.random = new Random();
    }


    private int randomLevel() {
        int lvl = 0;
        while (lvl < MAX_LEVEL && random.nextDouble() < P) {
            lvl++;
        }
        return lvl;
    }

    public boolean search(int value) {
        Node current = this.head;
        int step = 0;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value <= value) {
                step++;
                current = current.forward[i];
                if (current.value == value) {
                    System.out.println(step);
                    return true;
                }
            }
            step++;
        }
        return false;
    }


    public void insert(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = this.head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current == null || current.value != value) {
            int lvl = randomLevel();

            if (lvl > level) {
                for (int i = level + 1; i <= lvl; i++) {
                    update[i] = head;
                }
                level = lvl;
            }

            Node newNode = new Node(value, lvl);
            for (int i = 0; i <= lvl; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
        size++;
    }


    public void remove(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current != null && current.value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != current) {
                    break;
                }
                update[i].forward[i] = current.forward[i];
            }

            while (level > 0 && head.forward[level] == null) {
                level--;
            }

        }

    }


    public void print() {
        System.out.println("SkipList (level: " + level + ")");
        for (int i = level; i >= 0; i--) {
            System.out.print("Level " + i + ": ");
            Node node = head.forward[i];
            while (node != null) {
                System.out.print(node.value + " ");
                node = node.forward[i];
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipList list = new SkipList();

        list.insert(3);
        list.insert(6);
        list.insert(7);
        list.insert(9);
        list.insert(12);
        list.insert(17);
        list.insert(19);

        list.search(12);
        list.print();

        list.print();
    }
}
