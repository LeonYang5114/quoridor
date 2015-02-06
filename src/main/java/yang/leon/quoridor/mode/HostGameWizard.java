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

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultModel;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IRemoteModelAdapter;
import yang.leon.quoridor.RemoteController;

public class HostGameWizard extends AbstractModeWizard {

    private AbstractModeController controller;

    private JTextField tf_hostName;
    private JTextArea ta_hostLog;

    private JButton btn_listen;
    private JButton btn_launch;

    public HostGameWizard() {
	super();
	System.out.println("host game wizard");
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

	btn_listen = new JButton("Start to Listen");
	btn_listen.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    RemoteController controller = new RemoteController();
		    AbstractGameView view = new DefaultView();
		    controller.registerView(view);
		    getModeController().setGameController(controller);
		    String name = tf_hostName.getText();
		    IRemoteModelAdapter server = (IRemoteModelAdapter) UnicastRemoteObject
			    .exportObject(controller, 0);
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
	c.weightx = 0.3;
	c.fill = GridBagConstraints.NONE;
	add(btn_listen, c);

	ta_hostLog = new JTextArea();
	JScrollPane sp_hostLog = new JScrollPane(ta_hostLog);
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

	btn_launch = new JButton("Launch");
	btn_launch.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		final RemoteController controller = (RemoteController) getModeController()
			.getGameController();
		controller.registerModel(new DefaultModel(controller
			.getRemoteViewAdapters().size() + 1));
		AbstractLauncher launcher = new AbstractLauncher() {
		    @Override
		    public void launch(JFrame frame) {
			frame.getContentPane().removeAll();
			controller.launchNotify();
			frame.getContentPane().add(controller.getView());
			frame.setPreferredSize(null);
			frame.pack();
			frame.setLocationRelativeTo(null);
		    }
		};
		getModeController().launch(launcher);
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

    public void setModeController(AbstractModeController controller) {
	this.controller = controller;
    }

    public AbstractModeController getModeController() {
	return controller;
    }

    public void clientAdded(String clientName) {
	ta_hostLog.append("Client: " + clientName + " connected.\n");
	btn_launch.setEnabled(true);
    }

}
