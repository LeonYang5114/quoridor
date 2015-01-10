package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorView;

public class WonState extends WaitingState {

    public WonState(QuoridorView context) {
	super(context);
    }

    public void update(Graphics g) {
	ModelControlAdapter adpt = getContext().getModelCtrlAdpt();
	Location pawnLoc = adpt.getPlayer(adpt.getCurrPlayerIndex())
		.getPawnLoc();
	Point pawnPoint = QuoridorView.getPointFromSqrLoc(pawnLoc);
	g.drawImage(getContext().getImage("winner"), pawnPoint.x, pawnPoint.y,
		null);
    }

    public String toString() {
	return "Won state.";
    }

}
