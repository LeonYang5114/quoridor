package yang.leon.quoridor.mode;

import javax.swing.JFrame;

/**
 * It holds an reference to the game frame and displays the specific mode wizard
 * on the GUI. The wizard accepts the user instructions and invokes methods on
 * this controller, which launches the game.
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractModeController {

    /**
     * Gets the game frame
     * 
     * @return the game frame
     */
    public abstract JFrame getFrame();

    /**
     * Gets the current mode wizard
     * 
     * @return the current mode wizard
     */
    public abstract AbstractModeWizard getCurrModeWizard();

    /**
     * Sets the mode wizard as the wizard constructed dynamically indicated by
     * the name.
     * 
     * @param wizard
     *            the class name of the mode wizard
     */
    public abstract void setModeWizard(String wizard);

    /**
     * Launches the game with a specific {@link AbstractLauncher}.
     * 
     * @param launcher
     *            the <code>AbstractLauncher</code> used to launch the game
     */
    public abstract void launch(AbstractLauncher launcher);

    /**
     * Notifies the mode wizard if a game view is registered on the game
     * controller.
     * 
     * @param clientName
     *            the name of the client who provides the view
     */
    public abstract void viewRegisterNotify(String clientName);

    /**
     * Notifies the mode wizard if a game model is registered on the game
     * controller.
     * 
     * @param clientHost
     *            the name of the server who provides the model
     */
    public abstract void modelRegisterNotify(String clientHost);
}
