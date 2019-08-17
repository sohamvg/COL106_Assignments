README - Assignment 2 
Soham Gaikwad , 2018cs10394

Each method defined in the classes complies. No work is remaining in my opinion.
===============================================================================

Implementations:

1) Abstract class NodeBase<V> // generic class
	Variables:-
		a) protected int priority          // Stores the priority of the node(Item)
		b) protected V value               // Stores the info about the item
	Methods:-
		a)     public abstract int getPriority(); // return the priority of the node
		b)     public abstract V getValue();	// returns the value stored by the node
    		c)     public void show()                // prints the objects'priority
    		
    		
2) interface  QueueInterace<V>      // generic
	Methods:-
		a) public int size() // Returns the size, i.e. the number of elements in the queue
   		b) public boolean isEmpty() // Returns true if the queue is empty, else returns false
  		c) public boolean isFull()  // Returns true if the queue is full, else returns false
  		d) public void enqueue(Node<V> item) // Adds an item to the rear of the queue
   		e) public NodeBase<V> dequeue() // Removes an item from the front of the queue   
   		
3) Class BuyerBase<V> implements Runnable
	Variables:-
		a) protected PriorityQueue<V> catalog; // The shared priority queue
   		b) protected Lock lock; // Shared lock
    		c) protected Condition full, empty; // Shared condition variables
    		d) private int sleepTime; // Sleep duration (in ms) for current thread
   		e) private int iteration; // No. of iterations for buyer threads
   	Methods:-
   		a) public void buy()   // buys elements from the Priority Queue
   		b) public void run()   // overrides run() of Runnable interface. Buys objects a specific number of times
   		c) public void setSleepTime()  //sets value for private variable sleeptime
   		d) public void setIteration()  //sets the value for private variable iteration
   		
4) Class SellerBase<V> implements Runnable  //generic
	Variables:
		a)protected PriorityQueue<V> catalog; // Shared priority queue
  		b) protected Lock lock; //Shared lock
    		c) protected Condition full, empty; // Shared condition variables
    		d) private int sleepTime; // Sleep duration (in ms) for current thread
    		e) protected Queue<V> inventory; // List of items (shared between sellers)   		
   	Methods:	  	 		
   		a) public void sell()   // sells elements to the Priority Queue from inventory
   		b) public void run()   // overrides run() of Runnable interface. Sells objects a specific number of times
   		c) public void setSleepTime()  //sets value for private variable sleeptime
   		d) public void setIteration()  //sets the value for private variable iteration

5) Class Node extends NodeBase<V> ;
	Carries all functions from 1.		

6) Class Queue implements QueueInterface<V>
	Carries all functions from 2.
	Variables:
		a) int front    // Stores the value of front index of circular queue
		b) int rear     // Stores the value of rear index of circular queue
		c) NodeBase<V> queue // The array whoch stores the nodes
		d) currentSize  // Stores the value of size of queue

	implementation is such that queue can be enqueued as long as it is not full (i.e. array capacity is not filled completely). Therefore I take modulus with queue capacity while incrementing the iterators.
		
7) Class PriorityQueue implements QueueInterface<V> :
	Carries all functions from 2
	and
	public void display()  // Shows the values in the priority queue
	
	Variables:-
		a)NodeBase<V> queue  // Stores objects of type node
		b) int currentSize  // Stores the number of elements in queue
		c) int capacity   // Stores the maximum size of queue

	enqueue- sorted while insertng new node. starts from rear and goes till node priority < queue[i].priority.
	implementation is such that queue can be enqueued as long as it is not full (i.e. array capacity is not filled completely). Therefore I take modulus with queue capacity while incrementing the iterators.
		
8)Class Buyer extends BuyerBase<>V   //has same functions and variables
	public void run() // used from baseclass using super method
			  // same for setIteration
			  // same for setSellerSleeptime
 
9) Class Seller extends SellerBase<V>	//has same functions and variables
	public void run() // used from baseclass using super method
			  // same for setSellerSleeptime

10)public class Assignment2Driver
	Variables:
		a) public int catalogSize;   //maximum size of queue
    		b) public int numBuyers;	// number of buyers
    		c) public int numSellers;	// number of sellers
    		d) public int sellerSleepTime, buyerSleepTime;	
    		e) public Queue<Item> inventory; // carries all objects to be sold
    	Methods:
    		a) static void main()        // Takes in input for inventory
    					     // Initialises the number of iteratios
    					     // Creates and Initiates buyer and seller threads					
						
