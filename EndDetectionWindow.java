/*
 *FireCollaborator
 *Jerome François
 *  
 */

public class EndDetectionWindow extends Event {

	private Node node;

	/**
	 * @return Returns the router.
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param router The router to set.
	 */
	public void setRouter(Node node) {
		this.node = node;
	}

	public EndDetectionWindow(Node node) {
		super(1);
		// TODO Auto-generated constructor stub
		this.node = node;
	}


	
	
}
