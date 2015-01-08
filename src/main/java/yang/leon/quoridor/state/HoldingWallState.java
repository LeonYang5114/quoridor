package yang.leon.quoridor.state;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorModel;
import yang.leon.quoridor.QuoridorView;

public class HoldingWallState extends ViewState {

    private int direction;

    public HoldingWallState(int playerIndex) {
	super(playerIndex);
	direction = QuoridorModel.HORIZONTAL_WALL;
    }

    @Override
    public void mouseClicked(MouseEvent e, QuoridorView context) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    direction = (direction == QuoridorModel.HORIZONTAL_WALL) ? QuoridorModel.VERTICAL_WALL
		    : QuoridorModel.HORIZONTAL_WALL;
	    context.update();
	    return;
	}
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = QuoridorView.getCrsLocAtPoint(e.getPoint());
	ModelControlAdapter adpt = context.getModelCtrlAdpt();
	if (adpt.isCanPutWall(loc, direction)) {
	    adpt.putWall(adpt.getPlayer(getPlayerIndex()), loc, direction);
	}
    }

    @Override
    public void update(QuoridorView context) {
	// TODO Auto-generated method stub
	
    }
}
