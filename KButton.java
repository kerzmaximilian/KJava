

import java.awt.Color;

public class KButton {

	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int width = 100;
	private int height = 25;
	private KRect rect;
	private int lineWidth = 2;
	private int color = KGraphics.getFontColorInt();
	private int bgcolor = KGraphics.getDefaultColorInt();
	private boolean fill = false;
	private boolean visible = true;
	private KFont font;
	private boolean fontSet = false;
	private String fontT = KGraphics.getDefaultFont();
	private int fontSize = KGraphics.getDefaultFontSize();
	private boolean pressed = false;

	private String name;

	public KButton(int posX, int posY) {
		this.posXg = posX;
		this.posYg = posY;
	}

	public KButton(String name, int posX, int posY) {
		this.name = name;
		this.posXg = posX;
		this.posYg = posY;
	}

	public KButton(int posX, int posY, int width, int height) {
		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		construct();
	}

	public KButton(String name, int posX, int posY, int width, int height) {
		this.name = name;
		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		construct();
	}

	private void construct() {

	}

	public int[] add(int[] global) {
		int[] screen = global;

		if (visible == true) {
			rect = new KRect(posXg, posYg, width, height);
			if (fill == true) {
				rect.fill(new Color(bgcolor));
			} else {
				rect.noFill();
			}
			rect.setLineWidth(lineWidth);
			screen = rect.add(screen);

			while (fontSet == false) {
				font = new KFont(fontT, fontSize);
				font.print(name);
				int strWidth = font.getStringWidth();
				int strHeight = font.getStringHeight();
				if ((strWidth < width - 10) && (strHeight < height - 4)) {
					int x = width / 2 - strWidth / 2;
					int y = height / 2 - strHeight / 2;
					font.translate(posXg + x, posYg + y);
					font.setFontColor(new Color(color));
					fontSet = true;
				} else {
					fontSize -= 1;
				}
			}
			screen = font.add(screen);
		}

		return screen;
	}

	public void addActionListener() {

		if (KMouseEvent.mouseIn(posXg, posYg, width, height)) {
			pressed = true;
			fill=true;
			fontSet=false;
			color = KGraphics.getBGColorInt();
		} 

		if (KMouseEvent.released()) {
			pressed = false;
			fill=false;
			fontSet=false;
			color=KGraphics.getFontColorInt();
		}
	}

	// SETTERS

	public void setTitle(String name) {
		this.name = name;
	}

	public void fill() {
		color = KGraphics.getDefaultColorInt();
		fill = true;
		construct();
	}

	public void noFill() {
		fill = false;
		construct();
	}

	public void fill(Color c) {
		bgcolor = c.getRGB();
		fill = true;
		construct();
	}

	public void noFill(Color c) {
		bgcolor = c.getRGB();
		fill = false;
		construct();
	}

	public void setFontColor(Color c) {
		color = c.getRGB();
		construct();
	}

	public void setFontSize(int s) {
		fontSize = s;
		construct();
	}

	public void setFont(String f) {
		fontT = f;
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public void setDimensions(int w, int h) {
		width = w;
		height = h;
		construct();
	}

	public void translate(int x, int y) {
			posYg = y;
			posXg = x;
		construct();
	}

	public void reDraw(int x, int y, int w, int h) {
		if ((x < WIDTH - width) && (x >= 0))
			posYg = x;
		if ((y < HEIGHT - height) && (y >= 0))
			posXg = y;
		width = w;
		height = h;
		construct();
	}

	public void setLineWidth(int lw) {
		lineWidth = lw;
	}
	
	public void setStatus(boolean s){
		pressed = s;
	}

	// GETTERS

	public boolean getStatus() {
		return pressed;
	}


}
