package tearoom;

/*

CO661 : Theory and Practice of Concurrency
School of Computing, University of Kent
Dominic Orchard, 2018

*/

import java.util.concurrent.ThreadLocalRandom;

// Classes for representing items
public class Tea {

	private int drinkTime;

	public Tea() {
		this.drinkTime = ThreadLocalRandom.current().nextInt(1000,3000);
	}

	public void drink() {
		try {
			Thread.sleep(this.drinkTime);
		} catch (InterruptedException e) {
			System.out.println("Spilled the tea");
		}
	}

}
