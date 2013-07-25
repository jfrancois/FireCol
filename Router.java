/*
 *FireCollaborator
 *Jerome François
 *  
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * 
 */

/**
 * @author admin
 *
 */
public class Router extends Node {

	private long nbOps;
	private long nbAccess;

	protected Vector successors;
	protected HashMap scores;
	
	protected HashMap profile;
	protected double alphaProfile;
	protected Vector IPSExchange;
	
	private HashMap table;
	private float entropyThreshold;
	private float gamma;
	private float highEntropy;
	private float highFrequency;
	private float factorCase2;
	private float factorCase3;
	private float scoreDecrease;
	private float thresholdRemoveScore;
	private float scoreThreshold;
	private float thresholdFrequency;
	
	private Vector firstCheckV;
	private Vector firstSelect;
	private Vector secondSelect;
	
	private boolean participate = true;
	
	private static Rule uncertainty = new Rule("uncertainty");
	
	public Router(String name, double alphaProfile, float entropyThreshold, float gamma, int l) {
		super(name);
		// TODO Auto-generated constructor stub
		this.alphaProfile = alphaProfile;
		this.entropyThreshold = entropyThreshold;
		this.gamma = gamma;
		windowLength = l;
		successors = new Vector();
		table = new HashMap();
		scores = new HashMap();
		profile = new HashMap();
		IPSExchange = new Vector();
		evManager.addEndWindow(this,(float) windowLength);
		nbOps = 0;
	}

	public Router(String name, int l) {
		super(name);
		nbOps = 0;
		windowLength = l;
		successors = new Vector();
		table = new HashMap();
		scores = new HashMap();
		profile = new HashMap();
		
		/*Modifications 26/05/2008
		
		evManager.addEndWindow(this,(float) windowLength);
			Fin Modifications 26/05/2008*/
		
		
		IPSExchange = new Vector();
		alphaProfile = 0.5;
		gamma = (float) 0.3;
		entropyThreshold = (float) 0.001;
		highEntropy = (float) 0.2;
		highFrequency = (float) 0.05;//0.4; (0.05 simu2)
		factorCase2 = (float) 0.65;
		factorCase3=(float) 0.8;
		
		scoreDecrease = (float) 0.5;
		thresholdRemoveScore = (float) 0.05;
		scoreThreshold=(float) 0.7; //0.7 (simu2 0,4)
		thresholdFrequency=(float) 0.01;
		
		firstCheckV = new Vector();
		firstSelect = new Vector();
		secondSelect = new Vector();
		
	}
	
	public void addEndTime() {
		evManager.addEndWindow(this,(float) windowLength);
	}
	
	/**
	 * @return Returns the successors.
	 */
	public Vector getSuccessors() {
		return successors;
	}
	
	public void setSuccessors(Vector successors) {
		this.successors = successors;
	}
	
	public void addSuccesor(Node n) {
		successors.add(n);
	}
	
	public void removeSuccesor(Node n) {
		successors.remove(n);
	}
	


	/**
	 * @return Returns the table.
	 */
	
	public HashMap getTable() {
		return table;
	}

	/**
	 * @param table The table to set.
	 */
	public void setTable(HashMap table) {
		this.table = table;
	}
	
	public void addNewRoute(Rule r, Node n, int time) {
		table.put(r,new ArrayList());
		((ArrayList) table.get(r)).add(0,n);
		((ArrayList) table.get(r)).add(1,new Integer(time));
		scores.put(r,new Float(0));
		frequencies.put(r,new Float(0));
		profile.put(r,new Float(0));
	}
	
	public void addNewRoute(Rule r, int time) {
		table.put(r,new ArrayList());
		((ArrayList) table.get(r)).add(0,getSuccessors().get(0));
		((ArrayList) table.get(r)).add(1,new Integer(time));
		scores.put(r,new Float(0));
		frequencies.put(r,new Float(0));
		profile.put(r,new Float(0));
	}
	
