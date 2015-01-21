package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.rmi.RemoteException;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultModel;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IRemoteModelAdapter;
import yang.leon.quoridor.Location;
import yang.leon.quoridor.Player;

public abstract class IViewState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 166356488156092621L;
    private AbstractGameView context;

    public IViewState(AbstractGameView context) {
	this.context = context;
    }

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public AbstractGameView getContext() {
	return context;
    }

    public abstract void update(Graphics g);
    
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	return this.getClass().equals(obj.getClass());
    }
}
