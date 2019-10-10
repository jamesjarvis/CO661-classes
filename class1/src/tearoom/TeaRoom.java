package tearoom;

/*

CO661 : Theory and Practice of Concurrency
School of Computing, University of Kent
Dominic Orchard, 2018

*/

import java.util.LinkedList;

public interface TeaRoom {

	public VendingMachine machine();
	public LinkedList<? extends Customer> customers();

}
