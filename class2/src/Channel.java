import java.util.concurrent.*;

// Bidirectional synchronous channel in Java
public class Channel<T> {

	private SynchronousQueue<T> fwd;
	private SynchronousQueue<T> bwd;

	public Channel() {
		// A channel is internally two synchronous blocking queue (of capacity 1)
		// One for communications in one direction ("forward")
		fwd = new SynchronousQueue<T>();
		// and in the opposite direction ("backward")
		bwd = new SynchronousQueue<T>();
	}

	// Get one end point of the channel
	public Endpoint end() {
		return new Endpoint(fwd, bwd);
	}

	// Get the opposite end point
	public Endpoint dualEnd() {
		return new Endpoint(bwd, fwd);
	}


	public class Endpoint {

		private SynchronousQueue<T> internalQueueFwd;
		private SynchronousQueue<T> internalQueueBwd;

		private Endpoint(SynchronousQueue<T> fwd, SynchronousQueue<T> bwd) {
			this.internalQueueFwd = fwd;
			this.internalQueueBwd = bwd;
		}

		public void send(T element) throws InterruptedException {
			// Send from the forward direction
			internalQueueFwd.put(element);
		}

		public T receive() throws InterruptedException {
			return internalQueueBwd.take();
		}

	}

}