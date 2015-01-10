package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import yang.leon.quoridor.QuoridorView;

public class WaitingState extends ViewState {

    public WaitingState(QuoridorView context) {
	super(context);
	getContext().disableButtons();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void update(Graphics g) {
	drawCurrPlayer(g);
    }
    
    public String toString() {
	return "Waiting state.";
    }

}
