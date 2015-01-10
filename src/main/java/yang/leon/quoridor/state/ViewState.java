package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.Player;
import yang.leon.quoridor.QuoridorModel;
import yang.leon.quoridor.QuoridorView;

public abstract class ViewState {

    private QuoridorView context;

    public ViewState(QuoridorView context) {
	this.context = context;
    }

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public QuoridorView getContext() {
	return context;
    }

    public abstract void update(Graphics g);

    public void drawCurrPlayer(Graphics g) {
	ModelControlAdapter adpt = getContext().getModelCtrlAdpt();
	Player player = adpt.getPlayer(adpt.getCurrPlayerIndex());
	switch (player.getTargetEdge()) {
	case QuoridorModel.NORTH_EDGE:
	    g.drawImage(getContext().getImage("target.edge.horizontal"), 0, 0,
		    null);
	    break;
	case QuoridorModel.WEST_EDGE:
	    g.drawImage(getContext().getImage("target.edge.vertical"), 0, 0,
		    null);
	    break;
	case QuoridorModel.SOUTH_EDGE:
	    g.drawImage(getContext().getImage("target.edge.horizontal"), 0,
		    404, null);
	    break;
	case QuoridorModel.EAST_EDGE:
	    g.drawImage(getContext().getImage("target.edge.vertical"), 404, 0,
		    null);
	    break;
	default:
	    break;
	}
	Location pawnLoc = player.getPawnLoc();
	Point pawnPoint = QuoridorView.getPointFromSqrLoc(pawnLoc);
	g.drawImage(getContext().getImage("current.pawn"), pawnPoint.x,
		pawnPoint.y, null);
    }
}