	ArrayList getRoutes() {
		
		ArrayList res = new ArrayList();
		
		for (Iterator iter = table.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			res.add(element);
		}
		return res;
	}
	
	Node getRoute(Rule r) {
		return (Node) ((ArrayList) table.get(r)).get(0);
	}
	
	public int getTimeRoute(Node r) {
		
		for (Iterator iter = table.values().iterator(); iter.hasNext();) {
			ArrayList element = (ArrayList) iter.next();
			
			if (element.get(0) == r) {
				return ((Integer) element.get(1)).intValue();
			}
		}
		return 1;
		
	}
	
	public void receivePacket(Rule r) {
		totalCount++;
		frequencies.put(r,new Float(((Float) frequencies.get(r)).intValue()+1)) ;
		
	//	System.out.println("Router "+getName()+" reception: rule "+r.getName());
	/*	ArrayList route = (ArrayList) table.get(r);
		Node next = (Node) route.get(0);
		int time = ((Integer) route.get(1)).intValue(); 
		
		evManager.addPacketTransmission(r,next,time);*/
		
		/* Modif 26/05/08*/
		if (successors.size()>0) {
		Node next = (Node) successors.get(0);
		int time = 1; 
		
		evManager.addPacketTransmission(r,next,time);
		}
		
		/* Fin Modif 26/05/08*/
	}
	
