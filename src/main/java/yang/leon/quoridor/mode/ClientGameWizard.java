package yang.leon.quoridor.mode;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IRemoteModelAdapter;
import yang.leon.quoridor.IRemoteViewAdapter;
import yang.leon.quoridor.RemoteController;

public class ClientGameWizard extends AbstractModeWizard {

    private AbstractModeController controller;

    private JTextField tf_hostName;
    private JTextArea ta_clientLog;

    private JButton btn_find;

    public ClientGameWizard() {
	super();
	System.out.println("client game wizard");
	setLayout(new GridBagLayout());
	setMinimumSize(new Dimension(300, 250));

	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.weightx = 0.2;
	c.insets = new Insets(10, 5, 5, 5);
	c.weighty = 0.2;
	add(new JLabel("Host Name: "), c);

	tf_hostName = new JTextField();
	c.gridx = 1;
	c.gridy = 0;
	c.weightx = 0.5;
	c.fill = GridBagConstraints.HORIZONTAL;
	add(tf_hostName, c);

	btn_find = new JButton("Start to Find");
	btn_find.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    RemoteController controller = new RemoteController();
		    controller.setModeController(getModeController());
		    controller.registerView(new DefaultView());
		    String name = tf_hostName.getText();
		    IRemoteViewAdapter client = (IRemoteViewAdapter) UnicastRemoteObject
			    .exportObject(controller, 0);
		    Registry registry = null;
		    try {
			registry = LocateRegistry.createRegistry(1099);
		    } catch (RemoteException e2) {
			registry = LocateRegistry.getRegistry();
		    }
		    IRemoteModelAdapter server = null;
		    try {
			server = (IRemoteModelAdapter) registry.lookup(name);
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
		    btn_find.setEnabled(false);
		    return;
		} catch (Exception e1) {
		    System.err.println("Controller exception:");
		    e1.printStackTrace();
		}
	    }
	});
	c.gridx = 2;
	c.gridy = 0;
	c.weightx = 0.3;
	c.fill = GridBagConstraints.NONE;
	add(btn_find, c);

	ta_clientLog = new JTextArea();
	JScrollPane sp_hostLog = new JScrollPane(ta_clientLog);
	sp_hostLog
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	c.gridx = 0;
	c.gridy = 1;
	c.gridwidth = 3;
	c.insets = new Insets(5, 5, 5, 5);
	c.fill = GridBagConstraints.BOTH;
	c.weighty = 0.6;
	add(sp_hostLog, c);
	c.gridwidth = 1;

    }

    public void setModeController(AbstractModeController controller) {
	this.controller = controller;
    }

    public AbstractModeController getModeController() {
	return controller;
    }

    public void serverConnected(String serverName) {
	ta_clientLog.append("Server: " + serverName + " connected.\n");
    }

}
