package yang.leon.quoridor.mode;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A JPanel which accepts user specifications on how a game should launch and
 * tries to launch the game accordingly.
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractModeWizard extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 3868689827883491798L;

    /**
     * The mode controller of this wizard.
     */
    private AbstractModeController controller;

    /**
     * Sets the mode controller of this mode wizard as the given controller.
     * 
     * @param controller
     *            the mode controller to be use
     */
    public void setModeController(AbstractModeController controller) {
	this.controller = controller;
    }

    /**
     * Gets the mode controller of this mode wizard
     * 
     * @return the mode controller of this mode wizard
     */
    public AbstractModeController getModeController() {
	return controller;
    }

    /**
     * Shows this mode wizard in the given game frame.
     * 
     * @param frame
     *            the game frame in which this wizard should be shown
     */
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
