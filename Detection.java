
/*
 *FireCollaborator
 *Jerome François
 *  
 */


public class Detection {

	private int time;
	private Router router;
	private Rule rule;
	
	
	public Detection(int time, Router router, Rule rule) {
		super();
		this.time = time;
		this.router = router;
		this.rule = rule;

	}
	
	
	/**
	 * @return the router
	 */
	public Router getRouter() {
		return router;
	}
	/**
	 * @param router the router to set
	 */
	public void setRouter(Router router) {
		this.router = router;
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
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}



	
	
}
