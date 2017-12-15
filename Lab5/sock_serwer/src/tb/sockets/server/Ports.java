package tb.sockets.server;

import java.io.Serializable;

class Ports implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private int rxPort;
    private int txPort;

    public Ports(int rxPort, int txPort) {
        this.rxPort = rxPort;
        this.txPort = txPort;
    }

    public int getRxPort() {
        return rxPort;
    }

    public void setRxPort(int rxPort) {
        this.rxPort = rxPort;
    }

    public int getTxPort() {
        return txPort;
    }

    public void setTxPort(int txPort) {
        this.txPort = txPort;
    }
}
