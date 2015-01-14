package yang.leon.quoridor;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.Timer;

import yang.leon.quoridor.state.IViewState;

public class ClientView extends DefaultView {

    /**
     * 
     */
    private static final long serialVersionUID = -6815999624562923637L;
    private static int pollInterval = 50;
    private Timer pollTimer;

    private AbstractView viewDelegate;

    private AbstractModel updateDelegate;

    public ClientView() {
	this(null);
    }

    public ClientView(IModelAdapter modelAdpt) {
	viewDelegate = new DelegateView() {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = -5642956371266782270L;

	    public void update() {
		try {
		    getModelAdapter().requestWaitForUpdate();
		} catch (RemoteException e) {
		    e.printStackTrace();
		}
	    }
	};
	loadImages();
	try {
	    modelAdpt.registerView(viewDelegate);
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
	viewDelegate.setModelAdapter(modelAdpt);
	pollTimer = new Timer(pollInterval, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		IModelAdapter adpt = getModelAdapter();
		try {
		    AbstractView updating = adpt.getUpdatingView();
		    if (viewDelegate.equals(updating)) {
			updateDelegate = adpt.getUpdateDelegate();
			repaint();
			getModelAdapter().doneWithUpdateNotify(viewDelegate);
		    }
		} catch (RemoteException e1) {
		    e1.printStackTrace();
		}
	    }

	});
	setModelAdapter(modelAdpt);
	try {
	    updateDelegate = modelAdpt.getUpdateDelegate();
	} catch (RemoteException e1) {
	    e1.printStackTrace();
	}
	pollTimer.start();
    }

    JPanel initGridPanel() {
	return new JPanel() {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = -6491337289532664327L;

	    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateDelegate.update(g, ClientView.this);
		getViewState().update(g);
	    }
	};
    }

    public void setViewState(IViewState aState) {
	super.setViewState(aState);
    }

    public void setVisible(boolean flag) {
	super.setVisible(flag);
	if (flag)
	    pollTimer.restart();
	else
	    pollTimer.stop();
    }
}
