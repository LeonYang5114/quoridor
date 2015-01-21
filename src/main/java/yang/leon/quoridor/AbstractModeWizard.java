package yang.leon.quoridor;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class AbstractModeWizard extends JPanel {

    public abstract void setModeController(AbstractModeController controller);
    
    public abstract AbstractModeController getModeController();

    public void showWizard(JFrame frame) {
	frame.getContentPane().removeAll();
	frame.getContentPane().add(this);
	frame.setPreferredSize(null);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }

}
