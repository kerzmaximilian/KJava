

import java.awt.Color;

public class KGraph {
	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int xAxis = 100;
	private int yAxis = 100;
	private String xAxisT = "xAxis";
	private String yAxisT = "yAxis";
	private int lineWidth = 1;
	private int strHeight;
	private int color = KGraphics.getFontColorInt();
	private boolean visible = true;
	
	@SuppressWarnings("unused")
	private boolean legend = true;
	private KLine[] curve;
	private String fontT = KGraphics.getDefaultFont();
	private int fontSize = KGraphics.getDefaultFontSize();
	private String name;
	private int[] points;
	private int[] coord;
	private int ratio;

	// CONSTRUCTOR

	public KGraph(int[] points, int posX, int posY, int xAxis, int yAxis) {
		this.coord = points;
		posXg = posX;
		posYg = posY;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.ratio = xAxis / (coord.length - 1);
		yAdjust();

		construct();
	}

	public KGraph(String title, int[] points, int posX, int posY, int xAxis,
			int yAxis) {
		this.coord = points;
		posXg = posX;
		posYg = posY;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.name = title;
		this.ratio = xAxis / (coord.length - 1);
		yAdjust();

		construct();
	}

	private void yAdjust() {
		// adjust points' y coordinates
		int max = 0;
		int min = 0;
		for (int c : coord) {
			if (c > max)
				max = c;
			if (c < min)
				min = c;
		}

		// HIGHEST ERROR quote - needs to be adjusted
		float yRange = ((float) yAxis) / ((float) (max - min))
				- ((((float) yAxis) / ((float) (max - min)) / 2));
		points = new int[coord.length];

		for (int i = 0; i < coord.length; i++) {
			points[i] = (int) ((float) -(coord[i] - max) * yRange);
		}
	}

	// FUNCTIONS

	private void construct() {

		curve = new KLine[points.length - 1];
		for (int i = 0; i < curve.length; i++) {
			curve[i] = new KLine(i * ratio + posXg, posYg + points[i], (i + 1)
					* ratio + posXg, posYg + points[i + 1]);
		}

	}

	public int[] add(int[] global) {

		int[] screen = global;
		if (visible == true) {
			for (int i = 0; i < curve.length; i++) {
				curve[i].setLineWidth(lineWidth);
				curve[i].fill(new Color(color));
				screen = curve[i].add(screen);
			}
		}

		// axes
		KLine xL = new KLine(posXg, posYg + yAxis, posXg + xAxis, posYg + yAxis);
		xL.fill(new Color(KGraphics.getFontColorInt()));
		xL.setLineWidth(lineWidth + 1);
		screen = xL.add(screen);
		xL = new KLine(posXg - lineWidth, posYg, posXg - lineWidth, posYg
				+ yAxis);
		xL.fill(new Color(KGraphics.getFontColorInt()));
		xL.setLineWidth(lineWidth + 1);
		screen = xL.add(screen);

		// annotation (X)
		/*
		 * int xInter = xAxis/10; KLine xLi; for(int i=0; i<11; i++){ xLi = new
		 * KLine(posXg+xInter*i, posYg + yAxis, posXg+xInter*i, posYg +
		 * yAxis+20); xLi.fill(new Color(KGraphics.getFontColorInt()));
		 * xLi.setLineWidth(lineWidth + 1); screen = xL.add(screen); }
		 */

		// title
		if (name != null) {
			KFont des = new KFont(fontT, fontSize);
			des.print(name);
			if (posYg - des.getStringHeight() * 2 > 0) {
				des.translate(posXg + xAxis / 2 - des.getStringWidth() / 2,
						posYg - des.getStringHeight() * 2);
				screen = des.add(screen);

				KLine line = new KLine(posXg + xAxis / 2 - des.getStringWidth()
						/ 2, posYg - des.getStringHeight(), posXg + xAxis / 2
						+ des.getStringWidth() / 2, posYg
						- des.getStringHeight());
				screen = line.add(screen);
			}
		}

		// labels
		if (xAxisT != null) {
			KFont des = new KFont(fontT, fontSize);
			des.print(xAxisT);
			if (posYg + yAxis + des.getStringHeight() * 2.5 < HEIGHT) {
				des.translate(
						posXg + xAxis / 2 - des.getStringWidth() / 2,
						posYg + yAxis + des.getStringHeight() * 2
								- des.getStringHeight() / 2);
				screen = des.add(screen);
			}

			des = new KFont(fontT, fontSize);
			des.print(yAxisT);

			des.translate(posXg - des.getStringWidth() / 2,
					posYg - des.getStringHeight());
			screen = des.add(screen);
		}
		return screen;
	}

	// SETTERS

	public void setLegend(boolean b) {
		legend = b;
		construct();
	}

	public void setDimensions(int w, int h) {
		xAxis = w;
		yAxis = h;
		construct();
	}

	public void translate(int x, int y) {
			posYg = x;
			posXg = y;
		construct();
	}

	public void reDraw(int x, int y, int w, int h) {
		if ((x < WIDTH - xAxis) && (x >= 0))
			posYg = x;
		if ((y < HEIGHT - yAxis) && (y >= 0))
			posXg = y;
		xAxis = w;
		yAxis = h;
		construct();
	}

	public void setLineWidth(int lw) {
		lineWidth = lw;
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
		construct();
	}

	public void setPoints(int[] points) {
		this.points = points;
		construct();
	}

	public void setTitle(String title) {
		name = title;
	}

	public void setDescription(String title, String xAxis, String yAxis) {
		name = title;
		yAxisT = yAxis;
		xAxisT = xAxis;
	}

	// GETTERS

	public int getFontHeight() {
		return strHeight;
	}

	public int getGlobalPosY() {
		return posYg;
	}

	public int getGlobalPosX() {
		return posXg;
	}
	
	public int getXAxis(){
		return xAxis;
	}
	
	public int getYAxis(){
		return yAxis;
	}

}
