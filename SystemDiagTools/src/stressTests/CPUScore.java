package stressTests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class CPUScore {
	String cpuName = CPUNameWindows.getInfo();
	BufferedImage startImage;
	boolean close = false;
	public Window w;
	long score = 0;
	boolean closed = false;
	ArrayList<ComputationalThread> threads = new ArrayList<>();
	public void run(Image image){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource("image.jpg");
		ImageIcon icon = new ImageIcon(resource);
		Image pew = icon.getImage();
		startImage = new BufferedImage(pew.getWidth(null), pew.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		startImage.getGraphics().drawImage(pew, 0, 0, null);
		w = new Window((int)width, (int)height, true, "CPU Score", true, false);
		
		int iWidth = startImage.getWidth();
		int iHeight = startImage.getHeight();
		w.visible(true);
		w.backgroundColor = new Color(40,40,40);
		w.redraw();
		w.clear();
		w.redraw();
		w.visible(true);
		/*how to find color at point and invert it;
		BufferedImage a = (BufferedImage)startImage;
		int c = a.getRGB(0, 0);
		int red = (c & 0x00ff0000) >> 16;
		int green = (c & 0x0000ff00) >> 8;
		int blue = (c & 0x000000ff);
		red = 255 - red;
		green = 255 - green;
		blue = 255 - blue;
		Color color = new Color(red,green,blue);
		System.out.println(color.toString());*/

		//invert colors
		//invert image
		//invert colors
		//invert image
		//flip image
		//invert colors
		//flip image
		//invert colors
		int processors = Runtime.getRuntime().availableProcessors();
		int n = processors;
		//n=1;
		//System.out.println(n);
		for(int i = 0; i < n; i++){
			threads.add(new ComputationalThread("Thread " + i, startImage.getSubimage(i * (iWidth / n), 0, iWidth / n, iHeight)));
		}
		long startTime = System.nanoTime();
		for(int i = 0; i < threads.size(); i++){
			threads.get(i).start();
		}
		while(!threadsDone()){}
		long endTime = System.nanoTime();
		score = (endTime - startTime) / 1000;
		w.visible(true);
		w.clear();
		for(int i =0; i < threads.size();i++){
			startImage.getGraphics().drawImage(threads.get(i).i, i * (iWidth / n), 0, null);
		}
		double scale = width / iWidth;
		Button closeButton = new Button(10, 5, 50, 20, new Color(230,0,0), true, 2, new Color(100, 100, 100), true, false, true, 8, 14, "close", null, new Color(255,255,255)){
			@Override
			public void clicked(){
				closed = true;
			}
		};
		w.addButton(closeButton);
		w.scaleDraw((Image)startImage, 0, 0, scale);
		w.drawString(70, 20, cpuName + ": " + score, new Color(255,255,255));
		w.drawButtons();
		w.redraw();
		closed = false;
		close = false;
		closed = false;
		while(!closed){
			w.updateButtons();
		}
		w.visible(false);
		//System.out.println("finished");

	}
	public boolean threadsDone(){
		for(ComputationalThread t: threads){
			if(!t.complete){
				return false;
			}
		}
		return true;
	}
	private class ComputationalThread extends Thread {
		BufferedImage i;
		int width = 0;
		int height = 0;
		public boolean complete = false;
		public ComputationalThread(String name, Image imagePeice) {
			super(name);
			i = (BufferedImage)imagePeice;
			width = i.getWidth();
			height = i.getHeight();
			//System.out.println(width + " " + height);
		}
		@Override
		public void run() {
			for(int i = 0; i < 20; i++){
				invertColors();
				horizontalFlip();
				invertColors();
				horizontalFlip();
				invertColors();
				verticalFlip();
				invertColors();
				verticalFlip();
			}
			complete = true;
		}
		public void horizontalFlip(){
			AffineTransform tx = new AffineTransform();
			tx.scale(-1, 1);
			tx.translate(-width, 0);
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			i = op.filter((BufferedImage)i, null);
		}
		public void verticalFlip(){
			AffineTransform tx = new AffineTransform();
			tx.scale(1, -1);
			tx.translate(0, -height);
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			i = op.filter((BufferedImage)i, null);
		}
		public void invertColors(){
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					int c = i.getRGB(x, y);
					int red = (c & 0x00ff0000) >> 16;
				int green = (c & 0x0000ff00) >> 8;
			int blue = (c & 0x000000ff);
			red = 255 - red;
			green = 255 - green;
			blue = 255 - blue;
			int redF = (red<<16) & 0x00ff0000;
			int greenF = (green<<8) & 0x0000ff00;
			int blueF = (blue) & 0x000000ff;
			int RGB = 0xff000000 | redF | greenF | blueF;
			i.setRGB(x, y, RGB);
				}
			}
		}
	}
}
