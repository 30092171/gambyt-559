package gambyt.proxy.controllers;

import java.rmi.RemoteException;

import gambyt.proxy.ServerNotFoundException;

public interface RemoteSupplier<T> {

	public T get() throws RemoteException, ServerNotFoundException;

}
