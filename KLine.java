

import java.awt.Color;

public class KLine {

	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	private int lineWidth = KGraphics.getDefaultLineWidth();
	private  int color = KGraphics.getDefaultColorInt();
	private int[] yCoord;
	private boolean visible = true;

	// Constructor
	public KLine(int x1, int y1, int x2, int y2) {
		if ((x1 == x2) && (y1 == y2)) {
			System.err.println("KLINE: Equal coordinates. Use KPoint instead.");
		} else {
			if ((x1 < WIDTH) && (x1 >= 0))
				this.x1 = x1;
			if ((y1 < HEIGHT) && (y1 >= 0))
				this.y1 = y1;
			if ((x2 < WIDTH) && (x2 >= 0))
				this.x2 = x2;
			if (x2 >= WIDTH)
				this.x2 = WIDTH - 1;
			if ((y2 < HEIGHT) && (y2 >= 0))
				this.y2 = y2;
			if (y2 >= HEIGHT)
				this.y2 = HEIGHT - 1;
			if (x2 - x1 < 0) {
				int x = this.x1;
				this.x1 = this.x2;
				this.x2 = x;
				int y = this.y1;
				this.y1 = this.y2;
				this.y2 = y;
			}
		}
		construct();
	}

	// FUNCTIONs

	private void construct() {

		// line equation
		if (x2 - x1 > 0) {
			yCoord = new int[x2 - x1 + 1];
			int it = 0;
			float m = ((float) (y2 - y1)) / ((float) (x2 - x1));
			int b = -Math.round(m * (x1)) + y1;
			for (int i = x1; i <= x2; i++) {
				yCoord[it] = Math.round(m * i + b);
				it++;
			}
		}
	}

	public int[] add(int[] global) {

		int[] screen = global;
		if (screen.length != WIDTH * HEIGHT) {
			throw new IllegalArgumentException(
					"PixelArray does not have the correct pixel number.");
		}
		if (visible == true) {
			if (x2 - x1 > 0) {
				// ny:x ratio - used to fill the gaps between calculated points
				int range = y2 - y1;
				if (range < 0) {
					range *= -1;
				}
				int ratio = range / (x2 - x1);
				for (int k = 1; k < lineWidth + 1; k++) {
					for (int i = 0; i < yCoord.length; i++) {
						// yCoord.length == x range
						for (int j = 0; j < ratio + 1; j++) {
							if (WIDTH * (yCoord[i] + j) + i + x1 + k < screen.length)
								if (y2 - y1 != 0)
									screen[WIDTH * (yCoord[i] + j) + i + x1 + k] = color;
							if (y2 - y1 == 0)
								screen[WIDTH * (yCoord[i] + j + k) + i + x1] = color;

						}
					}
				}
			} else {
				// straight vertical line
				if (x2 - x1 == 0) {
					for (int i = y1; i < y2 + 1; i++) {
						for (int j = 1; j < lineWidth + 1; j++) {
							screen[WIDTH * i + x1 + j] = color;
						}
					}
				} else {
					System.err.println("KLINE: x2-x1<0.");
				}
			}
		}
		return screen;
	}

	// SETTERS

	public void noFill() {
		visible = false;
		construct();
	}

	public void fill() {
		color = KGraphics.getDefaultColorInt();
		visible = true;
		construct();
	}

	public void fill(Color c) {
		color = c.getRGB();
		visible = true;
		construct();
	}

	public void setVisible(boolean b) {
		visible = b;
		construct();
	}

	public void translate(int x1, int y1, int x2, int y2) {
		if ((x1 < WIDTH) && (x1 >= 0))
			this.x1 = x1;
		if ((y1 < HEIGHT) && (y1 >= 0))
			this.y1 = y1;
		if ((x2 < WIDTH) && (x2 >= 0))
			this.x2 = x2;
		if ((y2 < HEIGHT) && (y2 >= 0))
			this.y2 = y2;
		if (x2 - x1 < 0) {
			int x = this.x1;
			this.x1 = this.x2;
			this.x2 = x;
			int y = this.y1;
			this.y1 = this.y2;
			this.y2 = y;
		}
		construct();
	}

	public void setLineWidth(int lw) {
		if ((x1 + lw < WIDTH) && (x2 + lw < WIDTH)) {
			lineWidth = lw;
		} else {
			lineWidth = WIDTH - x1;
			if (lineWidth > WIDTH - x2) {
				lineWidth = WIDTH - x2;
			}
		}
		construct();
	}
	// GETTERS

}
