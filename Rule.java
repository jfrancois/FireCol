/*
 *FireCollaborator
 *Jerome François
 *  
 */

public class Rule {

	private String name;
	private int ruleNumber;

	public Rule(String name) {
		this.name = name;
	}

	public Rule(int i) {
		ruleNumber = i;
		name = "toH"+i;
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



	protected int getRuleNumber() {
		return ruleNumber;
	}

	protected void setRuleNumber(int ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	
	
	
	
}
