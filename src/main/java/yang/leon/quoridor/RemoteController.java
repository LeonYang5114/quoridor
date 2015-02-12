package yang.leon.quoridor;

import java.awt.Graphics;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

import yang.leon.quoridor.mode.AbstractLauncher;

public class RemoteController extends DefaultController implements
	IRemoteViewAdapter, IRemoteModelAdapter {

    private ArrayList<IRemoteViewAdapter> remoteViewAdpts;
    private IRemoteModelAdapter remoteModelAdpt;

    public RemoteController() {
	this(null);
    }

    public RemoteController(AbstractGameModel gameModel) {
	super(gameModel);
	remoteViewAdpts = new ArrayList<IRemoteViewAdapter>();
    }

    @Override
    public void update() {
	for (IRemoteViewAdapter viewAdpt : remoteViewAdpts) {
	    try {
		viewAdpt.update();
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	super.update();
    }

    @Override
    public ArrayList<Location> getCanMoveLocs(Location loc) {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.getCanMoveLocs(loc);
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.getCanMoveLocs(loc);
    }

    @Override
    public boolean isCanPutWall(Location loc, int direction) {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.isCanPutWall(loc, direction);
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.isCanPutWall(loc, direction);
    }

    @Override
    public boolean movePawn(Location newLoc) {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.movePawn(newLoc);
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.movePawn(newLoc);
    }

    @Override
    public void putWall(Location loc, int direction) {
	if (remoteModelAdpt != null) {
	    try {
		remoteModelAdpt.putWall(loc, direction);
		;
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	} else
	    super.putWall(loc, direction);
    }

    @Override
    public Player getPlayer(int playerIndex) {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.getPlayer(playerIndex);
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.getPlayer(playerIndex);
    }

    @Override
    public int getNumPlayers() {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.getNumPlayers();
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.getNumPlayers();
    }

    @Override
    public void nextPlayer() {
	if (remoteModelAdpt != null) {
	    try {
		remoteModelAdpt.nextPlayer();
		return;
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	} else {
	    try {
		getRemoteViewAdapters().get(getCurrPlayerIndex()).setViewState(
			"WaitingState");
		super.nextPlayer();
		getRemoteViewAdapters().get(getCurrPlayerIndex()).setViewState(
			"InitialState");
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public int getCurrPlayerIndex() {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.getCurrPlayerIndex();
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.getCurrPlayerIndex();
    }

    @Override
    public AbstractGameModel getUpdateDelegate() {
	if (remoteModelAdpt != null) {
	    try {
		return remoteModelAdpt.getUpdateDelegate();
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	}
	return super.getUpdateDelegate();
    }

    @Override
    public void update(Graphics g, AbstractGameView context) {
	if (remoteModelAdpt != null) {
	    try {
		remoteModelAdpt.update(g, context);
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	} else
	    super.update(g, context);
    }

    @Override
    public void updateAllViews() {
	if (remoteModelAdpt != null) {
	    try {
		remoteModelAdpt.updateAllViews();
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	} else
	    update();
    }

    public void registerRemoteViewAdapter(IRemoteViewAdapter remoteViewAdpt) {
	if (remoteViewAdpt == null)
	    return;
	remoteViewAdpts.add(remoteViewAdpt);
	try {
	    getModeController()
		    .viewRegisterNotify(RemoteServer.getClientHost());
	} catch (ServerNotActiveException e) {
	    getModeController().viewRegisterNotify("Local");
	}
    }

    public void registerRemoteModelAdapter(IRemoteModelAdapter remoteModelAdpt) {
	this.remoteModelAdpt = remoteModelAdpt;
	try {
	    getModeController().modelRegisterNotify(
		    RemoteServer.getClientHost());
	} catch (ServerNotActiveException e) {
	    getModeController().modelRegisterNotify("Local");
	}
    }

    public void launchNotify() {
	Collections.shuffle(getRemoteViewAdapters());
	boolean isFirst = true;
	for (IRemoteViewAdapter viewAdpt : getRemoteViewAdapters()) {
	    try {
		viewAdpt.launch(isFirst);
	    } catch (RemoteException e) {
		e.printStackTrace();
	    }
	    isFirst = false;
	}
    }

    public void launch(boolean isFirst) {
	if (isFirst) {
	    getView().setViewState("InitialState");
	}
	getModeController().launch(new AbstractLauncher() {
	    @Override
	    public void launch(JFrame frame) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(getView());
		frame.setPreferredSize(null);
		frame.pack();
		frame.setLocationRelativeTo(null);
	    }
	});
    }

    public ArrayList<IRemoteViewAdapter> getRemoteViewAdapters() {
	return remoteViewAdpts;
    }

    public IRemoteModelAdapter getRemoteModelAdapter() {
	return remoteModelAdpt;
    }
}
