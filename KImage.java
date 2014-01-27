

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.RenderedImage;

public class KImage {
	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int width = 0;
	private int width1 = 0;
	private int height = 0;
	private int lineWidth = KGraphics.getDefaultLineWidth();
	private static int color = KGraphics.getDefaultColorInt();
	private static int[] colors = KGraphics.getDeaultColors();
	private static float lowRes;
	private BufferedImage img;
	private BufferedImage tempImg;
	private int[] pixArr;
	private boolean fill = false;
	private boolean visible = true;

	private int[][] components;
	private int[][] deComp = new int[1][1];
	private int[] deselect = new int[0];

	// CONSTRUCTOR

	public KImage(int[] image, int posX, int posY) {
		this.posXg = posX;
		this.posYg = posY;
		this.pixArr = image;
		this.width1 = KGraphics.getDefaultImgWidth() - lineWidth;
		construct();
	}

	public KImage(int[] image, int posX, int posY, int imgWidth) {
		this.posXg = posX;
		this.posYg = posY;
		this.pixArr = image;
		this.width1 = imgWidth - lineWidth;
		construct();
	}

	public KImage(Image image, int posX, int posY) {
		this.posXg = posX;
		this.posYg = posY;
		img = (BufferedImage) image;
		this.width1 = KGraphics.getDefaultImgWidth() - lineWidth;
		construct();
	}

	public KImage(Image image, int posX, int posY, int imgWidth) {
		this.posXg = posX;
		this.posYg = posY;
		img = (BufferedImage) image;
		this.width1 = imgWidth - lineWidth;
		construct();
	}

	public KImage(Image image, int imgWidth) {
		img = (BufferedImage) image;
		this.width1 = imgWidth - lineWidth;
		construct();
	}

	// FUNCTIONS
	private void construct() {
		if (width != width1) {
			this.width = width1;
			scaleImg();
			pixArr = ((DataBufferInt) ((BufferedImage) tempImg).getRaster()
					.getDataBuffer()).getData();
			this.height = pixArr.length / width;
		}

	//	lowRes = width / DmelanoNMJ.LOWRES;
	}

	public void scaleImg() {
		if (img != null) {
			Image source = img;
			int w = (int) (width);
			int h = (int) (width);
			BufferedImage bi = getCompatibleImage(w, h);
			Graphics2D g2d = bi.createGraphics();
			double xScale = (double) w / KGraphics.getDefaultImgWidth();
			double yScale = (double) h / KGraphics.getDefaultImgWidth();
			AffineTransform at = AffineTransform.getScaleInstance(xScale,
					yScale);
			g2d.drawRenderedImage((RenderedImage) source, at);
			g2d.dispose();
			tempImg = bi;
		}
	}

	private BufferedImage getCompatibleImage(int w, int h) {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}

	public int[] add(int[] global) {

		int[] screen = global;
		if (visible == true) {
			if (fill == true) {
				KRect rect = new KRect(posYg - 1, posXg - 1, width + 2,
						width + 2);
				rect.noFill(new Color(color));
				screen = rect.add(screen);
			}

			int posG = WIDTH * posXg + posYg;

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					if ((posG + i * WIDTH + j >= 0)
							&& (posG + i * WIDTH + j < screen.length))
						screen[posG + i * WIDTH + j] = pixArr[i * width + j];
				}
			}

			if (components != null) {
				for (int i = 0; i < components.length; i++) {
					for (int j = 0; j < components[i].length; j += 2) {
						KRect rect = new KRect((int) (posYg + lowRes
								* components[i][j + 1]), (int) (posXg + lowRes
								* components[i][j]), (int) lowRes, (int) lowRes);
						if (i < colors.length) {
							rect.fill(new Color(colors[i]));
						} else {
							rect.fill();
						}
						screen = rect.add(screen);
					}
				}
			}

			/*if (deComp != null) {
				for (int i = 0; i < deComp.length; i++) {
					for (int j = 0; j < deComp[i].length / 2; i++) {
						int posXI = (int) (posYg + lowRes * deComp[i][j + 1]);
						int posYI = (int) (posXg + lowRes * deComp[i][j]);

						KLine line = new KLine(posXI, posYI, posXI
								+ (int) lowRes, posYI);
						line.fill();
						line.setLineWidth(1);
						screen = line.add(screen);
						line.translate(posXI, posYI, posXI, posYI
								+ (int) lowRes);
						screen = line.add(screen);
						line.translate(posXI + (int) lowRes, posYI, posXI
								+ (int) lowRes, posYI + (int) lowRes);
						screen = line.add(screen);
						line.translate(posXI, posYI + (int) lowRes, posXI
								+ (int) lowRes, posYI + (int) lowRes);
						screen = line.add(screen);
					}
				}
			}*/
		}
		return screen;
	}

	// SETTERS

	public void setBorder(Color c) {
		fill = true;
		color = c.getRGB();
		construct();
	}

	public void setBorder(boolean b) {
		fill = b;
		color = KGraphics.getFontColorInt();
		construct();
	}

	public void setVisible(boolean b) {
		visible = b;
		construct();
	}

	public void translate(int x, int y) {
		if ((x < WIDTH - width) && (x >= 0))
			posYg = x;
		if ((y < HEIGHT - height) && (y >= 0))
			posXg = y;
		construct();
	}

	public void setLineWidth(int lw) {
		int lwTemp = lw - lineWidth;
		lineWidth = lw;
		width1 = width - lwTemp;
		construct();
	}

	public void scale(int w) {
		width1 = w - lineWidth;
	}

	public void setComponents(int[][] c) {

		components = c;

		construct();
	}

	public int[][] deselect(int mPX, int mPY) {

		int mY = mPX;
		int mX = mPY;
		int des = -1;

		for (int i = 0; i < components.length; i++) {
			for (int j = 0; j < components[i].length / 2; j++) {

				int rectX = (int) (posXg + lowRes * components[i][j]);
				int rectY = (int) (posYg + lowRes * components[i][j + 1]);

				if ((mX >= rectX) && (mX <= rectX + lowRes) && (mY >= rectY)
						&& (mY <= rectY + lowRes)) {
					des = i;
				}
			}
		}
		if (des > -1) {
			
			int[][] comI;
			
			if (deComp == null) {
				comI = new int[1][];
				deComp = new int[0][];
			} else {
				comI = new int[deComp.length + 1][];
			}
		
			for (int i = 0; i < deComp.length; i++) {
				comI[i] = deComp[i];
			}
			comI[comI.length - 1] = components[des];
			deComp = comI;

			comI = new int[components.length - 1][];
			for (int i = 0; i < des; i++) {
				comI[i] = components[i];
			}
			for (int i = des; i < comI.length; i++) {
				comI[i] = components[i + 1];
			}
			components = comI;
			
			if(deselect == null){
				deselect = new int[1];
				deselect[0] = des;
			} else {
				int[] inter = new int[deselect.length+1];
				for(int i=0; i<deselect.length;i++){
					inter[i]=deselect[i];
				}
				inter[inter.length-1]=des;
				deselect = inter;
			}
		}

		return components;
	}

	public void resetDeselect() {
		deComp = new int[0][0];
		deselect = new int[0];
	}

	// GETTERS

	public int getImgWidth() {
		return width;
	}

	public int getImgHeight() {
		return width;
	}

	public int getGlobalPosX() {
		return posXg;
	}

	public int getGlobalPosY() {
		return posYg;
	}

	public int[][] getDeselectComp(){
		return deComp;
	}
	
	public int[] getDeselect(){
		return deselect;
	}
	
}
