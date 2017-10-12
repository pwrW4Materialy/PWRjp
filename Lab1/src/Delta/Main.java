package Delta;

import java.util.Scanner;

public class Main {

    private static DeltaCounter deltaCounter;
    private static double a, b, c;

    public static void main(String[] args) {

        readInput();

        deltaCounter = new DeltaCounter(a, b, c);
        deltaCounter.count();
        deltaCounter.displayResults();

        System.exit(0);
    }

    private static void readInput(){
        Scanner reader = new Scanner(System.in);
        boolean goodEntry;

        double[] inputs = new double[3];

        for(int i = 0; i < 3;  ++i) {
            goodEntry = false;
            while (!goodEntry) {
                try {
                    System.out.println("Enter " + Integer.toString(i+1) + ": ");
                    String inStr = reader.next();
                    inputs[i] = Double.parseDouble(inStr);
                    goodEntry = true;
                } catch (Exception e) {
                    reader.nextLine();
                    System.out.println("Invalid entry, try again");
                }
            }
        }

        reader.close();

        a = inputs[0];
        b = inputs[1];
        c = inputs[2];

        reader.close();
    }
}
