

import java.awt.Color;

public class KWrapper {

	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int width = 0;
	private int height = 0;
	private int color = KGraphics.getDefaultColorInt();
	private int colorF = KGraphics.getFontColorInt();
	private int lineWidth = KGraphics.getDefaultLineWidth();
	private String fontT = KGraphics.getDefaultFont();
	private int fontSize = KGraphics.getDefaultFontSize();
	private String str;
	private String[] line;
	private int strHeight = 15;
	private int strWidth = 0;
	private int spacing = KGraphics.getDefaultSpacing();
	private boolean fill = false;
	private boolean border = false;
	private boolean visible = true;
	private boolean wrapToLine = false;
	private boolean highlight = false;
	// Scroll
	private boolean hitTop = true;
	private boolean hitBottom = false;
	private boolean cursor = false;
	private boolean write = false;
	private int scroll = 0;
	private int scrollCalc = 0;
	private int scrollWith;
	private int lineInter = 0;

	// CONSTRUCTORS
	public KWrapper(int posX, int posY, int width, int height) {
		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
	}

	public KWrapper(String text, int posX, int posY, int width, int height) {
		posXg = posX;
		posYg = posY;
		str = text;
		this.width = width;
		this.height = height;
	}

	public KWrapper(String text, int posX, int posY, int width, int height,
			boolean b) {
		posXg = posX;
		posYg = posY;
		str = text;
		this.width = width;
		this.height = height;
		wrapToLine = b;
	}

	// METHODS

