package gambyt.proxy.controllers;

import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import gambyt.proxy.ServerNotFoundException;

public class Request<R> implements RemoteFuture<R> {
	private final CountDownLatch latch = new CountDownLatch(1);
	private RemoteSupplier<R> sf;
	private R value;
	private RemoteException re;

	public Request(RemoteSupplier<R> sf) {
		this.sf = sf;
	}

	public synchronized void fulfil() {
		try {
			value = sf.get();
			System.out.println("Request " + this.hashCode() + " fulfilled");
		} catch (RemoteException e) {
			this.re = e;
			System.err.println("RemoteException caught on request " + this.hashCode());
		} catch (ServerNotFoundException e) {
			System.err.println("this shouldn't happen");
			e.printStackTrace();
		}
		latch.countDown();
	}

	@Override
	public boolean cancel(boolean arg0) {
		return false;
	}

	@Override
	public R get() throws InterruptedException, ExecutionException, RemoteException {
		System.out.println("Waiting for req to finish");
		latch.await();
		if(re != null) throw re;
		return value;
	}

	@Override
	public R get(long arg0, TimeUnit arg1)
			throws InterruptedException, ExecutionException, TimeoutException, RemoteException {
		if (latch.await(arg0, arg1)) {
			if(re != null) throw re;
			return value;
		} else {
			throw new TimeoutException();
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return latch.getCount() == 0;
	}
}
