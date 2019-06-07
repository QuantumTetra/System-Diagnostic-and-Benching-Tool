package stressTests;


import java.awt.Color;
import java.awt.Font;

public class Button {
	public int x;
	public int y;
	public int width;
	public int height;
	public Color color;
	public Color borderColor;
	public int borderWidth;
	public boolean border;
	public boolean leftActivate;
	public boolean rightActivate;
	public boolean visible;
	public int textOffsetY;
	public int textOffsetX;
	public String text;
	public Font font;
	public Color textColor;
	public boolean clicked = false;
	public Button(int X, int Y, int Width, int Height, Color Color, boolean Border, int BorderWidth, Color BorderColor, boolean LeftActivates, boolean RightActivates, boolean Visible, int TextOffsetX, int TextOffsetY, String Text, Font Font, Color TextColor){
		x = X;
		y = Y;
		width = Width;
		height = Height;
		color = Color;
		borderColor = BorderColor;
		borderWidth = BorderWidth;
		border = Border;
		leftActivate = LeftActivates;
		rightActivate = RightActivates;
		visible = Visible;
		textOffsetX = TextOffsetX;
		textOffsetY = TextOffsetY;
		text = Text;
		font = Font;
		textColor = TextColor;
	}
	public void clickDetected(){
		if(!clicked){
			clicked = true;
		}
	}
	public void noClick(){
		if(clicked){
			clicked = false;
		}
	}
	public void clicked(){
		//for overriding
	}
}
