

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class KFont {

	private Font font;
	private int color = KGraphics.getFontColorInt();
	private int hColor = KGraphics.getFontColorInt();
	private String fontName = KGraphics.getDefaultFont();
	private String line;
	private int posX;
	private int posY;
	private int WIDTH = KDisplay.WIDTH;
	private int size = KGraphics.getDefaultFontSize();
	private int strWidth;
	private int strHeight;
	private BufferedImage img;
	private boolean highlight = false;

	public KFont() {
	}

	public KFont(String fontName, int size) {

		this.fontName = fontName;
		this.size = size;
	}

	private void construct() {
		if (line != null) {
			img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = img.createGraphics();
			font = new Font(fontName, Font.PLAIN, size);
			g2d.setFont(font);
			FontMetrics fm = g2d.getFontMetrics();
			strWidth = fm.stringWidth(line);
			strHeight = fm.getHeight();
			g2d.dispose();
		} else {
			//System.err.println("KFONT: Assign String first.");
		}
	}

	public int[] add(int[] gloabal) {
		int[] screen = gloabal;

		if ((line == null) || (strWidth == 0))
			return screen;

		img = new BufferedImage(strWidth, strHeight,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		g2d.setColor(new Color(color));
		g2d.drawString(line, 0, fm.getAscent());
		g2d.dispose();
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer())
				.getData();

		int h = 0;
		int v = 0;
		for (int i = 0; i < pixels.length; i++) {
			if ((WIDTH * (posY + h) + posX + v < screen.length)
					&& (WIDTH * (posY + h) + posX + v >= 0))
				if (pixels[i] < 0) {
					screen[WIDTH * (posY + h) + posX + v] = color;
				}
			if ((pixels[i] >= 0) && (highlight == true))
				screen[WIDTH * (posY + h) + posX + v] = hColor;
			v++;
			if (i % strWidth == strWidth - 1) {
				h++;
				v = 0;
			}

		}

		return screen;
	}

	// SETTERS
	public void print(String line) {
		this.line = line;
		construct();
	}

	public void print(int x, int y, String line) {
		posX = x;
		posY = y;
		this.line = line;
		construct();
	}

	public void translate(int x, int y) {
		posX = x;
		posY = y;
	}

	public void setHighlight(boolean b) {
		highlight = b;
	}

	public void setFontColor(int R, int G, int B) {
		color = new Color(R, G, B).getRGB();
	}

	public void setFontColor(Color c) {
		color = c.getRGB();
	}

	public void setFontColor() {
		color = KGraphics.getFontColorInt();
	}

	// GETTERS

	public int getStringWidth() {
		return strWidth;
	}

	public int getStringHeight() {
		return strHeight;
	}

	public int getGlobalPosX() {
		return posX;
	}

	public int getGloablPosY() {
		return posY;
	}
}