	public void receivePacket(Rule r,int size) {
		//if (r.getName().equalsIgnoreCase("131.84.1.31")) {
		//	System.out.println("aa"+((Float) frequencies.get(r)).intValue());
		//}
		totalCount = totalCount + size;
		frequencies.put(r,new Float(((Float) frequencies.get(r)).intValue()+size)) ;
		
	//	System.out.println("Router "+getName()+" reception: rule "+r.getName());
	//	ArrayList route = (ArrayList) table.get(r);
	//	Node next = (Node) route.get(0);
	//	int time = ((Integer) route.get(1)).intValue(); 
		
		//evManager.addPacketTransmission(r,next,time);
		
		/* Modif 26/05/08*/
		
		if (successors.size()>0) {
			Node next = (Node) successors.get(0);
			int time = 1; 
			
			evManager.addPacketTransmission(r,next,time);
			}
			
		
	
		/* Fin Modif 26/05/08*/
		
	}
	
	
	public void endWindow() {
	//compute frequencies
		if (participate) {
		
		computeFrequencies();

		//displayFrequencies();
		
		
		evManager.addEndWindow(this,(float) windowLength);
		

//		displayFrequencies();
		preUpdateScores();
		
		if (FirstCheck()){
		
			updateScores(rulesSelection());
		detection();
		
		//displayScores();
		
		
		
		//displayProfile();
		
		
		}
		
//		update the profile
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			
			if (((Float) profile.get(element)).floatValue() < 0.00000001) {
				profile.put(element,new Float(((Float) frequencies.get(element)).floatValue()));
			}else {
				nbOps++;nbOps++;nbOps++;
				
				profile.put(element,new Float(((Float) profile.get(element)).floatValue()*alphaProfile + (1-alphaProfile) * ((Float) frequencies.get(element)).floatValue()));
			}
		}
		
		resetFrequencies();
	}
	}
	
	private void detection() {
		HashMap toSend = new HashMap(); 
		float total = 0;
		int cpt = 0;
		
		int count1;
		int count2;
		
		count1 = 0;
		count2 = 0;
		
		for (Iterator iter = scores.keySet().iterator(); iter.hasNext();) {
			
			Rule element = (Rule) iter.next();
//			if (element.getName().equalsIgnoreCase("131.84.1.31")) {
//				System.out.println("* "+scores.get(element)+" * "+frequencies.get(element));
//			}
			if (element!=uncertainty) {
			if (((Float) scores.get(element)).floatValue() < scoreThreshold) {
				
				toSend.put(element,new Float(((Float) scores.get(element)).floatValue()));
				total = total + ((Float) scores.get(element)).floatValue();
				nbOps++;
				count2++;
			} else {
				//System.out.println("Router "+getName()+" - attack to "+element.getName()+" detected");
				count1++;
			
				float maxScore = 1 * scoreDecrease;
				float confidence = (((Float) scores.get(element)).floatValue() - scoreThreshold) / (maxScore);
				evManager.attackDetected(this,element,confidence);
			}
			}
		}
	
		firstSelect.add(new Integer(count1));
		secondSelect.add(new Integer(count2));
		
		/* Modif 26/05/08*/
		
	
		
		if (toSend.size()>0) {
		
			toSend.put(uncertainty,new Float(1-total));
			Vector mem = new Vector();
			
			if (successors.size()>0) {
			Node next = (Node) successors.get(0);
			int time = 1; 
			evManager.addScoreTransmission(toSend,next,time);
			}
			/*
			for (Iterator iter = table.keySet().iterator(); iter.hasNext();) {
				Rule element = (Rule) iter.next();
				if (toSend.containsKey(element)) {
					ArrayList route = (ArrayList) table.get(element);
					Node next = (Node) route.get(0);
					if (! mem.contains(next)) {
					int time = ((Integer) route.get(1)).intValue(); 
					//send the information
					evManager.addScoreTransmission(toSend,next,time);
					mem.add(next);
					cpt++;
					}
					
					
				}
			
			}*/
			
		}
		
		/* Fin Modif 26/05/08*/

		
	}

	private void displayProfile() {
		System.out.println(getName()+" profile:");
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			System.out.println(element.getName()+" - "+profile.get(element));
		}
		
	}
	
	private void displayScores() {
		System.out.println(getName()+" scores:");
	for (Iterator iter = scores.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			System.out.println("score "+element.getName()+" - "+scores.get(element));

		}
	
	}

	private void resetFrequencies() {
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			frequencies.put(element,new Float(0));
		}
		totalCount = 0;
	}

	protected void computeFrequencies() {
		
	if (totalCount!=0) {
	
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			frequencies.put(element,new Float(((Float) frequencies.get(element)).floatValue() / totalCount));
			nbOps++;
		}
	
	}
	}
	
	public void displayFrequencies() {
		System.out.println(getName()+" Frequencies:");
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			System.out.println(element.getName()+" - "+frequencies.get(element));
		}
	}

	/**
	 * @return Returns the alphaProfile.
	 */
	public double getAlphaProfile() {
		return alphaProfile;
	}

	/**
	 * @param alphaProfile The alphaProfile to set.
	 */
	public void setAlphaProfile(double alphaProfile) {
		this.alphaProfile = alphaProfile;
	}
	
	
	public float log(int base, float val) {
		return ((float) (Math.log(val)/Math.log(base)));
	}
	
	public boolean FirstCheck() {
		float entropy=0;

		firstCheckV.add(new Boolean(true));
		//System.out.println(entropyThreshold);
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			
			if (((Float) frequencies.get(element)).floatValue() != 0) {
				
			if (((Float) profile.get(element)).floatValue() !=0) {
				
				entropy = entropy + ((Float) frequencies.get(element)).floatValue() * log(frequencies.keySet().size(),((Float) frequencies.get(element)).floatValue()/(((Float) profile.get(element)).floatValue()));
				
				
				nbOps++;
				nbOps++;
				nbOps++;
				nbOps++;
				
			}
			}
		}
	
	//	System.out.println("entropy = "+entropy);
		if ((entropy > entropyThreshold) || (entropy * -1.0 > entropyThreshold)) {
			return true;
		} else return false;
		
	}

	
	public float entropy() {
		
		float entropy=0;
		
		for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			if (((Float) frequencies.get(element)).floatValue()> 0) {
			entropy = entropy - ((Float) frequencies.get(element)).floatValue() * log(frequencies.keySet().size(),((Float) frequencies.get(element)).floatValue());
			
			
			nbOps++;
			nbOps++;
			nbOps++;
			
			}
		}
	//	System.out.println("entropy -- "+ entropy);
		return entropy;
		
	}
	
	
	public HashMap rulesSelection() {
		HashMap scores = new HashMap();
		float total = 0;
	
		if (entropy() > highEntropy) {
			
			for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
				Rule element = (Rule) iter.next();
				if (((Float) frequencies.get(element)).floatValue()> thresholdFrequency ) {
				if ((((Float) frequencies.get(element)).floatValue() / ((Float) profile.get(element)).floatValue()) > 1 + gamma) {
					
					if (((Float) frequencies.get(element)).floatValue() > highFrequency ) {
						scores.put(element,frequencies.get(element));
						//System.out.println("Selection : "+element.getName());
						total = total + ((Float) frequencies.get(element)).floatValue();
					} else {
						//System.out.println("Selection : "+element.getName());
						scores.put(element,new Float(((Float) frequencies.get(element)).floatValue()*factorCase2));
						nbOps++;
						
						total = total + ((Float) frequencies.get(element)).floatValue()*factorCase2;
					}

				}}
			}
			
		} else {
			//System.out.println("low entropy");
			for (Iterator iter = frequencies.keySet().iterator(); iter.hasNext();) {
				Rule element = (Rule) iter.next();
				if (((Float) frequencies.get(element)).floatValue()> thresholdFrequency ) {
				if ((((Float) frequencies.get(element)).floatValue() / ((Float) profile.get(element)).floatValue()) > 1 + gamma) {
					
					if (((Float) frequencies.get(element)).floatValue() > highFrequency ) {
						//System.out.println("Selection : "+element.getName());
						scores.put(element,new Float(((Float) frequencies.get(element)).floatValue()*factorCase3));
						nbOps++;
						
						total = total + ((Float) frequencies.get(element)).floatValue()*factorCase3;
					}
					
				}
			}}
			
		}
		
		scores.put(uncertainty,new Float(1-total));
		
		return scores;
	}
	
	
	boolean checkVector(Vector v){
		if (v.size() > 1) {
			for(int i=1;i<v.size();i++) {
				if (v.get(i) != v.firstElement()) {
					return true;
				}
			}
		return false;
		}
		return true;
	}
	
	
	float computeK(Vector toIterate,Vector mem) {
		
		float res=0;
		//System.out.println("ici");
		if (toIterate.size() > 0) {
			
			
			HashMap h= (HashMap) toIterate.remove(0);
			
			//iterate over the hashmap
			for (Iterator iter = h.keySet().iterator(); iter.hasNext();) {
				Rule element = (Rule) iter.next();
				
				if (element != uncertainty) {
				
					if (toIterate.size() > 0) {
						
						mem.add(element);
						Vector v =  new Vector();
						v.addAll(toIterate);
						res = res + ((Float) h.get(element)).floatValue() * computeK(v,(Vector) mem.clone());
						
						nbOps++;
						nbOps++;
						
						mem.remove(element);
						
					} else {
						
						if (checkVector(mem)) {
							res = res + ((Float) h.get(element)).floatValue();
							nbOps++;
							
						} else if(element != mem.firstElement()) {
							res = res + ((Float) h.get(element)).floatValue();
							nbOps++;
							
						}
						
						
					}
				
					
				}
				
			}
			
		} 
				
		return res;
		
	}
	
	
	
	
	float computeBelief(Vector toIterate,Rule mem, boolean ok) {
		
		float res=0;
		
		if (toIterate.size() > 0) {
			HashMap h= (HashMap) toIterate.remove(0);
			
			//iterate over the hashmap
			for (Iterator iter = h.keySet().iterator(); iter.hasNext();) {
				Rule element = (Rule) iter.next();
				if (element == mem) {
					
					if (toIterate.size() > 0) {
												res = res + ((Float) h.get(element)).floatValue() * computeBelief((Vector) toIterate.clone(),mem,true);
												nbOps++;
												nbOps++;
												
						
					} else {
						
						res = res + ((Float) h.get(element)).floatValue();
						nbOps++;
					}
				
					
				} else if (element == uncertainty) {
					
					if (toIterate.size() > 0) {
			
						res = res + ((Float) h.get(element)).floatValue() * computeBelief((Vector) toIterate.clone(),mem,ok);
						
						nbOps++;
						nbOps++;
						
						
					} else {
						if (ok) {
							res = res + ((Float) h.get(element)).floatValue();
							
							nbOps++;
							
						}
					}
				
					
				}
				
			}
		} 
		return res;
		
	}
	
	
	

