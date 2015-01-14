package yang.leon.quoridor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallbackClientView extends Remote {

    public void update() throws RemoteException;

    public void setModelAdapter(IModelAdapter modelAdpt)
	    throws RemoteException;

}
