package yang.leon.quoridor;

import java.awt.Graphics;
import java.util.ArrayList;

public class DefaultController extends AbstractController {

    private ArrayList<AbstractView> views;
    private AbstractModel model;

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

    public void setModel(AbstractModel abstractModel) {
	if (model != null)
	    model.setViewAdapter(null);
	model = (abstractModel != null) ? abstractModel : new DefaultModel(2);
	model.setViewAdapter(this);
	update(null);
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
    public void nextPlayer(AbstractView context) {
	model.nextPlayer(context);
    }

    @Override
    public int getCurrPlayerIndex() {
	return model.getCurrPlayerIndex();
    }

    @Override
    public void update(Graphics g, AbstractView context) {
	model.update(g, context);
    }

    @Override
    public void update(AbstractView invoker) {
	for (AbstractView view : views)
	    if (!view.equals(invoker))
		view.update();
    }

}
