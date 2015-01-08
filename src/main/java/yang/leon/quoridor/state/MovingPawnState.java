package yang.leon.quoridor.state;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorView;

public class MovingPawnState extends ViewState {

    public MovingPawnState(int playerIndex) {
	super(playerIndex);
    }

    @Override
    public void mouseClicked(MouseEvent e, QuoridorView context) {
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = QuoridorView.getSqrLocAtPoint(e.getPoint());
	ModelControlAdapter adpt = context.getModelCtrlAdpt();
	ArrayList<Location> canMoveLocs = adpt.getCanMoveLocs(adpt.getPlayer(
		getPlayerIndex()).getPawnLoc());
	if (!canMoveLocs.contains(loc))
	    return;
	adpt.movePawn(adpt.getPlayer(getPlayerIndex()), loc);
	context.setState(new InitState((getPlayerIndex() + 1)
		/ adpt.getNumPlayers()));
	context.update();
    }

    @Override
    public void update(QuoridorView context) {
	ModelControlAdapter adpt = context.getModelCtrlAdpt();
	ArrayList<Location> canMoveLocs = adpt.getCanMoveLocs(adpt.getPlayer(
		getPlayerIndex()).getPawnLoc());
    }
}
