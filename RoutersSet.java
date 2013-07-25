/*
 *FireCollaborator
 *Jerome François
 *  
 */


import java.util.ArrayList;


public class RoutersSet {

	private ArrayList routers;
	
	private String name;
	
	public RoutersSet(String n) {
		
		routers = new ArrayList();
		name = n;
	}
	
	public void addRouter(Router r) {
		routers.add(r);
	}
	
	public void removeRouter(Router r) {
		routers.remove(r);
	}
	
	/**
	 * @return the routers
	 */
	public ArrayList getRouters() {
		return routers;
	}

	/**
	 * @param routers the routers to set
	 */
	public void setRouters(ArrayList routers) {
		this.routers = routers;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
