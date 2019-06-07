package stressTests;

import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;



public class MainWindow {
	public String cpuName = CPUNameWindows.getInfo();
	public String[] ramSticks = RamInfoWindows.info().split("\n");
	public String[] gpus = GPUNameWindows.info().split("\n");
	public String[] disks = DiskNamesWindows.info().split("\n");
	public Window w;
	boolean close = false;
	ImageIcon iconImage;
	public void run(){
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource("icon.png");
		iconImage = new ImageIcon(resource);
		int GPUdist = gpus.length * 15;
		int RAMdist = ramSticks.length * 15;
		int dist = 135 + GPUdist + RAMdist + (15 * (disks.length)) + 20;
		w = new Window(400, dist + 50, false, "System Tools", false, false, iconImage);
		w.visible(true);
		w.backgroundColor = new Color(40,40,40);
		w.redraw();
		w.clear();
		w.redraw();
		
		Button closeButton = new Button(175, dist + 15, 50, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 8, 14, "close", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				close = true;
			}
		};
		w.addButton(closeButton);
		Button runLoad = new Button(50, 50, 100, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 8, 14, "Thermal Test", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				w.drawString(160, 65, "Running...", new Color(255,255,255));
				w.redraw();
				CPULoad load = new CPULoad();
				load.run(iconImage);
			}
		};
		w.addButton(runLoad);
		Button runScore = new Button(50, 80, 100, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 8, 14, "Benchmark", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				w.drawString(160, 95, "Running...", new Color(255,255,255));
				w.redraw();
				CPUScore score = new CPUScore();
				score.run(iconImage.getImage());
			}
		};
		w.addButton(runScore);
		//System.out.println(gpus[0].replaceAll(" ", ""));
		close = false;
		while(!close){
			w.drawString(30, 20, "Computer Info/Testing application ver 0.1", new Color(255,255,255));
			w.drawString(50, 40, "CPU: " + cpuName, new Color(255,255,255));
			w.drawString(50, 120, "GPUs: ", new Color(255,255,255));
			for(int i = 0; i < gpus.length; i++){
				w.drawString(60, 135 + i*15, "" + (i+1) + ". " + gpus[i], new Color(255,255,255));
			}
			w.drawString(50, 135 + GPUdist, "RAM:", new Color(255,255,255));
			for(int i = 0; i < ramSticks.length; i++){
				w.drawString(60, 135 + GPUdist + (i+1)*15, i+1 + ". " + ramSticks[i], new Color(255,255,255));
			}
			w.drawString(50, 135 + GPUdist + RAMdist + 15, "Partitions:", new Color(255,255,255));
			for(int i = 0; i < disks.length; i++){
				w.drawString(60, 135 + GPUdist + RAMdist + 15 + 15*(i+1), disks[i], new Color(255,255,255));
			}
			w.drawButtons();
			w.updateButtons();
			w.redraw();
			w.pause(10);
			w.clear();
		}
		//System.out.println("finsih");
		w.visible(false);
		w.clearAll();
		
		/*CPULoad load = new CPULoad();
		load.run();
		load = null;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CPUScore score = new CPUScore();
		score.run();
		System.exit(0);*/
		System.exit(0);
	}
}
