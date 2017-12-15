package tb.sockets.client;

import tb.sockets.server.Message;
import tb.sockets.server.Ports;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	private ClientNetworkManager clientNetworkManager;
	private NetworkManager networkManager;

	private Ports ports;
	private String ip;

	private JLabel lblNotConnected;
	private JTextArea textArea;
	private JTextField userNameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		clientNetworkManager = new ClientNetworkManager();
		networkManager = new NetworkManager();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(10, 14, 26, 14);
		contentPane.add(lblHost);
		
		JTextField frmtdtxtfldIp;
		frmtdtxtfldIp = new JTextField();

		JFormattedTextField frmtdtxtfldXxxx = new JFormattedTextField();

		try {
			frmtdtxtfldIp.setBounds(43, 11, 90, 20);
			contentPane.add(frmtdtxtfldIp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton btnConnect = new JButton("Connect");

		try {
			//if(!frmtdtxtfldIp.getText().isEmpty() && !frmtdtxtfldXxxx.getText().isEmpty()) {
				btnConnect.addActionListener(actionEvent -> connect(frmtdtxtfldIp.getText(), Integer.parseInt(frmtdtxtfldXxxx.getText())));
			//}
		}
		catch (Exception e){
			System.out.println(e.toString());
		}

		btnConnect.setBounds(10, 70, 95, 23);
		contentPane.add(btnConnect);

		frmtdtxtfldXxxx.setBounds(43, 39, 90, 20);
		contentPane.add(frmtdtxtfldXxxx);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(10, 42, 26, 14);
		contentPane.add(lblPort);
		
		textArea = new JTextArea();
		textArea.setBounds(145, 14, 487, 448);
		contentPane.add(textArea);

		JTextField entry = new JTextField();
		entry.setBounds(145, 470, 300, 30);
		contentPane.add(entry);

		JLabel userNameLabel = new JLabel("User name:");
		userNameLabel.setBounds(10, 142, 96, 44);
		contentPane.add(userNameLabel);

		userNameField = new JTextField();
		userNameField.setBounds(10, 182, 126, 44);
		contentPane.add(userNameField);

		JButton send = new JButton("SEND");
		send.addActionListener(actionEvent -> sendText(entry.getText()));
		send.setBounds(445, 470, 100, 30);
		contentPane.add(send);

		lblNotConnected = new JLabel("Not Connected");
		lblNotConnected.setForeground(new Color(255, 255, 255));
		lblNotConnected.setBackground(new Color(128, 128, 128));
		lblNotConnected.setOpaque(true);
		lblNotConnected.setBounds(10, 104, 123, 23);
		contentPane.add(lblNotConnected);
	}

	private void connect(String ip, int port){
		this.ip = ip;
		lblNotConnected.setText("no connection");

		if(clientNetworkManager.connect(ip, port)){

			try {
				clientNetworkManager.send(getMyIp());
			}
			catch (Exception e){
				System.out.println(e.toString());
			}

			String portsStr = clientNetworkManager.receive();

			try {
				ports = (Ports)CoderDecoder.deserialize(portsStr);

				clientNetworkManager.send("@reboot");
				clientNetworkManager.disconnect();
				clientNetworkManager = new ClientNetworkManager();

				receiveLoop();

				try {
					Thread.sleep(250);
				}
				catch (Exception e){
					System.out.println("unable to skip 100ms");
				}

				clientNetworkManager.connect(ip, ports.getTxPort());


			}
			catch (Exception e){
				System.out.println(e.toString());
			}

			lblNotConnected.setText("connected");
		}
	}

	private void receiveLoop(){
		new Thread(()->{
			networkManager.startServer(ports.getRxPort());
			while (true) {
				try {
					Thread.sleep(1);
				}
				catch (Exception e){
					System.out.println("unable to skip 1ms");
				}

				String msg = networkManager.readMessage();
				try {
					if(msg != null){
						Message message = (Message) CoderDecoder.deserialize(msg);
						textArea.setText(textArea.getText() + "[" + message.getTime() + "]"
								+ " [" + message.getUser() + "]: " + message.getMessage() + "\n");
					}
				}
				catch (Exception e) {

				}
			}
		}).start();
	}

	private void sendText(String text){
		try {
			String user = userNameField.getText();
			if(user.isEmpty()){
				user = "guest";
			}

			clientNetworkManager.send(CoderDecoder.serialize(
					new Message(user, text, new Timestamp(System.currentTimeMillis()).toString())));
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	private String getMyIp() throws Exception{
		List<String> ipList = new ArrayList<>();
		String finalIp = "";
		Enumeration<NetworkInterface> iFaces = NetworkInterface.getNetworkInterfaces();

		while(iFaces.hasMoreElements()){
			NetworkInterface networkInterface = iFaces.nextElement();
			if(networkInterface.isUp()){
				Enumeration<InetAddress> ips = networkInterface.getInetAddresses();
				while (ips.hasMoreElements()){
					ipList.add(ips.nextElement().getHostAddress());
				}
			}
		}

		for(String ip : ipList){
			if(ip.startsWith("192") || ip.startsWith("172") || ip.startsWith("10")){
				finalIp = ip;
			}
		}

		return finalIp;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		clientNetworkManager.send("@reboot");
		clientNetworkManager.disconnect();
	}
}
