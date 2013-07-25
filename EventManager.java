/*
 *FireCollaborator
 *Jerome François
 *  
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Vector;

public class EventManager {

	private Vector eventVectors;

	private Vector save;

	private int saveMode = 0;

	private Vector hosts;

	private float cpt=0;

	private ArrayList attackToCheck;
	
	private AnalysisManager analysisManager;

	public EventManager() {
		eventVectors = new Vector();
		save = new Vector();
		hosts = new Vector();
		attackToCheck = new ArrayList();
	}

	public void clearHosts() {
		hosts.clear();
	}

	public void addHost(Host h) {

		hosts.add(h);

	}

	public void addEndWindow(Node n, float time) {

		//	System.out.println(time);
		
		if (getSaveMode() == 0) {
			Vector event = new Vector();
			//WARNING
			event.add(new Float(cpt+time));
			event.add(new EndDetectionWindow(n));
		//	System.out.println("--> "+event.get(0));
			if (eventVectors.size() > 0) {
				int i = 0;
				while(i<eventVectors.size()) {
				
					Vector e = (Vector) eventVectors.get(i);
					if (((Float) e.get(0)).floatValue() >= (cpt+time)) break;
					else i++;

				}
				eventVectors.add(i,event); 
			} else eventVectors.add(event);

		}

	}

	public void addScoreTransmission(HashMap hm, Node n, float time) {

		
		if (getSaveMode() == 0) {
			Vector event = new Vector();
			event.add(new Float(cpt+time));
			event.add(new ScoreTransmission(n, hm));
			if (eventVectors.size() > 0) {
				int i = 0;
				while(i<eventVectors.size()) {
					Vector e = (Vector) eventVectors.get(i);
					if (((Float) e.get(0)).floatValue() >= time) break;
					else i++;

				} 
				eventVectors.add(i,event); 
			}else eventVectors.add(event);

		}
		

	}

	public void addPacketTransmission(Rule r, Node n, float time) {

	
		if (getSaveMode() == 0) {
			Vector event = new Vector();
			event.add(new Float(time));
			event.add(new PacketTransmission(n, r));
			if (eventVectors.size() > 0) {
				int i = 0;
				while(i<eventVectors.size()) {
					Vector e = (Vector) eventVectors.get(i);
					if (((Float) e.get(0)).floatValue() >= time) break;
					else i++;

				}
				eventVectors.add(i,event);
			}else 	eventVectors.add(event);

		}
		
		

	}
	
	
	public void addPacketTransmission(Rule r, Node n, float time, int size) {
		
		if (getSaveMode() == 0) {
			Vector event = new Vector();
	//		System.out.println("---------------- " + time);
			event.add(new Float(time));
			event.add(new PacketTransmission(n, r,size));
			if (eventVectors.size() > 0) {
				int i = 0;
				while(i<eventVectors.size()) {
					Vector e = (Vector) eventVectors.get(i);
					if (((Float) e.get(0)).floatValue() >= time) break;
					else i++;

				}
				eventVectors.add(i,event);
			} else eventVectors.add(event);

		}

	}
	
	public void addPacketTransmission2(Rule r, Node n, float time, int size) {

		if (getSaveMode() == 0) {
			Vector event = new Vector();
	//		System.out.println("---------------- " + time);
			event.add(new Float(cpt+time));
			event.add(new PacketTransmission(n, r,size));
			if (eventVectors.size() > 0) {
				int i = 0;
				while(i<eventVectors.size()) {
					Vector e = (Vector) eventVectors.get(i);
					if (((Float) e.get(0)).floatValue() >= cpt+time) break;
					else i++;

				}
				eventVectors.add(i,event);
			} else eventVectors.add(event);

		}

	}
	
	public void addPacketTransmissionOrder(Rule r, Node n, float time, int size) {
		
		if (getSaveMode() == 0) {
		//	System.out.println("t = "+time);
			Vector event = new Vector();
		//	System.out.println("---------------- " + time);
			event.add(new Float(time));
			event.add(new PacketTransmission(n, r,size));
			//PacketTransmission pt = (PacketTransmission) event.get(1);
			eventVectors.add(event);
			//if (pt.rule.getName().equalsIgnoreCase("131.84.1.31")) {
			//	System.out.println("aa");
			//}
		}

	}
	

	private double cpttemp=0;
	
	public void play1() {
		
		
		
		if (eventVectors.size() > 0) {
			
			Vector event = (Vector) eventVectors.remove(0);
			Event element = (Event) event.get(1);
			cpt = ((Float) event.get(0)).floatValue();
			
			
			if (cpt>cpttemp+5000) {
				System.out.println("Play ----------------- " + cpt);
				cpttemp=cpt;
			}
			if (element.getType() == 0) {
				
				// packet transmission
				PacketTransmission pt = (PacketTransmission) element;
				//if (pt.rule.getName().equalsIgnoreCase("131.84.1.31")) {
				//	System.out.println("aa");
				//}f
				pt.getDest().receivePacket(pt.getRule(),pt.getSize());
			} else if (element.getType() == 2) {
				// score transmission
				ScoreTransmission st = (ScoreTransmission) element;
				st.getDest().receiveScores(st.getScores());
			} else if (element.getType() == 1) {
			//	 end detection window --> 
			//	System.out.println("Play ----------------- " + ((Double) event.get(0)).doubleValue());
				EndDetectionWindow element2 = (EndDetectionWindow) element;
				
				element2.getNode().endWindow();
			}
		}
		
		
	}

	public void play(float time) {
	//	System.out.println("Begin with " + eventVectors.size());
		while(cpt<time) {
			play1();

		}
		
	}

	public void loadReplay() {

		eventVectors = save;
		save = new Vector();
		setSaveMode(1);

	}

	/**
	 * @return Returns the saveMode.
	 */
	public int getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode
	 *            The saveMode to set.
	 */
	public void setSaveMode(int saveMode) {
		this.saveMode = saveMode;
	}

	public void saveEvents(String fileName) {
	    
		
		FileOutputStream fos;
		 ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
		    oos.writeObject(save);
	        oos.flush();
	        oos.close();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public void loadEvents(String fileName) {

        FileInputStream fis;
		try {
			fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
	        eventVectors = (Vector)ois.readObject();
	        ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

		
	
		save = new Vector();
		setSaveMode(1);

	}

	
	public void addAttackAnalysis(Rule rule1, Router r21, int begin, int duration) {
		
		ArrayList list = new ArrayList();
		list.add(0,r21);
		list.add(1,rule1);
		list.add(2,new Integer(begin));
		list.add(3,new Integer(duration));
		attackToCheck.add(list);
		
		
		
	}

	public void attackDetected(Router router, Rule rule) {
		
		//System.out.println("time " + cpt);
		
		if (cpt>=9) {
			
		//analysisManager.addDetection(new Detection(cpt,router,rule));
		System.out.println(cpt + " *** "+router.getName()+" alert: "+rule.getName());
		}
		
		
	/*	for (Iterator iter = attackToCheck.iterator(); iter.hasNext();) {
			ArrayList element = (ArrayList) iter.next();
			
			Router rt = (Router) element.get(0);
			Rule r = (Rule) element.get(1);
			int begin = ((Integer) element.get(2)).intValue();
			int duration = ((Integer) element.get(3)).intValue();
			
			if ((rt == router) & (r == rule)) {
				if((cpt>=begin) & (cpt<=begin+duration-1)  ) {
			
				System.out.println("******************* "+router.getName()+" attack detected: "+rule.getName()+" - "+(cpt-begin));
			} else {
				System.out.println("******************* "+router.getName()+" false positive");
			}
			}
			
		}*/
		
		
	}
	
	public void attackDetected(Host h, Rule r) {
//		System.out.println("################## Real attack: Host "+h.getName());
//		analysisManager.addConfirmedAttack(h,r,cpt);
	}

	/**
	 * @return the analysisManager
	 */
	public AnalysisManager getAnalysisManager() {
		return analysisManager;
	}

	/**
	 * @param analysisManager the analysisManager to set
	 */
	public void setAnalysisManager(AnalysisManager analysisManager) {
		this.analysisManager = analysisManager;
	}
	
	public void clearEvents() {
		
		eventVectors.clear();
		cpt = 0;
		
	}

	/**
	 * @return the eventVectors
	 */
	public Vector getEventVectors() {
		return eventVectors;
	}

	/**
	 * @param eventVectors the eventVectors to set
	 */
	public void setEventVectors(Vector eventVectors) {
		this.eventVectors = eventVectors;
	}

	public void setCpt(int i) {
		cpt = i;
		
	}

	public void attackDetected(Router router, Rule element, float confidence) {
		if (cpt>=9) {
			
			//analysisManager.addDetection(new Detection(cpt,router,rule));
			System.out.println(cpt + "   "+router.getName()+"   "+element.getName()+"   "+confidence);
			}
			
	}
	
	
}
