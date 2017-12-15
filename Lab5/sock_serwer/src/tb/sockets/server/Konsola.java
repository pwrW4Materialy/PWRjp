package tb.sockets.server;

public class Konsola {

	public static void main(String[] args) {
		HandshakeServer handshakeServer = new HandshakeServer(8666, 12128, 12256);
		handshakeServer.run();
	}
}
