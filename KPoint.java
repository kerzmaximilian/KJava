

import java.awt.Color;

public class KPoint {
	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int lineWidth = KGraphics.getDefaultPointWidth();
	private static int color = KGraphics.getDefaultColorInt();
	private int[] setOff;
	private boolean fill = false;

	// Constructors
	public KPoint(int x, int y) {
		if ((x < WIDTH - lineWidth) && (x - lineWidth >= 0))
			posYg = x;
		if ((y < HEIGHT - lineWidth) && (y - lineWidth >= 0))
			posXg = y;
		construct();
	}

	// functions

	private void construct() {

			setOff = new int[1];
			setOff[0] = WIDTH * posXg + posYg;
		
		if (lineWidth > 1) {
			setOff = new int[4];
			setOff[0] = WIDTH * posXg + posYg;
			setOff[1] = WIDTH * posXg + posYg + 1;
			setOff[2] = WIDTH * (posXg + 1) + posYg;
			setOff[3] = WIDTH * (posXg + 1) + posYg + 1;
		}

		if (lineWidth > 2) {
			setOff = new int[9];
			setOff[0] = WIDTH * posXg + posYg;
			setOff[1] = WIDTH * posXg + posYg + 1;
			setOff[2] = WIDTH * (posXg + 1) + posYg;
			setOff[3] = WIDTH * (posXg + 1) + posYg + 1;
			setOff[4] = WIDTH * (posXg - 1) + posYg;
			setOff[5] = WIDTH * (posXg - 1) + posYg - 1;
			setOff[6] = WIDTH * (posXg + 1) + posYg - 1;
			setOff[7] = WIDTH * (posXg - 1) + posYg + 1;
			setOff[8] = WIDTH * posXg + posYg - 1;
		}
		if (lineWidth > 3) {
			setOff = new int[13];
			setOff[0] = WIDTH * posXg + posYg;
			setOff[1] = WIDTH * posXg + posYg + 1;
			setOff[2] = WIDTH * (posXg + 1) + posYg;
			setOff[3] = WIDTH * (posXg + 1) + posYg + 1;
			setOff[4] = WIDTH * (posXg - 1) + posYg;
			setOff[5] = WIDTH * (posXg - 1) + posYg - 1;
			setOff[6] = WIDTH * (posXg + 1) + posYg - 1;
			setOff[7] = WIDTH * (posXg - 1) + posYg + 1;
			setOff[8] = WIDTH * posXg + posYg - 1;
			setOff[9] = WIDTH * (posXg - 2) + posYg;
			setOff[10] = WIDTH * (posXg + 2) + posYg;
			setOff[11] = WIDTH * posXg + posYg + 2;
			setOff[12] = WIDTH * posXg + posYg - 2;
		}

	}

	public int[] add(int[] global) {
		int[] screen = global;
		if (fill == true) {
			for (int i = 0; i < setOff.length; i++) {
				screen[setOff[i]] = color;
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


	public void translate(int x, int y) {
		if ((x < WIDTH - lineWidth) && (x - lineWidth >= 0))		
			posYg = x;
		if ((y < HEIGHT - lineWidth) && (y - lineWidth >= 0))
			posXg = y;
		construct();
	}

	public void setPointWidth(int pw) {
		lineWidth = pw;
		construct();
	}
	// GETTERS

}
