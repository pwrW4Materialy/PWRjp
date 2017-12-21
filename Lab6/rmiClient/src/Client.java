import java.rmi.Naming;
import java.util.Scanner;

public class Client {

    private static RMIInterface rmiInterface;

    public static void main(String[] args){

        Scanner reader = new Scanner(System.in);

        while (true) {

            System.out.println("Enter your name");
            String input = reader.nextLine();

            try {
                System.out.println(rmiHello(input));
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println();
        }
    }

    public static String rmiHello(String name) throws Exception{
        rmiInterface = (RMIInterface) Naming.lookup("//127.0.0.1/MyServer");
        return rmiInterface.helloTo(name);
    }
}
