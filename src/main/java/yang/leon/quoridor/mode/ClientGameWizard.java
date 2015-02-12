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
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IRemoteModelAdapter;
import yang.leon.quoridor.IRemoteViewAdapter;
import yang.leon.quoridor.RemoteController;

/**
 * A mode wizard which represents a client in a network game and helps search
 * for the server (host).
 * 
 * @author Leon Yang
 *
 */
public class ClientGameWizard extends AbstractModeWizard {

    /**
     * 
     */
    private static final long serialVersionUID = 2028879025627140646L;

    /**
     * Input area for the host address. If it is blank, it looks for server in
     * the local computer.
     */
    private JTextField tf_hostAddress;

    /**
     * Input are for the host name, which is used to identify the server in the
     * RMI calls.
     */
    private JTextField tf_hostName;

    /**
     * Displays any log related to the connection.
     */
    private JTextArea ta_clientLog;

    /**
     * Button that lists all the local servers on clicked.
     */
    private JButton btn_list;

    /**
     * Button that tries to connect the client to the specific server on
     * clicked.
     */
    private JButton btn_connect;

    /**
     * Constructs the client game wizard and initializes its GUI.
     */
    public ClientGameWizard() {
	super();
	System.out.println("client game wizard");
	setLayout(new GridBagLayout());
	setMinimumSize(new Dimension(300, 250));

	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.insets = new Insets(10, 5, 5, 5);
	add(new JLabel("Host Address: "), c);

	tf_hostAddress = new JTextField();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = 0;
	c.weightx = 1;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = new Insets(10, 0, 5, 5);
	c.gridwidth = 2;
	add(tf_hostAddress, c);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 1;
	c.insets = new Insets(5, 5, 5, 5);
	add(new JLabel("Host Name: "), c);

	tf_hostName = new JTextField();
	c.gridx = 1;
	c.gridy = 1;
	c.weightx = 1;
	c.insets = new Insets(5, 0, 5, 5);
	c.fill = GridBagConstraints.HORIZONTAL;
	add(tf_hostName, c);

	btn_list = new JButton("List Local Servers");
	btn_list.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		ArrayList<DatagramPacket> packets = findServers();
		if (packets.size() == 0) {
		    JOptionPane.showMessageDialog(ClientGameWizard.this,
			    "No local server was found.");
		    return;
		}
		ArrayList<String> serversInfo = new ArrayList<String>();
		for (DatagramPacket packet : packets) {
		    String message = new String(packet.getData()).trim();
		    int nameStart = message.indexOf('<') + 1;
		    int nameEnd = message.lastIndexOf('>');
		    serversInfo.add(packet.getAddress().getHostAddress() + "::"
			    + message.substring(nameStart, nameEnd));
		}

		JList<String> serverList = new JList<String>(serversInfo
			.toArray(new String[serversInfo.size()]));
		serverList.setSelectedIndex(0);
		int result = JOptionPane
			.showConfirmDialog(ClientGameWizard.this, serverList,
				"Select a server",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
		    String selected = serverList.getSelectedValue();
		    tf_hostAddress.setText(selected.substring(0,
			    selected.indexOf("::")));
		    tf_hostName.setText(selected.substring(selected
			    .indexOf("::") + 2));
		}
	    }

	});

	btn_connect = new JButton("Connect Server");
	btn_connect.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    RemoteController controller = new RemoteController();
		    controller.setModeController(getModeController());
		    controller.registerView(new DefaultView());
		    String hostAddress = tf_hostAddress.getText();
		    String name = tf_hostName.getText();
		    IRemoteViewAdapter client = (IRemoteViewAdapter) UnicastRemoteObject
			    .exportObject(controller, 0);
		    IRemoteModelAdapter server = null;
		    try {
			server = (IRemoteModelAdapter) Naming.lookup("rmi://"
				+ hostAddress + "/" + name);
		    } catch (NotBoundException e2) {
			ta_clientLog.append("The host name does not exist. "
				+ "Please choose another one.\n");
			return;
		    }
		    try {
			server.registerRemoteViewAdapter(client);
			client.registerRemoteModelAdapter(server);
		    } catch (RemoteException e2) {
			e2.printStackTrace();
			return;
		    }
		    btn_connect.setEnabled(false);
		    return;
		} catch (Exception e1) {
		    System.err.println("Controller exception:");
		    e1.printStackTrace();
		}
	    }
	});
	JPanel pnl_buttons = new JPanel();
	pnl_buttons.add(btn_list);
	pnl_buttons.add(btn_connect);
	c.gridx = 0;
	c.gridy = 2;
	c.weightx = 1;
	c.gridwidth = 2;
	c.fill = GridBagConstraints.HORIZONTAL;
	add(pnl_buttons, c);

	ta_clientLog = new JTextArea(10, 20);
	ta_clientLog.setLineWrap(true);
	JScrollPane sp_hostLog = new JScrollPane(ta_clientLog);
	sp_hostLog
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	c.gridx = 0;
	c.gridy = 3;
	c.gridwidth = 2;
	c.insets = new Insets(5, 5, 5, 5);
	c.fill = GridBagConstraints.BOTH;
	c.weighty = 1;
	add(sp_hostLog, c);
	c.gridwidth = 1;

    }

    /**
     * Finds the servers in the local network using UDP broadcast and returns a
     * list of <code>String</code>s that contain the servers' address and name.
     * 
     * @return a list of <code>String</code>s that contain the servers' address
     *         and name
     */
    private ArrayList<DatagramPacket> findServers() {
	DatagramSocket c;
	// This segment of code comes from Michiel De Mey
	// http://michieldemey.be/blog/network-discovery-using-udp-broadcast/

	// Find the server using UDP broadcast
	try {
	    // Open a random port to send the package
	    c = new DatagramSocket();
	    c.setBroadcast(true);
	    c.setSoTimeout(500);

	    byte[] sendData = "DISCOVER_QUORIDOR_SERVER_REQUEST".getBytes();

	    // Try the 255.255.255.255 first
	    try {
		DatagramPacket sendPacket = new DatagramPacket(sendData,
			sendData.length,
			InetAddress.getByName("255.255.255.255"), 8888);
		c.send(sendPacket);
		System.out
			.println(getClass().getName()
				+ ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
	    } catch (Exception e) {
	    }

	    // Broadcast the message over all the network interfaces
	    Enumeration<NetworkInterface> interfaces = NetworkInterface
		    .getNetworkInterfaces();
	    while (interfaces.hasMoreElements()) {
		NetworkInterface networkInterface = interfaces.nextElement();

		if (networkInterface.isLoopback() || !networkInterface.isUp()) {
		    continue; // Don't want to broadcast to the loopback
			      // interface
		}

		for (InterfaceAddress interfaceAddress : networkInterface
			.getInterfaceAddresses()) {
		    InetAddress broadcast = interfaceAddress.getBroadcast();
		    if (broadcast == null) {
			continue;
		    }

		    // Send the broadcast package!
		    try {
			DatagramPacket sendPacket = new DatagramPacket(
				sendData, sendData.length, broadcast, 8888);
			c.send(sendPacket);
		    } catch (Exception e) {
		    }

		    System.out.println(getClass().getName()
			    + ">>> Request packet sent to: "
			    + broadcast.getHostAddress() + "; Interface: "
			    + networkInterface.getDisplayName());
		}
	    }

	    System.out
		    .println(getClass().getName()
			    + ">>> Done looping over all network interfaces. Now waiting for a reply!");

	    // Wait for a response
	    byte[] recvBuf = new byte[15000];
	    ArrayList<DatagramPacket> receivePackets = new ArrayList<DatagramPacket>();
	    while (true) {
		DatagramPacket receivePacket = new DatagramPacket(recvBuf,
			recvBuf.length);
		try {
		    c.receive(receivePacket);
		    System.out.println(getClass().getName()
			    + ">>> Broadcast response from server: "
			    + receivePacket.getAddress().getHostAddress());

		    // Check if the message is correct
		    String message = new String(receivePacket.getData()).trim();
		    if (message.contains("QUORIDOR_SERVER_NAME=")) {
			// DO SOMETHING WITH THE SERVER'S IP (for example, store
			// it
			// in your controller)
			boolean duplicate = false;
			for (DatagramPacket p : receivePackets) {
			    if (duplicate)
				break;
			    if (p.getAddress().equals(
				    receivePacket.getAddress())
				    && p.getData().equals(
					    receivePacket.getData()))
				duplicate = true;
			}
			if (!duplicate)
			    receivePackets.add(receivePacket);
		    }
		} catch (SocketTimeoutException ex) {
		    break;
		}
	    }

	    // Close the port!
	    c.close();
	    return receivePackets;
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return null;
    }

    /**
     * Shows message on the log text area if this client is connected to a
     * server.
     * 
     * @param serverName the name of the server this client connected to
     */
    public void serverConnected(String serverName) {
	ta_clientLog.append("Server: " + serverName + " connected.\n");
    }

}
