package yang.leon.quoridor.mode;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultModel;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IRemoteModelAdapter;
import yang.leon.quoridor.RemoteController;

/**
 * A mode wizard that helps host a network game, listens to client connections,
 * and launches the game.
 * 
 * @author Leon Yang
 *
 */
public class HostGameWizard extends AbstractModeWizard {

    /**
     * 
     */
    private static final long serialVersionUID = 207327648817422356L;

    /**
     * Input area for the name of the host, which is used for identification.
     */
    private JTextField tf_hostName;

    /**
     * Displays any log related to the connection.
     */
    private JTextArea ta_hostLog;

    /**
     * Button that starts the host to listen to client requests on clicked.
     */
    private JButton btn_listen;

    /**
     * Button that launches the game on clicked. This button is only enabled
     * when at least one client has connected to the host.
     */
    private JButton btn_launch;

    /**
     * The game server which provides the game model and connects to all other
     * client remote controllers.
     */
    private RemoteController serverController;

    /**
     * Constructs the host game wizard and initializes its GUI.
     */
    public HostGameWizard() {
	super();
	System.out.println("host game wizard");
	setLayout(new GridBagLayout());
	setMinimumSize(new Dimension(300, 250));

	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.insets = new Insets(10, 5, 5, 5);
	c.weighty = 0.2;
	add(new JLabel("Host Name: "), c);

	tf_hostName = new JTextField();
	c.gridx = 1;
	c.gridy = 0;
	c.weightx = 1;
	c.fill = GridBagConstraints.HORIZONTAL;
	add(tf_hostName, c);

	btn_listen = new JButton("Start to Listen");
	btn_listen.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    serverController = new RemoteController();
		    serverController.setModeController(getModeController());
		    String name = tf_hostName.getText();
		    IRemoteModelAdapter server = (IRemoteModelAdapter) UnicastRemoteObject
			    .exportObject(serverController, 0);
		    Registry registry = null;
		    try {
			registry = LocateRegistry.createRegistry(1099);
		    } catch (RemoteException e2) {
			registry = LocateRegistry.getRegistry();
		    }
		    try {
			registry.lookup(name);
		    } catch (NotBoundException e2) {
			registry.rebind(name, server);
			listenForUDP(name);
			ta_hostLog.append("Server: "
				+ InetAddress.getLocalHost() + "\n");
			ta_hostLog.append("Start listening...\n");
			btn_listen.setEnabled(false);
			return;
		    }
		    ta_hostLog.append("The host name already exists. "
			    + "Please choose another one.\n");
		} catch (Exception e1) {
		    System.err.println("Controller exception:");
		    e1.printStackTrace();
		}
	    }
	});
	c.gridx = 2;
	c.gridy = 0;
	c.weightx = 0;
	c.fill = GridBagConstraints.NONE;
	add(btn_listen, c);

	ta_hostLog = new JTextArea(10, 20);
	ta_hostLog.setLineWrap(true);
	JScrollPane sp_hostLog = new JScrollPane(ta_hostLog);
	sp_hostLog
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	c.gridx = 0;
	c.gridy = 1;
	c.gridwidth = 3;
	c.weightx = 1;
	c.insets = new Insets(5, 5, 5, 5);
	c.fill = GridBagConstraints.BOTH;
	c.weighty = 0.6;
	add(sp_hostLog, c);
	c.gridwidth = 1;

	btn_launch = new JButton("Launch");
	btn_launch.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		RemoteController localController = new RemoteController();
		localController.setModeController(getModeController());
		AbstractGameView view = new DefaultView();
		localController.registerView(view);

		serverController.registerRemoteViewAdapter(localController);
		localController.registerRemoteModelAdapter(serverController);

		serverController.registerModel(new DefaultModel(
			serverController.getRemoteViewAdapters().size()));

		serverController.launchNotify();
	    }
	});
	c.gridx = 0;
	c.gridy = 2;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weighty = 0.2;
	add(btn_launch, c);
	btn_launch.setEnabled(false);

    }

    /**
     * Listens to any UDP client request and send back a message containing the
     * name and address of this server so that the client can know about and
     * connect to this and all other server on the local network.
     * 
     * @param serverName
     */
    private void listenForUDP(final String serverName) {
	Thread listen = new Thread() {
	    // This segment of code comes from Michiel De Mey
	    // http://michieldemey.be/blog/network-discovery-using-udp-broadcast/
	    public void run() {
		DatagramSocket socket;
		try {
		    socket = new DatagramSocket(8888,
			    InetAddress.getByName("0.0.0.0"));
		    socket.setBroadcast(true);

		    while (true) {
			byte[] recvBuf = new byte[15000];
			DatagramPacket packet = new DatagramPacket(recvBuf,
				recvBuf.length);
			socket.receive(packet);

			// Packet received
			System.out.println(getClass().getName()
				+ ">>>Discovery packet received from: "
				+ packet.getAddress().getHostAddress());
			System.out.println(getClass().getName()
				+ ">>>Packet received; data: "
				+ new String(packet.getData()));

			// See if the packet holds the right command (message)
			String message = new String(packet.getData()).trim();
			if (message.equals("DISCOVER_QUORIDOR_SERVER_REQUEST")) {
			    byte[] sendData = ("QUORIDOR_SERVER_NAME=<"
				    + serverName + ">").getBytes();

			    // Send a response
			    DatagramPacket sendPacket = new DatagramPacket(
				    sendData, sendData.length,
				    packet.getAddress(), packet.getPort());
			    socket.send(sendPacket);

			    System.out.println(getClass().getName()
				    + ">>>Sent packet to: "
				    + sendPacket.getAddress().getHostAddress());
			}
		    }
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	    }
	};
	listen.start();
    }

    /**
     * Shows message on the log text area if a client is connected to this
     * server.
     * 
     * @param clientName
     *            the name of the client connected to this server
     */
    public void clientAdded(String clientName) {
	ta_hostLog.append("Client: " + clientName + " connected.\n");
	btn_launch.setEnabled(true);
    }

}
