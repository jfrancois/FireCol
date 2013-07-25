/*
 *FireCollaborator
 *Jerome François
 *  
 */


import java.io.Serializable;

public class Event implements Serializable {

	protected int type;

	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}

	public Event(int type) {
		super();
		// TODO Auto-generated constructor stub
		this.type = type;
	}
	
	
	
}
