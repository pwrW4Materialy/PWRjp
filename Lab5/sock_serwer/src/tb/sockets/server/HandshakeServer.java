package tb.sockets.server;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

public class HandshakeServer {

    private int rxPort;
    private int txPort;

    private NetworkManager networkManager;
    private ReplaySubject<String> replaySubject;

    public HandshakeServer(int serverPort, int rxStart, int txStart){
        this.rxPort = rxStart;
        this.txPort = txStart;
        networkManager = new NetworkManager();
        networkManager.startServer(serverPort);
        replaySubject = ReplaySubject.create();
    }

    public void run(){
        while (true) {
            String ip = networkManager.readMessage();

            System.out.println(ip + " connected");

            try {
                networkManager.send(CoderDecoder.serialize(new Ports(rxPort, txPort)));
            } catch (Exception e) {
                System.out.println("Ports serialization error");
            }

            replaySubject.subscribeOn(Schedulers.computation()).subscribe(new ClientObserver(ip, rxPort, txPort, replaySubject));

            rxPort++;
            txPort++;
        }
    }

    public int getRxPort() {
        return rxPort;
    }

    public int getTxPort() {
        return txPort;
    }
}
