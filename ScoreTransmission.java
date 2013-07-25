/*
 *FireCollaborator
 *Jerome François
 *  
 */


import java.util.HashMap;

public class ScoreTransmission extends Event {


	protected Node dest;
	protected HashMap scores;

	public ScoreTransmission(Node dest, HashMap hm) {
		super(2);
		setDest(dest);
		setScores(hm);
	}


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
	 * @return Returns the scores.
	 */
	public HashMap getScores() {
		return scores;
	}




	/**
	 * @param scores The scores to set.
	 */
	public void setScores(HashMap scores) {
		this.scores = scores;
	}

	
}
