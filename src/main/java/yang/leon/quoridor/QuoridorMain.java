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
//	try {
//	    AbstractController controller = new DefaultController(3);
//	    AbstractView view = new DefaultView(controller);
//	    String name = "ModelAdapter";
//	    IModelAdapter stub = (IModelAdapter) UnicastRemoteObject
//		    .exportObject(controller, 2040);
//	    Registry registry = LocateRegistry.createRegistry(1099);
//	    registry.rebind(name, stub);
//	    System.out.println("ComputeEngine bound");
//	    new QuoridorMain(view);
//	} catch (Exception e) {
//	    System.err.println("ComputeEngine exception:");
//	    e.printStackTrace();
//	}

	try {
	    String name = "ModelAdapter";
	    Registry registry = LocateRegistry.getRegistry("192.168.0.103");
	    IModelAdapter modelAdpt = (IModelAdapter) registry.lookup(name);
	    AbstractView view = new DefaultView(modelAdpt);
	    System.out.println(view.getPreferredSize());
	    new QuoridorMain(view);
	} catch (Exception e) {
	    System.err.println("ComputePi exception:");
	    e.printStackTrace();
	}
    }

}
