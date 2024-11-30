public class Main{

    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        skipList.insert(3);
        skipList.insert(1);
        skipList.insert(4);
        skipList.insert(1);
        skipList.insert(5);
        skipList.insert(9);
        skipList.insert(2);
        skipList.insert(6);

        System.out.println("Contains 5: " + skipList.contains(5)); // true
        System.out.println("Contains 7: " + skipList.contains(7)); // false

        skipList.delete(5);
        System.out.println("Contains 5 after delete: " + skipList.contains(5)); // false
    }
}