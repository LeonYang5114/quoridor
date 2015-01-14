package yang.leon.quoridor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class QuoridorMain extends JFrame {

    public QuoridorMain(JPanel contentPane) {
	super();
	add(contentPane);
	setTitle("Quoridor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
	pack();
	setLocationRelativeTo(null);
	setVisible(true);
    }

    public static void main(String[] args) {
	server();
//	client();
    }

    private static void client() {
	try {
	    String name = "ModelAdapter";
	    Registry registry = LocateRegistry.getRegistry("216.26.121.22");
	    IModelAdapter modelAdpt = (IModelAdapter) registry.lookup(name);
	    AbstractView view = new ClientView(modelAdpt);
	    new QuoridorMain(view);
	} catch (Exception e) {
	    System.err.println("View exception:");
	    e.printStackTrace();
	}
    }

    private static void server() {
	try {
	    System.setProperty("java.rmi.server.hostname", "216.26.121.22");
	    AbstractController controller = new DefaultController(3);
	    AbstractView view = new DefaultView(controller);
	    String name = "ModelAdapter";
	    IModelAdapter stub = (IModelAdapter) UnicastRemoteObject
		    .exportObject(controller, 2040);
	    Registry registry = LocateRegistry.createRegistry(1099);
	    registry.rebind(name, stub);
	    new QuoridorMain(view);
	} catch (Exception e) {
	    System.err.println("Controller exception:");
	    e.printStackTrace();
	}

    }

}
