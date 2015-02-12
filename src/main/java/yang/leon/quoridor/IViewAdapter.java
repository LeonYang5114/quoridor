package yang.leon.quoridor;

/**
 * Specifies the methods that a game method can call to modify the game model
 * indirectly. Any game controller implements this interface.
 * 
 * @author Leon Yang
 * @see AbstractGameController
 */
public interface IViewAdapter {

    /**
     * Notifies the game view that an update is necessary if it is not null. The
     * game view's {@link AbstractGameView#update() update()} method will be
     * called.
     */
    public void update();

    /**
     * Calls the game view's {@link AbstractGameView#setViewState(String)
     * setViewState} method if it is not null.
     * 
     * @param stateName
     *            the class name of a specific <code>AbstractViewState</code> to be
     *            constructed and set
     */
    public void setViewState(String stateName);
}
