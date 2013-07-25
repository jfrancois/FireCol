/*
 *FireCollaborator
 *Jerome François
 *  
 */

public class PacketTransmission extends Event {

	protected Node dest;
	protected Rule rule;
	protected int size;
	
	
	/**
	 * @return Returns the dest.
	 */
	public Node getDest() {
		return dest;
	}
	/**
	 * @param dest The dest to set.
	 */
	public void setDest(Node dest) {
		this.dest = dest;
	}
	/**
	 * @return Returns the rule.
	 */
	public Rule getRule() {
		return rule;
	}
	/**
	 * @param rule The rule to set.
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public PacketTransmission(Node dest, Rule rule, int size) {
		super(0);
		// TODO Auto-generated constructor stub
		this.dest = dest;
		this.rule = rule;
		this.size = size;
	}
	
	public PacketTransmission(Node dest, Rule rule) {
		super(0);
		// TODO Auto-generated constructor stub
		this.dest = dest;
		this.rule = rule;
		this.size = 0;
	}
	
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
