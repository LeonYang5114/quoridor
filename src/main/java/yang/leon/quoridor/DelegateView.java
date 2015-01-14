package yang.leon.quoridor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

import yang.leon.quoridor.state.IViewState;

class DelegateView extends AbstractView {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3485230108525274330L;

    public DelegateView() {	
    }

    @Override
    public Image getImage(String key) {
	return null;
    }

    @Override
    public void resetGUI() {
    }

    @Override
    public void update() {
    }

    @Override
    public JPanel getGridPanel() {
	return null;
    }

    @Override
    public void drawWall(Graphics g, Point p, int direction, String arg) {
    }

    @Override
    public void drawBackground(Graphics g) {
    }

    @Override
    public void drawPawn(Graphics g, Point p) {
    }

    @Override
    public void win(int currPlayerIndex) {
    }

    @Override
    public void disableButtons() {
    }

    @Override
    public IViewState getViewState() {
	return null;
    }

    @Override
    public void setViewState(IViewState aState) {	
    }

}
