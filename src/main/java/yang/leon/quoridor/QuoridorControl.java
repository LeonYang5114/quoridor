package yang.leon.quoridor;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

public class QuoridorControl {
    private QuoridorModel model;
    private QuoridorView view;

    public QuoridorControl(int numPlayers) {
	model = new QuoridorModel(numPlayers, new ViewControlAdapter() {

	    @Override
	    public Component getComponent() {
		return view;
	    }

	}, new ViewUpdateAdapter() {

	    @Override
	    public void update() {
		view.repaint();
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
	    public void movePawn(Player p, Location newLoc) {
		model.movePawn(p, newLoc);
	    }

	    @Override
	    public void putWall(Player p, Location loc, int direction) {
		model.putWall(p, loc, direction);

	    }

	    @Override
	    public Player getPlayer(int playerIndex) {
		return model.getPlayer(playerIndex);
	    }

	    @Override
	    public int getNumPlayers() {
		return model.getNumPlayers();
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
	new QuoridorControl(2);
    }
}
