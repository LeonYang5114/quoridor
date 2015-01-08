package yang.leon.quoridor.state;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorView;

public class InitState extends ViewState {

    public InitState(int playerIndex) {
	super(playerIndex);
    }

    @Override
    public void mouseClicked(MouseEvent e, QuoridorView context) {
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = QuoridorView.getSqrLocAtPoint(e.getPoint());
	ModelControlAdapter adpt = context.getModelCtrlAdpt();
	if (adpt.getPlayer(getPlayerIndex()).getPawnLoc().equals(loc)) {
	    context.setState(new MovingPawnState((getPlayerIndex() + 1)
		    / adpt.getNumPlayers()));
	    context.update();
	}
    }

    @Override
    public void update(QuoridorView context) {
    }
}
