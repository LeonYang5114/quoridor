package yang.leon.quoridor;

import yang.leon.quoridor.mode.AbstractModeController;

public abstract class AbstractGameController implements IModelAdapter,
	IViewAdapter {

    public abstract void setModeController(AbstractModeController controller);

    public abstract void registerView(AbstractGameView view);
    
    public abstract AbstractGameView getView();

    public abstract void registerModel(AbstractGameModel model);
    
    public abstract AbstractGameModel getModel();

}
