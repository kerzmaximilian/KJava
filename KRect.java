

import java.awt.Color;

public class KRect {
	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int width = 0;
	private int height = 0;
	private int lineWidth = KGraphics.getDefaultLineWidth();
	private int color = KGraphics.getDefaultColorInt();
	private int bgcolor = KGraphics.getBGColorInt();
	private int[] pixArr;
	private int[] setOff;
	private boolean fill = false;
	private boolean visible = true;

	public KRect(int posX, int posY, int width, int height) {
		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		construct();
	}

	private void construct() {

		// position within screen
		setOff = new int[height];
		int posG = WIDTH * posXg + posYg;
		for (int i = 0; i < height; i++) {
			setOff[i] = posG;
			posG += WIDTH;
		}
		if (visible == true) {
			if (fill == false) {
				pixArr = new int[height * width];
				for (int i = 0; i < pixArr.length; i++) {
					if ((i <= width * lineWidth) || (i % width < lineWidth)
							|| (i % width >= width - lineWidth)
							|| (i >= height * width - (lineWidth * width))) {
						pixArr[i] = color;
					} else {
						pixArr[i] = bgcolor;
					}
				}
			} else {
				for (int i = 0; i < pixArr.length; i++) {
					pixArr[i] = color;
				}
			}
		} else {
			for (int i = 0; i < pixArr.length; i++) {
				pixArr[i] = bgcolor;
			}
		}
	}

	public int[] add(int[] global) {

		int[] screen = global;
		if (screen.length != WIDTH * HEIGHT) {
			throw new IllegalArgumentException(
					"PixelArray does not have the correct pixel number.");
		}

		int posG = WIDTH * posYg + posXg;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if ((posG + i * WIDTH + j >= 0)
						&& (posG + i * WIDTH + j < screen.length))
					screen[posG + i * WIDTH + j] = pixArr[i * width + j];
			}
		}

		return screen;
	}

	// SETTERS

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
		color = c.getRGB();
		fill = true;
		construct();
	}

	public void noFill(Color c) {
		fill = false;
		color = c.getRGB();
		construct();
	}

	public void setVisible(boolean b) {
		visible = b;
		construct();
	}

	public void setDimensions(int w, int h) {
		width = w;
		height = h;
		construct();
	}

	public void translate(int x, int y) {
		if ((x < WIDTH - width) && (x >= 0))
			posYg = x;
		if ((y < HEIGHT - height) && (y >= 0))
			posXg = y;
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
		construct();
	}

	// GETTERS

}
