/*
 *FireCollaborator
 *Jerome François
 *  
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class TrafficGenerator {

	protected EventManager manager;
	
	public TrafficGenerator(EventManager em) {
		super();
		manager = em;
	}
	
	public void generate(Router r, int begin, int interval, int steps, double type, int nb) {
		

		Set rules = r.getFrequencies().keySet();
		int nbRules = rules.size();
		for(int i=0;i<steps*interval;i=i+interval) {
			
			//type = -1 --> distribution uniforme
			if (type == -1) {
				
				for(int j=0;j<nb;j++) {
				
					int index = (int) (Math.random() * nbRules);
					manager.addPacketTransmission((Rule) (rules.toArray()[index]), r, begin+i);
					
				}
				
			}
			else {
				
				
				Object[] randomRules = new Object[rules.size()];
				
				for (Iterator iter = rules.iterator(); iter.hasNext();) {
					Rule element = (Rule) iter.next();
					randomRules[element.getRuleNumber()-1] = element;
				}
				

			/*	
				ArrayList copy = new ArrayList(rules);
				int cpt = 0;
				while(!copy.isEmpty()) {
					int index = (int) (Math.random() *copy.size());
					//copy.get(index);
					randomRules[cpt] = copy.remove(index);
					cpt++;
				}*/
				
				
				
			//power law distribution
			//type = b in a*e^-(b*i)
				double[] proba = new double[nbRules];
				double cumul = 0;
				for(int j=0;j<nbRules;j++) {
					cumul=cumul + Math.exp(-((j+1)*type));
					proba[j] = cumul;
				}
				
				
				
				for(int j=0;j<nb;j++) {
					int k =0;
					double gen = Math.random() * cumul;
					while(gen > proba[k]) {	
						k++;
					}
					
					manager.addPacketTransmission((Rule) (randomRules[k]), r, begin+i);
					
				}
				
			}
			
		}
	}
		
	public void generateAttack(Attack a) {
		
		for (Iterator iter = a.getRouters().iterator(); iter.hasNext();) {
			Router element = (Router) iter.next();
			for(int i=0;i<a.getSteps()*a.getInterval();i=i+a.getInterval()) {
			
				for(int j=0;j<a.getNumber();j++) {
					manager.addPacketTransmission(a.getRule(), element,a.getBegin()+i);
				
				}
								
			}
			
		}
		
	}
		
}
	
	

