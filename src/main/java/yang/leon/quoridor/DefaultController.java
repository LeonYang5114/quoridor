package yang.leon.quoridor;

import java.util.ArrayList;

public class DefaultController extends AbstractController {

    private ArrayList<AbstractView> views;
    private AbstractModel model;

    private AbstractView updatingView;

    public DefaultController() {
	this(2);
    }

    public DefaultController(int numPlayers) {
	this(new DefaultModel(numPlayers));
    }

    public DefaultController(AbstractModel model) {
	views = new ArrayList<AbstractView>();
	setModel(model);
    }

    public AbstractView getUpdatingView() {
	return updatingView;
    }

    public void setModel(AbstractModel abstractModel) {
	if (model != null)
	    model.setViewAdapter(null);
	model = (abstractModel != null) ? abstractModel : new DefaultModel(2);
	model.setViewAdapter(this);
	update();
    }

    public void registerView(AbstractView abstractView) {
	views.add(abstractView);
	abstractView.setModelAdapter(this);
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
    public AbstractModel getUpdateDelegate() {
	return model.getUpdateDelegate();
    }

    @Override
    public void requestWaitForUpdate() {
	model.requestWaitForUpdate();
    }

    @Override
    public void doneWithUpdateNotify(AbstractView context) {
	if (updatingView == null || context.equals(updatingView)) {
	    model.doneWithUpdateNotify();
	    updatingView = null;
	}
    }

    @Override
    public void update() {
	for (AbstractView view : views) {
	    updatingView = view;
	    view.update();
	}
    }
    
    @Override
    public void requestUpdate() {
	update();
    }

}
