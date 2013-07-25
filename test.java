/*
 *FireCollaborator
 *Jerome François
 *  
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EventManager em = new EventManager();
		Node.setEvManager(em);

                //network5(em);
		
	//	System.out.println("\n\nTest 80\n\n");
		//test80();
		//System.out.print("\n\n");
		System.out.println("\n\nTest 10\n\n");
		test10();
		//System.out.print("\n\n");
		//System.out.println("\n\nTest 82\n\n");
		
                
                //testData(args[0]);
		//System.out.print("\n\n");
		
                //System.out.println("\n\nTest 83\n\n");
		//test83();
		//System.out.print("\n\n");
		
		//test40();
		
	}


protected static void network4(EventManager em) {
	
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 12, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 3;
	int numberRouters = 2;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =20;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(12, 4, 1, 1, 100, 0.3, 70);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}


protected static void network5(EventManager em) {
	
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 3, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber =3;
	int numberRouters = 2;
	int rule =3;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =40;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	
	
	
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(3, 4, 1, 1, 100, 0.3, 58);


	em.play(100);
	

//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}





protected static void test10() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 3, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 3;
	int numberRouters = 2;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =8;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(3, 4, 1, 1, 100, 0.3, 58);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}




protected static void test11() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 4, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =5;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 38);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test12() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =4;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}




protected static void test13() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 8, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 8;
	int numberRouters = 16;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =1;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(8, 28, 1, 1, 100, 0.3, 8);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test14() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 2, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 2;
	int numberRouters = 1;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =16;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(2, 3, 1, 1, 100, 0.3, 77);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test15() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 1, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 1;
	int numberRouters = 1;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =16;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(1, 2, 1, 1, 100, 0.3, 116);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}


protected static void test20() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 3, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 3;
	int numberRouters = 2;
	int rule =3;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =40;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(3, 4, 1, 1, 100, 0.3, 58);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}




protected static void test21() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 4, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =3;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =27;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 38);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test22() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =3;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =20;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}




protected static void test23() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 8, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 8;
	int numberRouters = 14;
	int rule =3;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =6;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<225;i++) {
	
	float var = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(8, 28, 1, 1, 100, 0.3, 8);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test30() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 3, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	
	int numberPackets0 =8;
	for(int i=0;i<250;i++) {
	
	float var = 0.6f;	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	int ringNumber = 3;
	int numberRouters = 2;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = numberPackets0 * (1 + ((int) (i/25)));
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(3, 4, 1, 1, 100, 0.3, 58);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}




protected static void test31() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 4, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	
	int numberPackets0 =5;
	for(int i=0;i<250;i++) {
	
	float var = 0.7f;	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = numberPackets0 * (1 + ((int) (i/25)));
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 38);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	


	

}



protected static void test32() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	
	int numberPackets0 = 4;
	for(int i=0;i<250;i++) {
	
	float var = 0.8f;	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = numberPackets0 * (1 + ((int) (i/25)));
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

	

}




protected static void test33() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 8, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	
	int numberPackets0 =1;
	for(int i=0;i<250;i++) {
	
	float var = 0.9f;	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	int ringNumber = 8;
	int numberRouters = 16;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = numberPackets0 * (1 + ((int) (i/25)));
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(8, 28, 1, 1, 100, 0.3, 8);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

	
	

}






protected static void test40() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	
	
	
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = 4;
	
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	int ringNumber2 = 5;
	int numberRouters2 = 4;
	int rule2 =3;
	int begin2 = 50;
	int interval2 = 1;
	int steps2=10;
	int numberPackets2 =20;
	Attack a2 = topo.createAttack(ringNumber2, numberRouters2, rule2, begin2, interval2, steps2, numberPackets2);
	a2.computeReachability();
	

	int ringNumber3 = 5;
	int numberRouters3 = 4;
	int rule3 =3;
	int begin3 = 40;
	int interval3 = 1;
	int steps3=10;
	int numberPackets3 =20;
	Attack a3 = topo.createAttack(ringNumber3, numberRouters3, rule3, begin3, interval3, steps3, numberPackets3);
	a3.computeReachability();
	
	int ringNumber4 = 5;
	int numberRouters4 = 4;
	int rule4 =1;
	int begin4 = 60;
	int interval4 = 1;
	int steps4=10;
	int numberPackets4 = 4;
	
	Attack a4 = topo.createAttack(ringNumber4, numberRouters4, rule4, begin4, interval4, steps4, numberPackets4);
	a4.computeReachability();
	
	
	for(int i=0;i<250;i++) {
	
	float var = 0.7f;	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	
	
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	am.addAttack(a2);
	am.addAttack(a3);
	am.addAttack(a4);
	generator.generateAttack(a);
	generator.generateAttack(a2);
	generator.generateAttack(a3);
	generator.generateAttack(a4);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate();
	System.out.print("\n");
	am.displayAverageFalsePositive();
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime();
	
	
	

	

}





protected static void test50() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	
	
	
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = 4;
	
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	int ringNumber2 = 5;
	int numberRouters2 = 4;
	int rule2 =3;
	int begin2 = 50;
	int interval2 = 1;
	int steps2=10;
	int numberPackets2 =20;
	Attack a2 = topo.createAttack(ringNumber2, numberRouters2, rule2, begin2, interval2, steps2, numberPackets2);
	a2.computeReachability();
	

	int ringNumber3 = 5;
	int numberRouters3 = 4;
	int rule3 =3;
	int begin3 = 40;
	int interval3 = 1;
	int steps3=10;
	int numberPackets3 =20;
	Attack a3 = topo.createAttack(ringNumber3, numberRouters3, rule3, begin3, interval3, steps3, numberPackets3);
	a3.computeReachability();
	
	int ringNumber4 = 5;
	int numberRouters4 = 4;
	int rule4 =1;
	int begin4 = 60;
	int interval4 = 1;
	int steps4=10;
	int numberPackets4 = 4;
	
	Attack a4 = topo.createAttack(ringNumber4, numberRouters4, rule4, begin4, interval4, steps4, numberPackets4);
	a4.computeReachability();
	
	float[] paramVar = {0.6f,0.7f,0.8f,0.9f};
	
	
	
	
	for(int i=0;i<200;i++) {
	
	float var = 0.7f;	
	float var2 = paramVar[(int) i/50];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setHighEntropy(var2);
	}
	
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	am.addAttack(a2);
	am.addAttack(a3);
	am.addAttack(a4);
	generator.generateAttack(a);
	generator.generateAttack(a2);
	generator.generateAttack(a3);
	generator.generateAttack(a4);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(50);
	System.out.print("\n");
	am.displayAverageFalsePositive(50);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(50);
	
	
	

	

}





protected static void test51() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	
	
	
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = 4;
	
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	int ringNumber2 = 5;
	int numberRouters2 = 4;
	int rule2 =3;
	int begin2 = 50;
	int interval2 = 1;
	int steps2=10;
	int numberPackets2 =20;
	Attack a2 = topo.createAttack(ringNumber2, numberRouters2, rule2, begin2, interval2, steps2, numberPackets2);
	a2.computeReachability();
	

	int ringNumber3 = 5;
	int numberRouters3 = 4;
	int rule3 =3;
	int begin3 = 40;
	int interval3 = 1;
	int steps3=10;
	int numberPackets3 =20;
	Attack a3 = topo.createAttack(ringNumber3, numberRouters3, rule3, begin3, interval3, steps3, numberPackets3);
	a3.computeReachability();
	
	int ringNumber4 = 5;
	int numberRouters4 = 4;
	int rule4 =1;
	int begin4 = 60;
	int interval4 = 1;
	int steps4=10;
	int numberPackets4 = 4;
	
	Attack a4 = topo.createAttack(ringNumber4, numberRouters4, rule4, begin4, interval4, steps4, numberPackets4);
	a4.computeReachability();
	
	float[] paramVar = {0.2f,0.3f,0.4f,0.5f};
	
	
	
	
	for(int i=0;i<200;i++) {
	
	float var = 0.7f;	
	float var2 = paramVar[(int) i/50];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setGamma(var2);
	}
	
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	am.addAttack(a2);
	am.addAttack(a3);
	am.addAttack(a4);
	generator.generateAttack(a);
	generator.generateAttack(a2);
	generator.generateAttack(a3);
	generator.generateAttack(a4);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(50);
	System.out.print("\n");
	am.displayAverageFalsePositive(50);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(50);
	
	
	

	

}




protected static void test52() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	
	
	
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets = 4;
	
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	int ringNumber2 = 5;
	int numberRouters2 = 4;
	int rule2 =3;
	int begin2 = 50;
	int interval2 = 1;
	int steps2=10;
	int numberPackets2 =20;
	Attack a2 = topo.createAttack(ringNumber2, numberRouters2, rule2, begin2, interval2, steps2, numberPackets2);
	a2.computeReachability();
	

	int ringNumber3 = 5;
	int numberRouters3 = 4;
	int rule3 =3;
	int begin3 = 40;
	int interval3 = 1;
	int steps3=10;
	int numberPackets3 =20;
	Attack a3 = topo.createAttack(ringNumber3, numberRouters3, rule3, begin3, interval3, steps3, numberPackets3);
	a3.computeReachability();
	
	int ringNumber4 = 5;
	int numberRouters4 = 4;
	int rule4 =1;
	int begin4 = 60;
	int interval4 = 1;
	int steps4=10;
	int numberPackets4 = 4;
	
	Attack a4 = topo.createAttack(ringNumber4, numberRouters4, rule4, begin4, interval4, steps4, numberPackets4);
	a4.computeReachability();
	
	float[] paramVar = {0.01f,0.025f,0.05f,0.1f};
	
	
	
	
	for(int i=0;i<200;i++) {
	
	float var = 0.7f;	
	float var2 = paramVar[(int) i/50];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setEntropyThreshold(var2);
	}
	
	

	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	am.addAttack(a2);
	am.addAttack(a3);
	am.addAttack(a4);
	generator.generateAttack(a);
	generator.generateAttack(a2);
	generator.generateAttack(a3);
	generator.generateAttack(a4);
	topo.generateTraffic(5, 9, 1, 1, 100, 0.3, 25);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(50);
	System.out.print("\n");
	am.displayAverageFalsePositive(50);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(50);
	
	
	

	

}



protected static void test70() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 4, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =4;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	int ringNumber2 = 3;
	int numberRouters2 = 2;
	int rule2 =1;
	int begin2 = 51;
	int interval2 = 1;
	int steps2=10;
	int numberPackets2 =1;
	Attack a2 = topo.createAttack(ringNumber2, numberRouters2, rule2, begin2, interval2, steps2, numberPackets2);
	a2.computeReachability();
	
	int ringNumber3 = 2;
	int numberRouters3 = 1;
	int rule3 =1;
	int begin3 = 52;
	int interval3 = 1;
	int steps3=10;
	int numberPackets3 =2;
	Attack a3 = topo.createAttack(ringNumber3, numberRouters3, rule3, begin3, interval3, steps3, numberPackets3);
	a3.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	//float[] paramVar = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
	
	for(int i=0;i<250;i++) {
	
	float var = 0.6f;	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	am.addAttack(a2);
	generator.generateAttack(a2);
	am.addAttack(a3);
	generator.generateAttack(a3);
	topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 30);
	topo.generateTraffic(3, 2, 1, 1, 100, 0.3, 15);
	topo.generateTraffic(2, 1, 1, 1, 100, 0.3, 15);

	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate();
	System.out.print("\n");
	am.displayAverageFalsePositive();
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime();
	
	
	

}

	


protected static void test71() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRoutersParticipate(2,1.5, 4, 1,0.6);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =5;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float var = 0.6f;	
	
	for(int i=0;i<250;i++) {
		
	
		
		for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
			Router element = (Router) iter.next();
			element.setScoreThreshold(var);
		}
		
		//System.out.println("$$$ Simulation #"+i+" $$$");
		em.setEventVectors(saveBegin);
		em.setCpt(0);
		am.removeSimulation();
		am.addAttack(a);
		generator.generateAttack(a);
		topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 38);
	

		em.play(100);
		am.computeRates(i);
		
		}


//		am.displayAverageDetectionRate();
//		System.out.print("\n");
		
		am.displayAverageDetectionRate();
		System.out.print("\n");
		am.displayAverageFalsePositive();
		System.out.print("\n");
		
//	am.displayAverageFalsePositive();
//		System.out.print("\n");
		am.displayAverageDetectionTime();
	
	
	

}





protected static void test80() {
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 3, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 3;
	int numberRouters = 2;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =8;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());

	float var = 0.4f;	

float[] paramVar = {0.05f,0.1f,0.15f,0.2f,0.25f,0.3f,0.35f,0.4f,0.45f,0.5f,0.55f,0.6f};
int[] paramVar2 = {53,54,55,56,57,58,59,60,61,62,63,64};
	for(int i=0;i<300;i++) {
	
	float var2 = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
//	System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(3, 4, 1, 1, 100,var2,58);

	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test81() {
	
	
	
	
	
	

	
	
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRoutersParticipate(2,1.5, 4, 1,1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =5;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());
	

	float var = 0.6f;	
	
	int[] paramVar2 = {33,34,35,36,37,38,39,40,41,42,43,44};
	float[] paramVar = {0.05f,0.1f,0.15f,0.2f,0.25f,0.3f,0.35f,0.4f,0.45f,0.5f,0.55f,0.6f};
	
	for(int i=0;i<300;i++) {
	
	float var2 = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
//	System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, var2, 38);
	


	em.play(100);
	am.computeRates(i);
	
	}
	
	
	
	//		am.displayAverageDetectionRate();
//		System.out.print("\n");
		
		am.displayAverageDetectionRate(25);
		System.out.print("\n");
		am.displayAverageFalsePositive(25);
		System.out.print("\n");
		
//	am.displayAverageFalsePositive();
//		System.out.print("\n");
		am.displayAverageDetectionTime(25);
	
	
	

}









protected static void test82() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 5, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 5;
	int numberRouters = 4;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =4;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());

	float var = 0.7f;	
	int[] paramVar2 = {18,19,20,21,23,25,27,33,42,50,60,72};
float[] paramVar = {0.05f,0.1f,0.15f,0.2f,0.25f,0.3f,0.35f,0.4f,0.45f,0.5f,0.55f,0.6f};
	
	for(int i=0;i<300;i++) {
	
	float var2 = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	//System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	
	topo.generateTraffic(5, 9, 1, 1, 100, var2,25);

	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);


}




protected static void test83() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 8, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	int ringNumber = 8;
	int numberRouters = 16;
	int rule =1;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =1;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());

	float var = 0.6f;	
	int[] paramVar2 = {33,34,35,36,37,38,39,40,41,42,43,44};
float[] paramVar = {0.05f,0.1f,0.15f,0.2f,0.25f,0.3f,0.35f,0.4f,0.45f,0.5f,0.55f,0.6f};
	
	for(int i=0;i<300;i++) {
	
	float var2 = paramVar[(int) i/25];	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, var2, 38);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void test90() {
	
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	topo.generateHosts(5, 100, 1);
	topo.generateRouters(2,1.5, 8, 1);
	topo.computeRoutes();
	topo.displayTopology();
	
	

	
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());

	float var = 0.7f;	

	int[] paramVar = {1,2,3,4,5};
	
	for(int i=0;i<250;i++) {
	
	int var2 = paramVar[(int) i/50];	
	
	int ringNumber = 4;
	int numberRouters = 3;
	int rule =var2;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =5;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 38);


	em.play(100);
	am.computeRates(i);
	
	}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	
	
	

}



protected static void testData(String file) {
	
	HashMap nodes = new HashMap();
	HashMap rules = new HashMap();
	
	
	HashMap affect = new HashMap();
	
	EventManager em = new EventManager();
	Node.setEvManager(em);
	
	TrafficGenerator generator = new TrafficGenerator(em);
	Topology topo = new Topology(em,generator);
	
	
	//topo.generateRouters(1,1.5, 3, 60);
	topo.generateRoutersFromFile(2,1.5, 5, 1,"host_file");
	
	topo.displayTopology();
	
//	Router myIPS = (Router) topo.getAllRouters().getRouters().get(0);

	FileInputStream nomLogique;
float maxi=0;
	try {
		nomLogique = new 
FileInputStream(file);
		InputStreamReader ligne = new InputStreamReader (nomLogique);
		BufferedReader bufferLigne = new BufferedReader (ligne);
	
	
	String read;
	float timeBegin = 0;
	
	read=bufferLigne.readLine();
		while ((read=bufferLigne.readLine())!=null){
			read = " "+read;
			Rule r;
			Host h;
			
			String[] separation = read.split("\\s+");
			
		//	System.out.println("aa "+separation[2]);
		
			if (separation.length > 1) {
			float time = Float.parseFloat(separation[2]);
			String IPaddr = separation[4];
			String source = separation[3];
			//System.out.println("aa "+time);
			int size = 1;//Integer.parseInt(separation[6]);
			
			//first packet
			if (timeBegin == 0) {
				timeBegin = time;
			}
			
			if (nodes.containsKey(IPaddr)) {
				h = (Host) nodes.get(IPaddr);
				r = (Rule) rules.get(IPaddr);
			} else {
				r = new Rule(IPaddr);
				h = new Host(IPaddr,10,1,r);
				nodes.put(IPaddr,h);
				rules.put(IPaddr, r);
				/*modification 26/05/08*/	
				for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
					Router element = (Router) iter.next();
					element.addNewRoute(r,h,1);
					
				}
				
				
				
				//myIPS.addNewRoute2(r, 1);
				/*fin modification 26/05/08*/
			}
			
			float t = time-timeBegin;
			//System.out.println("aa "+t);
			maxi = t;
			//System.out.println("t = "+t);
			
			if (affect.containsKey(source)) {
				em.addPacketTransmissionOrder(r, (Node) affect.get(source), t,size);
			} else {
				Random rg = new Random();
				int rand = rg.nextInt(topo.getAllRouters().getRouters().size());
				em.addPacketTransmissionOrder(r, (Node) topo.getAllRouters().getRouters().get(rand), t,size);
				affect.put(source,topo.getAllRouters().getRouters().get(rand));
			}
			
		
			
			}
		}
		
	bufferLigne.close(); 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.addEndTime();
		
	}
	/*
	AnalysisManager am =new AnalysisManager();
	em.setAnalysisManager(am);
	
	
	Vector saveBegin = em.getEventVectors();
	
	am.addRoutersSet(topo.getAllRouters());
	am.addRoutersSet(topo.getRings());

	float var = 0.7f;	

	int[] paramVar = {1,2,3,4,5};
	
	for(int i=0;i<250;i++) {
	
	int var2 = paramVar[(int) i/50];	

	int ringNumber = 4;
	int numberRouters = 3;
	int rule =var2;
	int begin = 50;
	int interval = 1;
	int steps=10;
	int numberPackets =5;
	Attack a = topo.createAttack(ringNumber, numberRouters, rule, begin, interval, steps, numberPackets);
	a.computeReachability();
	
	
	for (Iterator iter = topo.getAllRouters().getRouters().iterator(); iter.hasNext();) {
		Router element = (Router) iter.next();
		element.setScoreThreshold(var);
	}
	
	//System.out.println("$$$ Simulation #"+i+" $$$");
	System.out.println("$$$ Simulation #"+i+" $$$");
	em.setEventVectors(saveBegin);
	em.setCpt(0);
	am.removeSimulation();
	am.addAttack(a);
	generator.generateAttack(a);
	topo.generateTraffic(4, 6, 1, 1, 100, 0.3, 38);

	*/
	em.play(maxi+20);
	//am.computeRates(i);
	System.out.println("maxi = "+maxi);	
	//}


//	am.displayAverageDetectionRate();
//	System.out.print("\n");
/*	
	am.displayAverageDetectionRate(25);
	System.out.print("\n");
	am.displayAverageFalsePositive(25);
	System.out.print("\n");
	
//am.displayAverageFalsePositive();
//	System.out.print("\n");
	am.displayAverageDetectionTime(25);
	*/
	
	
	
	
	

}



}
	

