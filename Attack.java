/*
 *FireCollaborator
 *Jerome François
 *  
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;



public class Attack {

	private ArrayList routers;
	private Rule rule;
	private int begin;
	private int interval;
	private int steps;
	private int number;
	
	private HashMap reachability;
	

	

	public Attack(ArrayList routers, Rule rule, int begin, int intevral, int steps, int number) {
		super();
		this.routers = routers;
		this.rule = rule;
		this.begin = begin;
		this.interval = intevral;
		this.steps = steps;
		this.number = number;
	}

	public void computeReachability() {
		
		reachability = new HashMap();
		
		for (Iterator iter = routers.iterator(); iter.hasNext();) {
			Node element = (Node) iter.next();

			computeReachability(element,0);
			
		}
		
	}
	
	private void computeReachability(Node r,int level) {
		
		reachability.put(r, new Integer(level));
		Vector succ = r.getSuccessors();
	
		for (Iterator iter = succ.iterator(); iter.hasNext();) {
			Node element = (Node) iter.next();
			
			computeReachability(element,level + r.getTimeRoute(element));
		}
		
	}
	
	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * @param begin the begin to set
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}

	/**
	 * @return the intevral
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param intevral the intevral to set
	 */
	public void setInterval(int intevral) {
		this.interval = intevral;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
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
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @param steps the steps to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * @return the reachability
	 */
	public HashMap getReachability() {
		return reachability;
	}

	/**
	 * @param reachability the reachability to set
	 */
	public void setReachability(HashMap reachability) {
		this.reachability = reachability;
	}
	
	
	
	
	
}
