package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import yang.leon.quoridor.AbstractView;

public class WaitingState extends IViewState {

    public WaitingState(AbstractView context) {
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
