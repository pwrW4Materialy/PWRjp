package Lab2;

public class DummyData{

    private int number;
    private String name;
    private double value;

    public DummyData(){
    }

    public DummyData(int number, String name, double value) {
        this.number = number;
        this.name = name;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(number) + ", " + name + ", " + Double.toString(value);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null){
            return false;
        }
        if (this == object) {
            return true;
        }
        if (getClass() != object.getClass()){
            return false;
        }

        DummyData dummyData = (DummyData) object;

        if (number != dummyData.number || Double.compare(dummyData.value, value) != 0) {
            return false;
        }

        return name != null ? name.equals(dummyData.name) : dummyData.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = number;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
