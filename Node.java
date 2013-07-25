/*
 *FireCollaborator
 *Jerome François
 *  
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;


public abstract class Node {
	protected String name;
	protected HashMap frequencies;
	protected int totalCount;	
	protected int windowLength;
	
	static EventManager evManager ;
	
	
	public Node(String name) {
		super();
		frequencies = new HashMap();
		this.name = name;
		totalCount = 0;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}


	public abstract void receivePacket(Rule r);
	/**
	 * @return Returns the evManager.
	 */
	public static EventManager getEvManager() {
		return evManager;
	}
	/**
	 * @param evManager The evManager to set.
	 */
	public static void setEvManager(EventManager evManager) {
		Node.evManager = evManager;
	}

	/**
	 * @return Returns the frequencies.
	 */
	public HashMap getFrequencies() {
		return frequencies;
	}

	/**
	 * @return Returns the totalCount.
	 */
	public int getTotalCount() {
		return totalCount;
	}
	
	
	public abstract void endWindow() ;

	public void receiveScores(HashMap scores) {
		
		
	}

	public Vector getSuccessors() {
		return new Vector();
		
	}

	public int getTimeRoute(Node element) {
		
		return 0;
	}

	public void receivePacket(Rule rule, int size) {
		// TODO Auto-generated method stub
		
	}
	
	
}
