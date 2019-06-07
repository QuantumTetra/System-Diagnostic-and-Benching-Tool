package stressTests;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;



public class CPULoad {
	public Window w;
	ArrayList<BusyThread> threads = new ArrayList<>();
	ArrayList<Integer> bools = new ArrayList<>();
	public int numberOfThreads = 0;
	public int threadsCompleted = 0;
	public boolean close = false;
	public boolean closedOut = false;
	public int numCore = 0;
	public int numThreadsPerCore = 0;
	public int loadPercentage = 0;
	public double load = 0;
	public long duration = 0;
	public void run(ImageIcon image){
		String cpuName = CPUNameWindows.getInfo();
		w = new Window(300, 200, false, "CPU Load", false, true, image);
		w.backgroundColor = new Color(40,40,40);
		paintMainScreen(cpuName);
		w.visible(false);
		w.clearAll();
		if(!closedOut){
			//System.out.println("go");
			load = loadPercentage/100.0;
			//System.out.println(duration);
			numberOfThreads = numCore * numThreadsPerCore;
			for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
				//System.out.println("new thread");
				threads.add(new BusyThread("Thread" + thread, load, duration));
			}
			//System.out.println("" + numberOfThreads);
			for(int i = 0; i < numberOfThreads; i++){
				threads.get(i).start();
			}
			//System.out.println("waiting for " + numberOfThreads + " threads.");
			try {
				Thread.sleep(duration + 2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("finshed");
		}
	}
	public void paintMainScreen(String lolz){
		Button cpuPlus = new Button(50, 40, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 6, 15, "+", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				numCore++;
			}
		};
		Button cpuMinus = new Button(72, 40, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 9, 14, "-", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				if(numCore>0){
					numCore--;
				}
			}
		};
		Button threadPlus = new Button(50, 60, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 6, 15, "+", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				numThreadsPerCore++;
			}
		};
		Button threadMinus = new Button(72, 60, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 9, 14, "-", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				if(numThreadsPerCore>0){
					numThreadsPerCore--;
				}
			}
		};
		Button loadPlus = new Button(50, 80, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 6, 15, "+", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				if(loadPercentage<100){
					loadPercentage+=1;
				}
			}
			@Override
			public void clickDetected(){
				
			}
		};
		Button loadMinus = new Button(72, 80, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 9, 14, "-", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				if(loadPercentage>0){
					loadPercentage-=1;
				}
			}
			@Override
			public void clickDetected(){
				
			}
		};
		Button timePlus = new Button(50, 100, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 6, 15, "+", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				duration+=1000;
			}
			@Override
			public void clickDetected(){
				
			}
		};
		Button timeMinus = new Button(72, 100, 20, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 9, 14, "-", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				if(duration>999){
					duration-=1000;
				}
			}
			@Override
			public void clickDetected(){
				
			}
		};
		Button run = new Button(50, 165, 50, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 8, 14, "run", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				close = true;
			}
		};
		Button closeButton = new Button(200, 165, 50, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 8, 14, "close", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				close = true;
				closedOut = true;
			}
		};
		w.addButton(cpuPlus);
		w.addButton(cpuMinus);
		w.addButton(threadPlus);
		w.addButton(threadMinus);
		w.addButton(loadPlus);
		w.addButton(loadMinus);
		w.addButton(timePlus);
		w.addButton(timeMinus);
		w.addButton(run);
		w.addButton(closeButton);


		while(!close){
			w.drawString(50, 20, "CPU load application ver 0.1", new Color(255,255,255));
			w.drawString(15, 35, lolz, new Color(255,255,255));

			w.drawString(95, 60 - 3, "Cores: " + numCore, new Color(255,255,255));
			w.drawString(95, 80 - 3, "Threads per core: " + numThreadsPerCore, new Color(255,255,255));
			w.drawString(95, 100 - 3, "Load: %" + loadPercentage, new Color(255,255,255));
			w.drawString(95, 120 - 3, "Duration: " + duration/1000+ " sec", new Color(255,255,255));


			w.drawButtons();
			w.updateButtons();
			w.redraw();
			w.pause(1000/60);
			w.clear();
		}
	}
	private class BusyThread extends Thread {
		private double load;
		private long duration;
		public BusyThread(String name, double load, long duration) {
			super(name);
			this.load = load;
			this.duration = duration;
		}
		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			try {
				// Loop for the given duration
				while (System.currentTimeMillis() - startTime < duration) {
					// Every 100ms, sleep for the percentage of unladen time
					if (System.currentTimeMillis() % 100 == 0) {
						Thread.sleep((long) Math.floor((1 - load) * 100));
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done");
			
		}
	}
}