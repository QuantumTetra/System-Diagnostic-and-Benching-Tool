package stressTests;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Window {
	public JFrame f = new JFrame();
	@SuppressWarnings("serial")
	public JPanel p = new JPanel(){
		@Override
		protected void paintComponent (Graphics g){
			super.paintComponent(g);
			g.drawImage(i, 0, 0, null);
		}
	};
	public Image i;
	public Image i_R;
	public double MX = 0;
	public double MY = 0;
	public boolean mousePressed = false;
	public boolean rightClick = false;
	public ArrayList<Button> buttons = new ArrayList<>();
	public int windowBarHeight = 28;
	public int heightAdjust = 0;
	public int mouseAdjustX = 7;
	public int windowWidth = 0;
	public int windowHeight = 0;
	public Color backgroundColor = new Color(255,255,255);
	public Window(int width, int height, boolean undecorated, String title, boolean fullscreen, boolean visible){
		windowWidth = width;
		windowHeight = height;
		f.addMouseListener(new MouseClickCheck(this));
		if(!undecorated){
			height += windowBarHeight;
			heightAdjust = windowBarHeight;
		}
		f.setSize(width, height);
		i = f.createImage(width,height);
		i_R = f.createImage(width,height);
		f.setUndecorated(undecorated);
		if(fullscreen){
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		f.setVisible(visible);
		p.setLayout(new GridLayout());
		p.setBackground(new Color (255,255,255));
		f.add(p);
		f.addMouseMotionListener(new MousePosCheck(this));
		f.setName(title);
		f.setTitle(title);
		i = f.createImage(width,height);
		i_R = f.createImage(width,height);
		p.repaint();
		f.toFront();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowBarHeight = f.getHeight()-height;
		//System.out.println(windowBarHeight);
	}
	public Window(int width, int height, boolean undecorated, String title, boolean fullscreen, boolean visible, ImageIcon icon){
		windowWidth = width;
		windowHeight = height;
		f.setIconImage(icon.getImage());
		f.addMouseListener(new MouseClickCheck(this));
		if(!undecorated){
			height += windowBarHeight;
			heightAdjust = windowBarHeight;
		}
		f.setSize(width, height);
		i = f.createImage(width,height);
		i_R = f.createImage(width,height);
		f.setUndecorated(undecorated);
		if(fullscreen){
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		f.setVisible(visible);
		p.setLayout(new GridLayout());
		p.setBackground(new Color (255,255,255));
		f.add(p);
		f.addMouseMotionListener(new MousePosCheck(this));
		f.setName(title);
		f.setTitle(title);
		i = f.createImage(width,height);
		i_R = f.createImage(width,height);
		p.repaint();
		f.toFront();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowBarHeight = f.getHeight()-height;
		//System.out.println(windowBarHeight);
	}
	public void addButton(Button button){
		buttons.add(button);
	}
	public void removeButton(int pos){
		buttons.remove(pos);
	}
	public void updateButtons(){
		for(Button button: buttons){
			if(button.leftActivate){
				if(mousePressed && button.x < (int)MX && button.x + button.width > (int)MX && button.y < (int)MY && button.y + button.height > (int)MY){
					if(!button.clicked){
						button.clicked();
						button.clickDetected();
					}
				}else{
					button.noClick();
				}
			}else{
				if(button.rightActivate){
					if(rightClick && button.x < (int)MX && button.x + button.width > (int)MX && button.y < (int)MY && button.y + button.height > (int)MY){
						if(!button.clicked){
							button.clicked();
							button.clickDetected();
						}
					}else{
						button.noClick();
					}
				}
			}
		}
	}
	public void drawButtons(){
		for(Button b: buttons){
			if(b.visible){
				drawBlock(b.x, b.y, b.width, b.height, b.color);
				if(b.border){
					drawLine(b.x, b.y, b.x + b.width, b.y, b.borderColor, b.borderWidth);
					drawLine(b.x, b.y + b.height, b.x + b.width, b.y + b.height, b.borderColor, b.borderWidth);
					drawLine(b.x, b.y, b.x, b.y + b.height, b.borderColor, b.borderWidth);
					drawLine(b.x + b.width, b.y, b.x + b.width, b.y + b.height, b.borderColor, b.borderWidth);
				}
				if(b.font != null){
					drawString(b.x + b.textOffsetX, b.y + b.textOffsetY, b.text, b.textColor);
				}else{
					drawString(b.x + b.textOffsetX, b.y + b.textOffsetY, b.text, b.textColor, b.font);
				}
			}
		}
	}
	public void clearAll(){
		clear();
		buttons.clear();
	}
	public void visible(boolean visible){
		f.setVisible(visible);
	}
	public double differenceInRotation(double first, double second){
		double rot1 = fixRotation(first);
		double rot2 = fixRotation(second);
		double rotationNeeded = rot1 - rot2;
		if(Math.abs(rot1-rot2+Math.PI*2.0)<Math.abs(rotationNeeded)){
			rotationNeeded=rot1-(rot2+Math.PI*2.0);
		}
		if(Math.abs(rot1-rot2-Math.PI*2.0)<Math.abs(rotationNeeded)){
			rotationNeeded=rot1-(rot2-Math.PI*2.0);
		}
		return rotationNeeded;
	}
	public double toRadians(double degrees){
		return degrees  * (Math.PI / 180.0);
	}
	public double toDegrees(double radians){
		return radians * (180.0 / Math.PI);
	}
	public double upTo0(double num){
		if(num<0){
			num = 0;
		}
		return num;
	}
	public double fixRotation(double rotation){
		if(rotation>2*Math.PI){
			int howfar = (int)(rotation/Math.PI/2);
			for(int c=0;c<howfar;c++){
				rotation-=2*Math.PI;
			}
		}
		if(rotation<0){
			int howfar = 1+ (int)((rotation*-1.0)/Math.PI/2);
			for(int c=0;c<howfar;c++){
				rotation+=2*Math.PI;
			}
		}
		return rotation;
	}
	public void clear(){
		i = null;
		i = f.createImage(f.getSize().width, f.getSize().height);
		drawBlock(0,0,windowWidth,windowHeight,backgroundColor);
	}
	public void drawCircle(double x, double y, double radius, Color color){
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.fillOval((int)x - (int)radius, (int)y - (int)radius, (int)radius *2, (int)radius *2);
	}
	public void scaleDraw(Image image, int x, int y , double scale){
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		Graphics g = i.getGraphics();
		g.drawImage(op.filter((BufferedImage)image, null), x, y , null);
	}
	public Image getScaledImage(Image image, double scale){
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		return op.filter((BufferedImage)image, null);
	}
	public void horizontalFlipDraw(Image image, int x, int y, boolean right){
		AffineTransform tx = new AffineTransform();
		if(!right){
			tx.scale(-1, 1);
			tx.translate(-image.getWidth(null), 0);
		}
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		Graphics g = i.getGraphics();
		g.drawImage(op.filter((BufferedImage)image, null), x, y , null);
	}
	public void drawToImage( Image image, int x, int y){
		Graphics g = i.getGraphics();
		g.drawImage(image, x, y, null);
	}
	public void drawString(int x, int y, String text, Color color){
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.drawString(text, x, y);
	}
	public void drawString(int x, int y, String text, Color color, Font font){
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, x, y);
	}
	public void drawBlock(int x, int y, int width, int height, Color color){
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.fillRect(x,y,width,height);
	}
	public void drawLine(Point main, Point trail, Color color, int width){
		Graphics g = i.getGraphics();
		g.setColor(color);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(width));
		g2d.drawLine((int)main.getX(), (int)main.getY(), (int)trail.getX(), (int)trail.getY());
	}
	public void drawLine(int x, int y, int x2, int y2, Color color, int width){
		Graphics g = i.getGraphics();
		g.setColor(color);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(width));
		g2d.drawLine(x, y, x2, y2);
	}
	public void drawToImageRot(double rot, double rotPosX, double rotPosY, Image image, int x , int y, boolean right){
		Image test = image;
		BufferedImage test2 = (BufferedImage)test;
		double X = rotPosX;
		double Y = rotPosY;		
		AffineTransform tx = AffineTransform.getRotateInstance(rot,X,Y);
		if(!right){
			tx.scale(1, -1);
			tx.translate(0, -image.getHeight(null));
		}
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		Graphics g = i.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(op.filter(test2,  null), x, y, null);
	}
	public void drawPolygon(Polygon poly){
		Graphics g = i.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color (200,0,0));
		g2d.drawPolygon(poly);
	}
	public void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// do nothing
		}
	}
	public void redraw(){
		p.repaint();
	}
	public boolean isInside(Point block, int width, int height, Point pos){
		return ( (block.getX() <= pos.getX() && (block.getX()+width) >= pos.getX()) && (block.getY() <= pos.getY() && (block.getY()+height) >= pos.getY()) );
	}
	public void changeMX(double x){
		MX = x;
	}
	public void changeMY(double y){
		MY = y;
	}
	public void mousePressed(){
		mousePressed = true;
	}
	public void mouseUnpressed(){
		mousePressed = false;
	}
	public void rightPressed(){
		rightClick = true;
	}
	public void rightUnpressed(){
		rightClick = false;
	}
}
class MousePosCheck implements MouseMotionListener{
	Window m;
	public MousePosCheck (Window M ){
		m = M;
	}
	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		m.changeMX(event.getX() - m.mouseAdjustX);
		m.changeMY(event.getY() - m.heightAdjust);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		m.changeMX(event.getX() - m.mouseAdjustX);
		m.changeMY(event.getY() - m.heightAdjust);
	}

}
class MouseClickCheck implements MouseListener{
	Window m;
	public MouseClickCheck (Window M){
		m=M;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton()==1)
			m.mousePressed();
		if(arg0.getButton()==3)
			m.rightPressed();

	}		

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton()==1)
			m.mouseUnpressed();
		if(arg0.getButton()==3)
			m.rightUnpressed();
	}

}
