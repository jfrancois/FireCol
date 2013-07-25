/*
 *FireCollaborator
 *Jerome François
 *  
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class Topology {

	private ArrayList rings;
	private ArrayList hosts;
	private ArrayList rules;
	private EventManager eventManager;
	private RoutersSet allRouters;
	TrafficGenerator generator;
	
	public Topology(EventManager em, TrafficGenerator tg) {
		super();
		rings = new ArrayList();
		rules = new ArrayList();
		hosts = new ArrayList();
		eventManager = em;
		allRouters = new RoutersSet("All routers");
		generator =tg;
	}
	
	
	public void generateHosts(int number,int capacity,int windowLength) {
		
		hosts = new ArrayList(number);
		rules = new ArrayList(number);
		
		for(int i = 1;i<=number;i++) {
			Rule r = new Rule(i);
			Host h = new Host("H"+i,capacity,windowLength,r);
			hosts.add(i-1,h);
			rules.add(i-1,r);
			eventManager.addHost(h);
		}
	}
	
	public void generateRoutersFromFile(int layer1,double d, int numberLayer, int windowLength, String file) {
		
		
		rings = new ArrayList(numberLayer);
		int number = layer1;
			
		InputStream ips;
		try {
			ips = new FileInputStream(file);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
		
		
		String ligne;
		try {
			while ((ligne=br.readLine())!=null){
				String[] sp = ligne.split(",",0);
				System.out.println(ligne);
				
				int i = Integer.parseInt(sp[0]);
				RoutersSet rs = new RoutersSet("Layer " + i);
				rings.add(i-1,rs);
				
				for (int k = 1; k < sp.length; k++) {
					String string = sp[k];
					String[] tab = string.split("-");
					
					int j = Integer.parseInt(tab[0]);
					int inf = Integer.parseInt(tab[1]);
					
					Router r = new Router("R"+i+"."+j,windowLength);
					rs.addRouter(r);
					allRouters.addRouter(r);
					if (i==1) {
					//we connect all the routers to the final hosts
						
						for (Iterator iter = hosts.iterator(); iter.hasNext();) {
							Host element = (Host) iter.next();
							r.addSuccesor(element);
						}
					}
					else{
						
						RoutersSet rsToConnect = (RoutersSet) rings.get(i-2);
						for (Iterator iter = rsToConnect.getRouters().iterator(); iter.hasNext();) {
							Router element = (Router) iter.next();
							if (element.getName().equals("R"+(i-1)+"."+inf)) {
								r.addSuccesor(element);
							}
							
						}
						
					}
					
				}
				
			}
			br.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		

		
		
	}
	
	public void generateRoutersParticipate(int layer1,double d, int numberLayer, int windowLength, double part) {
		
		
		rings = new ArrayList(numberLayer);
		int number = layer1;
		
		for(int i = 1;i<=numberLayer;i++) {
			RoutersSet rs = new RoutersSet("Layer " + i);
			
			rings.add(i-1,rs);
			for(int j=1;j<=number;j++){
				//create a router
				Router r = new Router("R"+i+"."+j,windowLength);
				if(Math.random()>part) {
					r.setParticipate(false);
				}
				rs.addRouter(r);
				allRouters.addRouter(r);
				if (i==1) {
				//we connect all the routers to the final hosts
					
					for (Iterator iter = hosts.iterator(); iter.hasNext();) {
						Host element = (Host) iter.next();
						r.addSuccesor(element);
					}
				}else {
				//we connect to the routers of the layer i-1 with a probability 1/i
					RoutersSet rsToConnect = (RoutersSet) rings.get(i-2);
					boolean connexion = false;
					for (Iterator iter = rsToConnect.getRouters().iterator(); iter.hasNext();) {
						Router element = (Router) iter.next();
						if (Math.random()<= 1.0/i) {
							r.addSuccesor(element);
							connexion = true;
						}
						
					}
					if(!connexion) {
						r.addSuccesor((Router) rsToConnect.getRouters().get(0));
					}
					
				}
				
			}
			number = Math.max(((int) (number * d)),number +1);
			
		}
		
	}
	
	
	
	public void generateRouters(int layer1,double d, int numberLayer, int windowLength) {
		
		rings = new ArrayList(numberLayer);
		int number = layer1;
		
		for(int i = 1;i<=numberLayer;i++) {
			RoutersSet rs = new RoutersSet("Layer " + i);
			rings.add(i-1,rs);
			for(int j=1;j<=number;j++){
				//create a router
				Router r = new Router("R"+i+"."+j,windowLength);
				rs.addRouter(r);
				allRouters.addRouter(r);
				if (i==1) {
				//we connect all the routers to the final hosts
					
					for (Iterator iter = hosts.iterator(); iter.hasNext();) {
						Host element = (Host) iter.next();
						r.addSuccesor(element);
					}
				}else {
				//we connect to the routers of the layer i-1 with a probability 1/i
					RoutersSet rsToConnect = (RoutersSet) rings.get(i-2);
					
					/* Modif 26/05/08*/
					/*boolean connexion = false;
					for (Iterator iter = rsToConnect.getRouters().iterator(); iter.hasNext();) {
						Router element = (Router) iter.next();
						if (Math.random()<= 1.0/i) {
							r.addSuccesor(element);
							connexion = true;
						}
						
					}
					*/
					
					
					
					Random randG = new Random();
					int rand = randG.nextInt(rsToConnect.getRouters().size());
					r.addSuccesor((Router) rsToConnect.getRouters().get(rand));
					/*if(!connexion) {
						r.addSuccesor((Router) rsToConnect.getRouters().get(0));
					}*/
					
					
					/* Fin Modif 26/05/08*/
					
				}
				
			}
			number = Math.max(((int) (number * d)),number +1);
			
		}
		
	}
	
	public void computeRoutes() {
		
		for(int i = 0; i<rings.size();i++) {
			
			for (Iterator iter = ((RoutersSet)rings.get(i)).getRouters().iterator(); iter.hasNext();) {
				Router element = (Router) iter.next();
				
		
			
			if(i == 0) {
				//premier anneau, une route pour chaque hote
				for (int j=0;j<hosts.size();j++) {
					//time is 1 by default
					element.addNewRoute((Rule) rules.get(j),(Host) hosts.get(j), 1);
				}
			}
			else {
				
				HashMap possible = new HashMap();
				
				
				for (Iterator iterator = element.getSuccessors().iterator(); iterator.hasNext();) {
					Router element2 = (Router) iterator.next();
					
					
					for (Iterator iterator2 = element2.getRoutes().iterator(); iterator2.hasNext();) {
						
						Rule rule = (Rule) iterator2.next();
						ArrayList a;
						if (possible.containsKey(rule)) {
							a = (ArrayList) possible.get(rule);
						} else {
							a = new ArrayList();
							possible.put(rule,a);
						}
						a.add(element2);
						//element.addNewRoute(rule, element2, 1);
					}
					
					
				}
				
				for (Iterator iterator = possible.keySet().iterator(); iterator.hasNext();) {
					Rule element2 = (Rule) iterator.next();
					ArrayList a = (ArrayList) possible.get(element2);
					int index = (int) (Math.random() * a.size());
					element.addNewRoute(element2, (Node) a.get(index), 1);
					
				}
				
				
				
				
			}
			
			}
			
		}
		
	}
	
	public Attack createAttack(int ringNumber, int numberRouters, int indexRule, int begin, int interval, int steps, int numberPackets) {
		
		RoutersSet rs =(RoutersSet) rings.get(ringNumber-1);
		ArrayList attackRouters = new ArrayList(rs.getRouters());
		
		//CHoose some routers
		while(attackRouters.size()>numberRouters) {
			int index = (int) (Math.random() * attackRouters.size());
			attackRouters.remove(index);
		}
		return new Attack(attackRouters, (Rule) rules.get(indexRule-1), begin, interval,steps,numberPackets);
		
	}
	
	public void generateTraffic(int ringNumber,int numberRouters, int begin, int interval, int steps, double type, int nb) {
		
		RoutersSet rs =(RoutersSet) rings.get(ringNumber-1);
		ArrayList routers = new ArrayList(rs.getRouters());
		
		//CHoose some routers
		while(routers.size()>numberRouters) {
			int index = (int) (Math.random() * routers.size());
			routers.remove(index);
		}
		
		for (Iterator iter = routers.iterator(); iter.hasNext();) {
			Router element = (Router) iter.next();
			generator.generate(element, begin, interval, steps, type, nb);
			
		}
		
	}
	
	public void generateTraffic2(int ringNumber,double proportionRouters, int begin, int interval, int steps, double type, int nb) {
		
		generateTraffic(ringNumber, Math.max(1, ((int) proportionRouters*((RoutersSet) rings.get(ringNumber-1)).getRouters().size())), begin, interval, steps, type, nb);
		
	}
	
	
	
	public void displayTopology() {
		
		for (int i = rings.size()-1; i >= 0; i--) {
			RoutersSet rs = (RoutersSet) rings.get(i);
			
			System.out.println("\n************************");
			System.out.print(rs.getName()+":\n");
			
			for (Iterator iter = rs.getRouters().iterator(); iter.hasNext();) {
				Router element = (Router) iter.next();
				
				System.out.print(element.getName()+" connected to: ");
				
				for (Iterator iterator = element.getSuccessors().iterator(); iterator.hasNext();) {
					Node element2 = (Node) iterator.next();
					System.out.print(element2.getName()+", ");
				}
				System.out.print("\n");
				
				System.out.print(element.getName()+" with route to: ");
				
				for (Iterator iterator = element.getRoutes().iterator(); iterator.hasNext();) {
					Rule element2 = (Rule) iterator.next();
					System.out.print(element2.getName()+"-"+element.getRoute(element2).getName()+", ");
					
				}
				System.out.print("\n");
				
			}
			
			
		}
		
		System.out.println("************************");
		System.out.println("Hosts");
		for (Iterator iter = hosts.iterator(); iter.hasNext();) {
			Host element = (Host) iter.next();
			System.out.println(element.getName());
		}
		
	}


	/**
	 * @return the allRouters
	 */
	public RoutersSet getAllRouters() {
		return allRouters;
	}


	/**
	 * @param allRouters the allRouters to set
	 */
	public void setAllRouters(RoutersSet allRouters) {
		this.allRouters = allRouters;
	}


	/**
	 * @return the rings
	 */
	public ArrayList getRings() {
		return rings;
	}


	/**
	 * @param rings the rings to set
	 */
	public void setRings(ArrayList rings) {
		this.rings = rings;
	}



	
	
	
}
