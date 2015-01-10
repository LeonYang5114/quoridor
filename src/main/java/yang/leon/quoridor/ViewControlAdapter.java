package yang.leon.quoridor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import yang.leon.quoridor.state.ViewState;

public interface ViewControlAdapter {

    public void setViewState(ViewState aState);
    
    public ViewState getViewState();
    
    public Image getImage(String key);
    
    public void drawWall(Graphics g, Point p, int direction, String arg);
}
