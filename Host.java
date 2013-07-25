/*
 *FireCollaborator
 *Jerome François
 *  
 */


public class Host extends Node {

	private int capacity;

	private int packetsCount;
	
	private Rule rule;
	
	
	public Host(String name, int capacity, int l, Rule r) {
		super(name);
		this.capacity = capacity;
		packetsCount = 0;
		windowLength = l; 
	//	evManager.addEndWindow(this,windowLength);
		rule = r;
	}
	
	
	/**
	 * @return Returns the capacity.
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity The capacity to set.
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	

	public void receivePacket(Rule r) {
		//System.out.println("Host "+getName()+" reception: rule "+r.getName());
		packetsCount++;
	}
	
	public void endWindow() {
		/*
		if ((0.0+packetsCount)/windowLength>capacity) {
			System.out.println("Host "+getName()+" is attacked");
			evManager.attackDetected(this,rule);
		}
		packetsCount = 0;
		evManager.addEndWindow(this,windowLength);
		*/
	}
	


	
	
}
