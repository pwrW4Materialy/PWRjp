import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements RMIInterface{

    private static final long serialVersionUID = 327462342478232L;

    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args){

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        }
        catch (Exception e) {
            System.out.println("Exception starting RMI registry:");
            e.printStackTrace();
        }

        try {
            Naming.rebind("//127.0.0.1/MyServer", new Server());
            System.err.println("Server ready");

        } catch (Exception e) {

            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();

        }
    }

    @Override
    public String helloTo(String name) throws RemoteException {
        System.out.println("client argument: " + name);
        return "Server response, received: " + name;
    }
}