package tb.sockets.server;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class ClientObserver implements Observer<String> {

    private String ip;
    private int rx, tx;

    private NetworkManager networkManager;
    private ClientNetworkManager clientNetworkManager;
    private  ReplaySubject<String> replaySubject;

    public ClientObserver(String ip, int rx, int tx, ReplaySubject<String> replaySubject){
        this.replaySubject = replaySubject;
        this.ip = ip;
        this.rx = rx;
        this.tx = tx;

        this.networkManager = new NetworkManager();
        this.clientNetworkManager = new ClientNetworkManager();
    }

    private void listen(){
        new Thread(()->{
            networkManager.startServer(tx);
            while (true){
                try {
                    Thread.sleep(1);
                }
                catch (Exception e){
                    System.out.println("unable to skip 1ms");
                }

                String msg = networkManager.readMessage();
                if (msg != null) {

                    if (msg.equals("@reboot")) {
                        this.onComplete();
                    }

                    replaySubject.onNext(msg);
                    displayMsg(msg);
                }
            }
        }).start();
    }

    private void displayMsg(String msg){
        try {
            Message message = (Message) CoderDecoder.deserialize(msg);
            System.out.println(message.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        listen();

        try {
            Thread.sleep(50);
        }
        catch (Exception e){
            System.out.println("unable to skip 100ms");
        }

        clientNetworkManager.connect(ip, rx);

        if(clientNetworkManager.getStatus() == 1) {
            System.out.println("connection established");
        }
        else {
            System.out.println("connection error");
        }

        System.out.println("subscribed");
    }

    @Override
    public void onNext(String message) {
        clientNetworkManager.send(message);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.toString());
    }

    @Override
    public void onComplete() {
        System.out.println("user disconnected");
        networkManager.shutdownServer();
        clientNetworkManager.disconnect();
    }
}
