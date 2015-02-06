package yang.leon.quoridor;

import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;

import yang.leon.quoridor.mode.AbstractModeController;

public class DefaultController extends AbstractGameController {

    private AbstractGameView view;
    private AbstractGameModel model;

    private AbstractModeController modeController;

    public DefaultController() {
	this(null);
    }

    public DefaultController(int numPlayers) {
	this(new DefaultModel(numPlayers));
    }

    public DefaultController(AbstractGameModel gameModel) {
	registerModel(gameModel);
    }

    public void setModeController(AbstractModeController controller) {
	modeController = controller;
    }

    public AbstractModeController getModeController() {
	return modeController;
    }

    @Override
    public void update() {
	if (getView() != null)
	    getView().update();
    }

    @Override
    public ArrayList<Location> getCanMoveLocs(Location loc) {
	return model.getCanMoveLocs(loc);
    }

    @Override
    public boolean isCanPutWall(Location loc, int direction) {
	return model.isCanPutWall(loc, direction);
    }

    @Override
    public boolean movePawn(Location newLoc) {
	return model.movePawn(newLoc);
    }

    @Override
    public void putWall(Location loc, int direction) {
	model.putWall(loc, direction);
    }

    @Override
    public Player getPlayer(int playerIndex) {
	return model.getPlayer(playerIndex);
    }

    @Override
    public int getNumPlayers() {
	return model.getNumPlayers();
    }

    @Override
    public String nextPlayer() {
	return model.nextPlayer();
    }

    @Override
    public int getCurrPlayerIndex() {
	return model.getCurrPlayerIndex();
    }

    @Override
    public AbstractGameModel getUpdateDelegate() {
	return (model == null) ? null : model.getUpdateDelegate();
    }

    @Override
    public void update(Graphics g, AbstractGameView context) {
	model.update(g, context);
    }

    @Override
    public void updateAllViews() {
	update();
    }

    @Override
    public void registerModel(AbstractGameModel gameModel) {
	if (model != null)
	    model.setViewAdapter(null);
	model = gameModel;
	if (model != null) {
	    model.setViewAdapter(this);
	    update();
	}
    }

    @Override
    public void registerView(AbstractGameView gameView) {
	if (getView() != null)
	    getView().setModelAdapter(null);
	view = ((gameView != null) ? gameView : new DefaultView());
	getView().setModelAdapter(this);
    }

    public AbstractGameView getView() {
	return view;
    }
    
    public AbstractGameModel getModel() {
	return model;
    }

}
