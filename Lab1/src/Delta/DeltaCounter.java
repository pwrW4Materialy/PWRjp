package Delta;

public class DeltaCounter {

    private double a, b, c;
    private double x1;
    private double x2;

    public int getDeltaState() {
        return deltaState;
    }

    private int deltaState = -1;

    public DeltaCounter(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void count(){
        double delta = b*b - 4.0 * a * c;

        if(delta < 0.0){
            deltaState = 0;
        }
        else if(Math.abs(delta) < 0.000001){
            x1 = -b / (a * 2.0);
            x2 = x1;
            deltaState = 1;
        }
        else{
            x1 = (Math.sqrt(delta) + b) / (a * 2.0);
            x2 = (-Math.sqrt(delta) + b) / (a * 2.0);
            deltaState = 2;
        }
    }

    void displayResults(){
        switch (deltaState){
            case 0:
                System.out.println("Brak rozw.");
                break;
            case 1:
                System.out.println("Jedno rozw.: " + Double.toString(x1));
                break;
            case 2:
                System.out.println("x1: " + Double.toString(x1) + " x2: " + Double.toString(x2));
        }
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

}
