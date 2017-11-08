package Lab2;

public class Main {

    private static CollectionsTest collectionsTest;

    public static void main(String[] args) {
        collectionsTest = new CollectionsTest(256, true);

        for(int i = 0; i < 6; ++i){
            collectionsTest.generateTestData(i, 256);
            collectionsTest.findAndGetByIDTest(i/2);
            collectionsTest.findObjectTest(i/2);
            System.out.println("First object: ");
            collectionsTest.displayData(i/2, 1);
            System.out.println();
        }
    }
}
