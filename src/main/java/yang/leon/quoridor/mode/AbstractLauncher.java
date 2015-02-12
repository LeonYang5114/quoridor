package yang.leon.quoridor.mode;

import javax.swing.JFrame;

/**
 * It provides a method to launch the game. The implementation should be
 * specified by a mode wizard or a view related object, and the instance should be
 * passed to an object which has the reference to the game frame.
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractLauncher {

    /**
     * Launches the game by displaying the game view.
     * 
     * @param frame the game frame
     */
    public abstract void launch(JFrame frame);
}
