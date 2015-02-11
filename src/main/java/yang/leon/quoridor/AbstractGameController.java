package yang.leon.quoridor;

import yang.leon.quoridor.mode.AbstractModeController;

/**
 * Controller in the MVC structure
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractGameController implements IModelAdapter,
	IViewAdapter {

    /**
     * Sets the given {@link AbstractModeController AbstractModeController} as
     * the mode controller of this game controller
     * 
     * @param controller
     *            the specific mode controller
     */
    public abstract void setModeController(AbstractModeController controller);

    /**
     * Gets the {@link AbstractModeController AbstractModeController} of this
     * game controller.
     * 
     * @return the <code>AbstractModeController</code> of this game controller
     */
    public abstract AbstractModeController getModeController();

    /**
     * Registers an {@link AbstractGameView AbstractGameView} in this game
     * controller. It also sets this controller as the game controller of
     * the view.
     * 
     * @param view
     *            the <code>AbstractGameView</code> to be registered
     */
    public abstract void registerView(AbstractGameView view);

    /**
     * Gets the game view registered in this controller.
     * 
     * @return the game view registered
     */
    public abstract AbstractGameView getView();

    /**
     * Registers an {@link AbstractGameModel AbstractGameModel} in this
     * controller. It also sets this controller as the game controller of
     * the model.
     * 
     * @param model
     *            the <code>AbstractGameModel</code> to be registered
     */
    public abstract void registerModel(AbstractGameModel model);

    /**
     * Gets the game model registered in this controller.
     * 
     * @return the game model registered
     */
    public abstract AbstractGameModel getModel();

}