public void preUpdateScores() {
		
		float total = 0;
		Vector toRemove = new Vector();
		//decrease old score
		scores.remove(uncertainty);
		for (Iterator iter = scores.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			//System.out.println(scores.get(element));
			float sc = scoreDecrease*((Float) scores.get(element)).floatValue();
			
			nbOps++;
			
			
			if (sc > thresholdRemoveScore) {
				scores.put(element,new Float(sc));	
				total = total + sc;

				
				
			} else toRemove.add(element);
				
			
		}
		
		for (Iterator iter = toRemove.iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			scores.remove(element);
		}
		
		scores.put(uncertainty,new Float(1 - total));	
	
	}
	
	public void updateScores(HashMap newScores) {
	
	
		Vector rules = new Vector();
		
		for (Iterator iter = scores.keySet().iterator(); iter.hasNext();) {
			
			Rule element = (Rule) iter.next();
			//System.out.println("--scores -- "+element.getName()+" x "+scores.get(element) );
			if (!rules.contains(element)) rules.add(element);
		}
		
		for (Iterator iter = newScores.keySet().iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			//System.out.println("--new scores -- "+element.getName()+" x "+newScores.get(element) );
			//System.out.println(newScores.get(element));
			if (!rules.contains(element)) rules.add(element);
		}
		
		for (Iterator iterator = IPSExchange.iterator(); iterator.hasNext();) {
			HashMap element = (HashMap) iterator.next();
			
			for (Iterator iter = element.keySet().iterator(); iter.hasNext();) {
				Rule element2 = (Rule) iter.next();
				//System.out.println("--scores reçus-- "+element2.getName()+" x "+ element.get(element2));
				if (!rules.contains(element2)) rules.add(element2);
			}
		}
		
		
		IPSExchange.add(scores);
		IPSExchange.add(newScores);
		
		HashMap scores2 = new HashMap();
		
		//compute K of the denominator 1-K
		
		float k = 1 - computeK((Vector) IPSExchange.clone(),new Vector());
		
		nbOps++;
		
		for (Iterator iter = rules.iterator(); iter.hasNext();) {
			Rule element = (Rule) iter.next();
			//System.out.println("************* " + element.getName());
			//System.out.println(" --- "+computeBelief((Vector) IPSExchange.clone(),element,false)/k);
			scores2.put(element, new Float(computeBelief((Vector) IPSExchange.clone(),element,false)/k));
			
			nbOps++;	
			
		}
		
		scores = scores2;
	
		IPSExchange.clear();
	}
	
	
	/**
	 * @return Returns the entropyThreshold.
	 */
	public float getEntropyThreshold() {
		return entropyThreshold;
	}

	/**
	 * @param entropyThreshold The entropyThreshold to set.
	 */
	public void setEntropyThreshold(float entropyThreshold) {
		this.entropyThreshold = entropyThreshold;
	}

	/**
	 * @return Returns the gamma.
	 */
	public float getGamma() {
		return gamma;
	}

	/**
	 * @param gamma The gamma to set.
	 */
	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

	/**
	 * @return Returns the factorCase2.
	 */
	public float getFactorCase2() {
		return factorCase2;
	}

	/**
	 * @param factorCase2 The factorCase2 to set.
	 */
	public void setFactorCase2(float factorCase2) {
		this.factorCase2 = factorCase2;
	}

	/**
	 * @return Returns the factorCase3.
	 */
	public float getFactorCase3() {
		return factorCase3;
	}

	/**
	 * @param factorCase3 The factorCase3 to set.
	 */
	public void setFactorCase3(float factorCase3) {
		this.factorCase3 = factorCase3;
	}

	/**
	 * @return Returns the highEntropy.
	 */
	public float getHighEntropy() {
		return highEntropy;
	}

	/**
	 * @param highEntropy The highEntropy to set.
	 */
	public void setHighEntropy(float highEntropy) {
		this.highEntropy = highEntropy;
	}

	/**
	 * @return Returns the highFrequency.
	 */
	public float getHighFrequency() {
		return highFrequency;
	}

	/**
	 * @param highFrequency The highFrequency to set.
	 */
	public void setHighFrequency(float highFrequency) {
		this.highFrequency = highFrequency;
	}

	/**
	 * @return Returns the scoreDecrease.
	 */
	public float getScoreDecrease() {
		return scoreDecrease;
	}

	/**
	 * @param scoreDecrease The scoreDecrease to set.
	 */
	public void setScoreDecrease(float scoreDecrease) {
		this.scoreDecrease = scoreDecrease;
	}

	/**
	 * @return Returns the thresholdRemoveScore.
	 */
	public float getThresholdRemoveScore() {
		return thresholdRemoveScore;
	}

	/**
	 * @param thresholdRemoveScore The thresholdRemoveScore to set.
	 */
	public void setThresholdRemoveScore(float thresholdRemoveScore) {
		this.thresholdRemoveScore = thresholdRemoveScore;
	}
	
	
	public void receiveScores(HashMap sc) {

		if (participate) {
		
		IPSExchange.add(sc);
		}else {
		
		/*
			
			Vector mem = new Vector();
			
			for (Iterator iter = table.keySet().iterator(); iter.hasNext();) {
				Rule element = (Rule) iter.next();
				if (sc.containsKey(element)) {
					ArrayList route = (ArrayList) table.get(element);
					Node next = (Node) route.get(0);
					if (! mem.contains(next)) {
					int time = ((Integer) route.get(1)).intValue(); 
					//send the information
					evManager.addScoreTransmission(sc,next,time);
					mem.add(next);
					
					}
					
					
				}
			}*/
		}
			
		
		
	}

	/**
	 * @return the firstCheckV
	 */
	public Vector getFirstCheckV() {
		return firstCheckV;
	}

	/**
	 * @param firstCheckV the firstCheckV to set
	 */
	public void setFirstCheckV(Vector firstCheckV) {
		this.firstCheckV = firstCheckV;
	}

	/**
	 * @return the firstSelect
	 */
	public Vector getFirstSelect() {
		return firstSelect;
	}

	/**
	 * @param firstSelect the firstSelect to set
	 */
	public void setFirstSelect(Vector firstSelect) {
		this.firstSelect = firstSelect;
	}

	/**
	 * @return the secondSelect
	 */
	public Vector getSecondSelect() {
		return secondSelect;
	}

	/**
	 * @param secondSelect the secondSelect to set
	 */
	public void setSecondSelect(Vector secondSelect) {
		this.secondSelect = secondSelect;
	}

	/**
	 * @return the scoreThreshold
	 */
	public float getScoreThreshold() {
		return scoreThreshold;
	}

	/**
	 * @param scoreThreshold the scoreThreshold to set
	 */
	public void setScoreThreshold(float scoreThreshold) {
		this.scoreThreshold = scoreThreshold;
	}

	/**
	 * @return the participate
	 */
	public boolean isParticipate() {
		return participate;
	}

	/**
	 * @param participate the participate to set
	 */
	public void setParticipate(boolean participate) {
		this.participate = participate;
	}


	
}
