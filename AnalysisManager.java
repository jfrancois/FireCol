/*
 *FireCollaborator
 *Jerome François
 *  
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class AnalysisManager {

	ArrayList possibleAttacks;
	ArrayList attacks;
	ArrayList detections;
	ArrayList falsePositiveStat;
	ArrayList detectionRateStat;
	ArrayList timeDetectionStat;
	ArrayList routersSet;
	
	
	
	public AnalysisManager() {
		super();
		
		attacks = new ArrayList();
		detections = new ArrayList();
		falsePositiveStat = new ArrayList();
		detectionRateStat = new ArrayList();
	
		timeDetectionStat = new ArrayList();
		possibleAttacks = new ArrayList();
		routersSet = new ArrayList();
	}
	
	public void removeSimulation() {
		
		attacks.clear();
		detections.clear();
		possibleAttacks.clear();
	}
	
	public void addDetection(Detection d) {
		detections.add(d);
	}
	
	public void addAttack(Attack a) {
		possibleAttacks.add(a);
	}
	
	public void addRoutersSet(RoutersSet rs) {
		routersSet.add(rs);
	}
	
	public Attack testDetectionAttack(Detection detection) {
		for (Iterator iter = attacks.iterator(); iter.hasNext();) {
			Attack element = (Attack) iter.next();
			
			if (element.getReachability().containsKey(detection.getRouter())) {
				int reach = ((Integer) element.getReachability().get(detection.getRouter())).intValue();
				
				if ((element.getBegin() + reach <= detection.getTime()) & (element.getBegin() + (element.getSteps() - 1)*element.getInterval() + reach >= detection.getTime()) ) {
					return element;
				}
			
			}
			
		}
		return null;
	}
	
	
	/**
	 * @deprecated Use {@link #computeRates(int)} instead
	 */
	public void computeDetectionRateAndFalsePositive(int simuNb) {
		computeRates(simuNb);
	}

	public void computeRates(int simuNb) {
		
		
		HashMap resF = new HashMap();
		HashMap resD = new HashMap();
		HashMap resT = new HashMap();
	
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			
			
			
			RoutersSet element = (RoutersSet) iter.next();
			ArrayList attacksToDetect = new ArrayList();
			ArrayList attacksToDetectCopy = new ArrayList();
			
			for (Iterator Routers = element.getRouters().iterator(); Routers.hasNext();) {
				Router router = (Router) Routers.next();
				for (Iterator iterator = attacks.iterator(); iterator.hasNext();) {
					Attack attack = (Attack) iterator.next();
					
					if ((attack.getReachability().containsKey(router)) & (!attacksToDetect.contains(attack))) {
						attacksToDetect.add(attack);
					}	
				}
			}
					
			attacksToDetectCopy.addAll(attacksToDetect);
			int detected = 0;
			int fp = 0;
			
			HashMap detectedTime = new HashMap();
			
			for (Iterator iterator = detections.iterator(); iterator.hasNext();) {
				Detection detection = (Detection) iterator.next();
				
				if (element.getRouters().contains(detection.getRouter())) {
					Attack a = testDetectionAttack(detection);
					if (a != null) {
						//detected attack
						int delta = detection.getTime() - (a.getBegin() + ((Integer)a.getReachability().get(detection.getRouter())).intValue());		
						
						if(attacksToDetectCopy.contains(a)) {
							//attack not yet seen
							detectedTime.put(a,new Integer(delta));
							detected++;
							
							attacksToDetectCopy.remove(a);
						} else {
							detectedTime.put(a,new Integer( Math.min(((Integer)detectedTime.get(a)).intValue(),delta) ));	
						}
						
					} else {
						//false positive
						fp++;
					}
				}
				
				
			}
			
			//System.out.println("aaa "+detected);
			
			if (attacksToDetect.size()>0) {
				resD.put(element, new Float(detected * 1./attacksToDetect.size()));
			} else {
				
				resD.put(element, new Float(1));
			}
			
			resF.put(element, new Integer(fp));
			
			float avg = 0;
			for (Iterator iterator = detectedTime.values().iterator(); iterator.hasNext();) {
				Integer element2 = (Integer) iterator.next();
				avg = avg + element2.intValue();
			}
			
			if (detectedTime.size() > 0) {
				avg = avg / detectedTime.size();
			}
			resT.put(element, new Float(avg));
			
		}
		
		timeDetectionStat.add(simuNb,resT);
		falsePositiveStat.add(simuNb,resF);
		detectionRateStat.add(simuNb,resD);
		
	}
	


	public void addConfirmedAttack(Host h, Rule r, int cpt) {
		
		boolean trouve = false;
		
		for (Iterator iter = possibleAttacks.iterator(); iter.hasNext();) {
			Attack element = (Attack) iter.next();
			
			if (element.getRule() == r) {
				if ((element.getReachability().containsKey(h)) ) {
				
					if ( (cpt >= element.getBegin()+ ((Integer) element.getReachability().get(h)).intValue()) & (cpt <= element.getBegin()+ element.getInterval() * (element.getSteps() - 1)+((Integer) element.getReachability().get(h)).intValue())) {
					//	System.out.println(cpt+"  "+element.getBegin()+ "  "+element.getBegin()+ element.getInterval() * (element.getSteps() - 1));
						if (!attacks.contains(element)) attacks.add(element);
						trouve = true;
						
					}
				}
			}
			
		}
		if(!trouve) {
		//	System.out.println("Attack not found at time: "+cpt+" for rule: "+r.getName()+" at host:"+h.getName());
		}
		
		
	}
	
	
	public void displayDetectionRate() {
		
		for (int i = 0; i < detectionRateStat.size(); i++) {
			HashMap array_element = (HashMap) detectionRateStat.get(i);
			
			System.out.println("#Simu = " + i);
			
			for (Iterator iter = array_element.keySet().iterator(); iter.hasNext();) {
				RoutersSet element = (RoutersSet) iter.next();
				
				System.out.print(element.getName()+ ": "+((Float) array_element.get(element)).floatValue()+"   ---   ");
				
			}
			System.out.print("\n");
			
		}
		
	}
	
	public void displayFalsePositive() {
		
		for (int i = 0; i < falsePositiveStat.size(); i++) {
			HashMap array_element = (HashMap) falsePositiveStat.get(i);
			
			System.out.println("#Simu = " + i);
			
			for (Iterator iter = array_element.keySet().iterator(); iter.hasNext();) {
				RoutersSet element = (RoutersSet) iter.next();
				
				System.out.print(element.getName()+ ": "+((Integer) array_element.get(element)).intValue()+"   ---   ");
				
			}
			System.out.print("\n");
			
		}
		
	}
	
	public void displayDetectionRate2() {
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			for (int i = 0; i < detectionRateStat.size(); i++) {
				HashMap array_element = (HashMap) detectionRateStat.get(i);
				System.out.print(";"+((Float) array_element.get(element)).floatValue());
					
			}
			System.out.print("\n");
			
				
			}
		
		}
		
	public void displayFalsePositive2() {
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			for (int i = 0; i < falsePositiveStat.size(); i++) {
				HashMap array_element = (HashMap) falsePositiveStat.get(i);
				System.out.print(";"+((Integer) array_element.get(element)).intValue());
					
			}
			System.out.print("\n");
			
				
			}
		
		}
		
	public void displayAverageDetectionRate() {
		
		
		System.out.println("Average detection rate");
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			float avg = 0;
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			for (int i = 0; i < detectionRateStat.size(); i++) {
				HashMap array_element = (HashMap) detectionRateStat.get(i);
				avg = avg + ((Float) array_element.get(element)).floatValue();
					
			}
			avg = avg / detectionRateStat.size();
			System.out.print(": "+avg+"\n");
			
				
			}
		
		}
	
	public void displayAverageFalsePositive() {
		
		
		System.out.println("Average false positive");
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			float avg = 0;
			for (int i = 0; i < falsePositiveStat.size(); i++) {
				HashMap array_element = (HashMap) falsePositiveStat.get(i);
				avg = avg + ((Integer) array_element.get(element)).intValue();
					
			}
			avg = avg / falsePositiveStat.size();
			System.out.print(": "+avg+"\n");
			
				
			}
		
		}
	
	public void displayAverageDetectionTime() {
		
		
		System.out.println("Average detectionTime");
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			float avg = 0;
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			for (int i = 0; i < timeDetectionStat.size(); i++) {
				HashMap array_element = (HashMap) timeDetectionStat.get(i);
				avg = avg + ((Float) array_element.get(element)).floatValue();
					
			}
			avg = avg / timeDetectionStat.size();
			System.out.print(": "+avg+"\n");
			
				
			}
		
		}

	public void addRoutersSet(ArrayList rings) {
		
		for (Iterator iter = rings.iterator(); iter.hasNext();) {
			RoutersSet element = (RoutersSet) iter.next();
			
			addRoutersSet(element);
			
		}
		
	}
	
	public void displayAverageDetectionRate(int averageWindowSize) {
		
		
		System.out.println("Average detection rate");
		int k = 0;
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			float avg = 0;
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			for (int i = 0; i < detectionRateStat.size(); i++) {
				
				HashMap array_element = (HashMap) detectionRateStat.get(i);
				avg = avg + ((Float) array_element.get(element)).floatValue();
				k++;
				if(k>=averageWindowSize) {
					avg = avg /averageWindowSize;
					System.out.print(";"+avg);
					k= 0;
					avg =0;
				}
				
			}
			System.out.print("\n");
			
				
			}
		
		}
	
	
	public void displayAverageFalsePositive(int averageWindowSize) {
		
		
		System.out.println("Average false positive");
		int k =0;
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			float avg = 0;
			for (int i = 0; i < falsePositiveStat.size(); i++) {
				HashMap array_element = (HashMap) falsePositiveStat.get(i);
				avg = avg + ((Integer) array_element.get(element)).intValue();
				k++;
				if(k>=averageWindowSize) {
					avg = avg /averageWindowSize;
					System.out.print(";"+avg);
					k= 0;
					avg =0;
				}
			}
			System.out.print("\n");
			
				
			}
	}
	
	public void displayAverageDetectionTime(int averageWindowSize) {
		
		
		System.out.println("Average detectionTime");
		int k =0;
		
		for (Iterator iter = routersSet.iterator(); iter.hasNext();) {
			float avg = 0;
			RoutersSet element = (RoutersSet) iter.next();
			System.out.print(element.getName());
			for (int i = 0; i < timeDetectionStat.size(); i++) {
				HashMap array_element = (HashMap) timeDetectionStat.get(i);
				avg = avg + ((Float) array_element.get(element)).floatValue();
				k++;
				if(k>=averageWindowSize) {
					avg = avg /averageWindowSize;
					System.out.print(";"+avg);
					k= 0;
					avg =0;
				}
			}
			
			System.out.print("\n");
			
				
			}
		
		}
	
}
