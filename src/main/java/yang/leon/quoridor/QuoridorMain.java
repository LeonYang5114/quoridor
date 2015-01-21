package yang.leon.quoridor;

import javax.swing.JFrame;

public class QuoridorMain extends JFrame {

    public QuoridorMain() {
	super();
	setTitle("Quoridor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
	QuoridorMain main = new QuoridorMain();
	AbstractModeController controller = new ModeController(main);
	controller.setModeWizard(new HostGameWizard());
	controller.showWizard();
    }

}
