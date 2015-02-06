package yang.leon.quoridor.mode;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class AbstractModeWizard extends JPanel {

    public abstract void setModeController(AbstractModeController controller);
    
    public abstract AbstractModeController getModeController();

    public void showWizard(JFrame frame) {
	frame.getContentPane().removeAll();
	frame.getContentPane().add(this);
	frame.setMinimumSize(null);
	frame.setMinimumSize(getMinimumSize());
	frame.pack();
	frame.setVisible(true);
	frame.repaint();
    }

}
