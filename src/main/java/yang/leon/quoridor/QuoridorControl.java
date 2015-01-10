package yang.leon.quoridor;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import yang.leon.quoridor.state.ViewState;

public class QuoridorControl {
    private QuoridorModel model;
    private QuoridorView view;

    public QuoridorControl(int numPlayers) {
	model = new QuoridorModel(numPlayers, new ViewControlAdapter() {

	    @Override
	    public void setViewState(ViewState aState) {
		view.setViewState(aState);
	    }

	    @Override
	    public ViewState getViewState() {
		return view.getViewState();
	    }

	    @Override
	    public Image getImage(String key) {
		return view.getImage(key);
	    }

	    @Override
	    public void drawWall(Graphics g, Point p, int direction, String arg) {
		view.drawWall(g, p, direction, arg);
	    }

	}, new ViewUpdateAdapter() {

	    @Override
	    public void update() {
		view.update();
	    }

	});

	view = new QuoridorView(new ModelControlAdapter() {

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
	    public void nextPlayer(QuoridorView context) {
		model.nextPlayer(context);
	    }

	    @Override
	    public int getCurrPlayerIndex() {
		return model.getCurrPlayerIndex();
	    }

	}, new ModelUpdateAdapter() {

	    @Override
	    public void update(Graphics g) {
		model.update(g);
	    }

	});
    }

    public QuoridorModel getModel() {
	return model;
    }

    public QuoridorView getView() {
	return view;
    }

    public static void main(String[] args) {
	int numPlayers = 2;
	if (args.length > 1)
	    try {
		numPlayers = Integer.parseInt(args[1]);
	    } catch (NumberFormatException ext) {
	    }
	new QuoridorControl(numPlayers);
    }
}
