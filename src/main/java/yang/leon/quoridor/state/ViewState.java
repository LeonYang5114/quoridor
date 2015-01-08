package yang.leon.quoridor.state;

import java.awt.event.MouseEvent;

import yang.leon.quoridor.QuoridorView;


public abstract class ViewState {
    
    private int playerIndex;
    
    public ViewState(int playerIndex) {
	this.playerIndex = playerIndex;
    }
    
    public abstract void mouseClicked(MouseEvent e, QuoridorView context);
    
    public int getPlayerIndex() {
	return playerIndex;
    }
    
    public abstract void update(QuoridorView context);
}
