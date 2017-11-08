package Lab2;

import java.util.*;

class CollectionsTest {

    private List<DummyData> dummyDataList;

    private Set<DummyData> set;
    private Map<Integer, DummyData> map;
    private List<DummyData> list;

    private Random random;
    private boolean displayFlag;

    private int randomID = 0;
    private DummyData randomObject = null;

    CollectionsTest(int amount, boolean displayFlag){
        this.displayFlag = displayFlag;

        random = new Random(System.currentTimeMillis());
        dummyDataList = new Vector<>();

        for(int i = 0; i < amount; ++i){
            dummyDataList.add(randomDummyData());
        }

        newRandomID();
        newRandomObject();
    }
    
    void generateTestData(int collectionType, int amount){

        Long time = System.nanoTime();

        switch (collectionType) {
            case 0:
                set = new HashSet<>();
                System.out.println("HashSet");
                break;
            case 1:
                set = new LinkedHashSet<>();
                System.out.println("LinkedHashSet");
                break;
            case 2:
                map = new HashMap<>();
                System.out.println("HashMap");
                break;
            case 3:
                map = new TreeMap<>();
                System.out.println("TreeMap");
                break;
            case 4:
                list = new Vector<>();
                System.out.println("Vector");
                break;
            case 5:
                list = new ArrayList<>();
                System.out.println("ArrayList");
                break;
            default:
               System.out.println("wrong collectionType index");
               return;
        }

        if(collectionType == 2 || collectionType == 3) {
            for (int i = 0; i < amount; ++i) {
                map.put(i, dummyDataList.get(i));
            }
        }
        else if(collectionType == 0 || collectionType == 1)  {
            for (int i = 0; i < amount; ++i) {
                set.add(dummyDataList.get(i));
            }
        }
        else {
            for (int i = 0; i < amount; ++i) {
                list.add(dummyDataList.get(i));
            }
        }

        System.out.println("Elapsed time: " + Double.toString((System.nanoTime() - time) / 1000000.0) + " ms");

    }

    void findAndGetByIDTest(int type){
        Long time = System.nanoTime();
        System.out.println("findAndGetByID Test");

        switch (type) {
            case 0: //set
                for(DummyData dummyData : set){
                    if(dummyData.equals(dummyDataList.get(randomID))){
                        break;
                    }
                }
                break;
            case 1: //map
                map.get(randomID);
                break;
            case 2: //list
                list.get(randomID);
                break;
            default:
                System.out.println("wrong type index");
                return;
        }

        System.out.println("Elapsed time: " + Double.toString((System.nanoTime() - time) / 1000000.0) + " ms");
    }

    void findObjectTest(int type){
        Long time = System.nanoTime();
        System.out.println("findObject Test");

        switch (type) {
            case 0: //set
                for(DummyData dummyData : set){
                    if(dummyData.equals(randomObject)){
                        break;
                    }
                }
                break;
            case 1: //map
                map.containsValue(randomObject);
                break;
            case 2: //list
                list.indexOf(randomObject);
                break;
            default:
                System.out.println("wrong type index");
                return;
        }

        System.out.println("Elapsed time: " + Double.toString((System.nanoTime() - time) / 1000000.0) + " ns");
    }

    private DummyData randomDummyData(){
        return new DummyData(random.nextInt() % 100 + 1,
                randomString(random.nextInt() % 100 + 1), random.nextDouble());
    }

    public int newRandomID(){
        randomID = random.nextInt(dummyDataList.size());
        return randomID;
    }

    public DummyData newRandomObject() {
        randomObject = dummyDataList.get(random.nextInt(dummyDataList.size()));
        return randomObject;
    }

    void displayData(int type, int amount){
        if(!displayFlag){
            return;
        }

        if(type == 0) {
            Object[] array = set.toArray();

            for (int i = 0; i < amount; ++i) {
                System.out.println(Integer.toString(i) + ". " + array[i].toString());
            }
        }
        else if(type == 1){
            for (int i = 0; i < amount; ++i) {
                System.out.println(Integer.toString(i) + ". " + map.get(i));
            }
        }
        else if(type == 2){
            for (int i = 0; i < amount; ++i) {
                System.out.println(Integer.toString(i) + ". " + list.get(i));
            }
        }
    }

    private String randomString(int length){
        String str = "";

        for(int i = 0; i < length; ++i){
            str += (char)(random.nextInt() % ('z'-'a') + 'a');
        }

        return str;
    }

}