	private void construct() {

		KFont font = new KFont(fontT, fontSize);
		font.print(str);
		strHeight = font.getStringHeight() + spacing;

		if (font.getStringWidth() > width) {

			String[] words = str.split(" ");
			int it = 0;
			line = new String[words.length + 1];

			for (int i = 0; i < words.length; i++) {
				if (line[it + 1] == null) {
					line[it + 1] = words[i] + " ";
				} else {
					line[it + 1] += words[i] + " ";
				}

				font.print(line[it + 1]);

				// new line
				if (font.getStringWidth() < width) {
					line[it] = line[it + 1];
					font.print(line[it]);
				} else {
					if (wrapToLine == false) {
						try {
							it++;
							line[it + 1] = words[i] + " ";
							if (i + 1 == words.length) {
								line[it] = words[i] + " ";
								line[it + 1] = null;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							System.err
									.println("KWRAPPER: The x-dimension is too small.");
							visible = false;
						}
						if (strWidth <= font.getStringWidth())
							strWidth = font.getStringWidth();
					} else {
						boolean whi = true;
						int ii = 1;
						while (whi == true) {
							String strI = str.substring(0, str.length() - ii);
							strI += "...";
							font.print(strI);
							ii++;
							if (font.getStringWidth() < width) {
								whi = false;
								line = new String[1];
								line[0] = strI;
							}
						}
					}
				}

			}

			if (line.length > 1) {
				String[] inter = line;
				line = new String[it + 1];
				for (int i = 0; i < it + 1; i++) {

					line[i] = inter[i];
				}
			}
		} else {
			line = new String[1];
			line[0] = str;
		}
	}

	public int[] add(int[] global) {

		int[] screen = global;
		construct();

		if (visible == true) {

			if (border == true) {
				KRect rect = new KRect(posXg - lineWidth - 2, posYg - lineWidth
						- 2, width + lineWidth + 2, height + lineWidth + 4);
				rect.setLineWidth(lineWidth);
				rect.noFill(new Color(color));
				if (fill == true) {
					rect.fill(new Color(color));
				}
				screen = rect.add(screen);
			}

			KFont f = new KFont(fontT, fontSize);
			if (highlight == true) {
				f.setHighlight(true);
			}

			for (int i = scrollWith; i < line.length; i++) {
				f.print(line[i]);
				f.setFontColor(new Color(colorF));
				f.translate(posXg, posYg + strHeight * i + scroll);
				if ((strHeight * i + scroll > -2)
						&& (posYg + strHeight * i + scroll < posYg + height
								- strHeight / 2))
					screen = f.add(screen);
			}

			if (cursor == true) {
				int cur = f.getStringWidth();
				f.print("|");
				f.translate(posXg + cur, posYg + strHeight * (line.length - 1)
						+ scroll);
				f.setFontColor(new Color(color));
				if ((strHeight * (line.length - 1) + scroll > -2)
						&& (posYg + strHeight * (line.length - 1) + scroll < posYg
								+ height - strHeight / 2))
					screen = f.add(screen);
			}
		}
		return screen;
	}

	public void addActionListener() {

		if (line == null) {
			construct();
		}

		if (KMouseEvent.mouseIn(posXg, posYg, width, height)) {
			if (write == true) {
				cursor = true;
				color = KGraphics.getDefaultColor().getRGB();
			}

			hitTop = true;
			hitBottom = false;
			if (scroll < 0)
				hitTop = false;
			if (strHeight * (line.length) + scroll > height)
				hitBottom = true;

			int addScroll = KScrollEvent.getMoment();
			if ((hitBottom == true) && (addScroll < 0))
				scroll += addScroll;
			if ((hitTop == false) && (addScroll > 0))
				scroll += addScroll;

			scrollCalc = line.length - (height / strHeight) - 1;
			if ((cursor == true) && (scrollCalc > -1)
					&& (lineInter != line.length)) {

				if (lineInter < line.length)
					scroll -= strHeight;
				if (lineInter > line.length)
					scroll += strHeight;
				lineInter = line.length;
			}
		} else {
			if (write == true) {
				cursor = false;
				color = KGraphics.getFontColorInt();
			}
		}
	}

	// SETTERS

	public void fill() {
		color = KGraphics.getDefaultColorInt();
		fill = true;
		border = true;
	}

	public void noFill() {
		fill = false;
	}

	public void fill(Color c) {
		color = c.getRGB();
		fill = true;
		border = true;
	}

	public void setBorder(boolean b) {
		border = b;
	}

	public void setDimensions(int w, int h) {
		width = w;
		height = h;
	}

	public void translate(int x, int y) {
		if ((x < WIDTH - width) && (x >= 0))
			posXg = x;
		if ((y < HEIGHT - height) && (y >= 0))
			posYg = y;
	}

	public void reDraw(int x, int y, int w, int h) {
		if ((x < WIDTH - width) && (x >= 0))
			posYg = x;
		if ((y < HEIGHT - height) && (y >= 0))
			posXg = y;
		width = w;
		height = h;
	}

	public void setLineWidth(int lw) {
		lineWidth = lw;
	}

	public void setFontColor(Color c) {
		colorF = c.getRGB();
	}

	public void setFontSize(int s) {
		fontSize = s;
	}

	public void setFont(String f) {
		fontT = f;
	}

	public String setText(String text, KKeyEvent key) {

		if (str == null)
			str = text;
		if (KMouseEvent.mouseIn(posXg, posYg, width, height))
			str = key.intelligentString(text);
		return str;

	}
	
	public String setNo(String no, KKeyEvent key){
		
		if (str == null)
			str = no;
		if (KMouseEvent.mouseIn(posXg, posYg, width, height))
			str = key.intelligentNo(no);
		
		return str;
	}
	
	public void setTextToNull(){
		str=null;
	}

	public void setWrapToLine(boolean bo) {
		wrapToLine = bo;
	}

	public void setCursor(boolean b) {
		write = true;
	}

	public void setHighlight(boolean b) {
		highlight = b;
	}

	// GETTERS

	public int getMaxStringWidth() {
		return strWidth;
	}

	public int getFontHeight() {
		return strHeight;
	}

	public int getGlobalPosY() {
		return posYg;
	}

	public int getGlobalPosX() {
		return posXg;
	}

}
