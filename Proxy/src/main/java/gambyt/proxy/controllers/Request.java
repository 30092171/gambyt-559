package gambyt.proxy.controllers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class Request<R> implements Future<R> {
	private final CountDownLatch latch = new CountDownLatch(1);
	private Supplier<R> sf;
	private R value;

	public Request(Supplier<R> sf) {
		this.sf = sf;
	}

	public synchronized void fulfil() {
		value = sf.get();
		latch.countDown();
		System.out.println("Request " + this.hashCode() + " fulfilled");
	}

	@Override
	public boolean cancel(boolean arg0) {
		return false;
	}

	@Override
	public R get() throws InterruptedException, ExecutionException {
		System.out.println("Waiting for req to finish");
		latch.await();
		return value;
	}

	@Override
	public R get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
		if (latch.await(arg0, arg1)) {
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
