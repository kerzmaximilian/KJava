

import java.awt.Color;

public class KList {
	private int posXg = 0;
	private int posYg = 0;
	private int posYl = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int width = 100;
	private int height = 100;
	private int lineWidth = 2;
	private int strHeight=0;
	private int color = KGraphics.getFontColorInt();
	private int bgcolor = KGraphics.getDefaultColorInt();
	private int borderColor = KGraphics.getFontColorInt();
	private boolean fill = false;
	private boolean visible = true;
	private KWrapper[] wrap;
	private String fontT = KGraphics.getDefaultFont();
	private int fontSize = KGraphics.getDefaultFontSize();
	private String name;
	private String[] list = new String[0];
	private int selected = -1;
	// Scroll
	private boolean hitTop = true;
	private boolean hitBottom = false;
	private boolean cursor = false;
	private int scroll = 0;
	private int scrollCalc = 0;
	private int lineInter = 0;

	// CONSTRUCTOR

	public KList(int posX, int posY, int width, int height) {
		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
	}

	public KList(String[] list, int posX, int posY, int width, int height) {
		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		this.list = list;
	}

	// FUNCTION

	private void construct() {
		if (list.length > 0) {
			KFont trial = new KFont(fontT, fontSize);
			trial.print(list[0]);
			strHeight = trial.getStringHeight();
			wrap = new KWrapper[list.length];
			for (int i = 0; i < wrap.length; i++) {
				posYl = posYg + (strHeight + KGraphics.getDefaultSpacing()) * i;
				wrap[i] = new KWrapper(list[i], posXg + 2, posYl + scroll,
						width, height);
				wrap[i].setFont(fontT);
				wrap[i].setFontSize(fontSize);
				wrap[i].setFontColor(new Color(color));
				wrap[i].setWrapToLine(true);
			}
		}
	}

	public int[] add(int[] gloabal) {

		construct();
		int[] screen = gloabal;
		if (visible == true) {
			KRect rect = new KRect(posXg - lineWidth - 2,
					posYg - lineWidth - 2, width + lineWidth + 2, height
							+ lineWidth + 2);
			rect.setLineWidth(lineWidth);
			rect.noFill(new Color(borderColor));
			if (fill == true) {
				rect.fill(new Color(borderColor));
			}
			screen = rect.add(screen);

			if ((name != null) && (posYg - strHeight > 0)) {
				KFont title = new KFont(fontT, fontSize + 1);
				title.print(name);
				title.translate(posXg - 2, posYg - strHeight - 4);
				title.setFontColor(new Color(borderColor));
				screen = title.add(screen);
			}
			if (list.length > 0) {
				for (int i = 0; i < wrap.length; i++) {
					if ((wrap[i].getGlobalPosY() >= posYg)
							&& (wrap[i].getGlobalPosY() + strHeight < posYg
									+ height)) {
						// highlight selected
						if (selected == i) {
							KRect highL = new KRect(posXg - lineWidth + 2,
									wrap[i].getGlobalPosY(), width - 4,
									strHeight);
							highL.fill(new Color(borderColor));
							screen = highL.add(screen);
							wrap[i].setFontColor(new Color(KGraphics
									.getBGColorInt()));
						}
						screen = wrap[i].add(screen);
					}
				}
			}
		}

		return screen;
	}

	public void addActionListener() {

		if(strHeight==0)
			construct();
		
		if (KMouseEvent.mouseIn(posXg, posYg, width, height)) {

			borderColor = bgcolor;
			if (list.length > 0) {
				hitTop = true;
				hitBottom = false;
				if (scroll < 0)
					hitTop = false;
				if (list.length * strHeight + scroll > height)
					hitBottom = true;

				int addScroll = KScrollEvent.getMoment();
				if ((hitBottom == true) && (addScroll < 0))
					scroll += addScroll;
				if ((hitTop == false) && (addScroll > 0))
					scroll += addScroll;

				scrollCalc = list.length - (height / strHeight * list.length)
						- 1;
				if ((cursor == true) && (scrollCalc > -1)
						&& (lineInter != list.length)) {

					if (lineInter < list.length)
						scroll -= strHeight;
					if (lineInter > list.length)
						scroll += strHeight;
					lineInter = list.length;
				}

				// deduce selected item
				if (KMouseEvent.released() == false) {
					int mPosY = KMouseEvent.mousePosY();
					for (int i = 0; i < wrap.length; i++) {
						if ((wrap[i].getGlobalPosY() <= mPosY)
								&& (wrap[i].getGlobalPosY() + strHeight >= mPosY))
							selected = i;
					}
				}
			}
		} else {
			borderColor = color;
		}

	}

	public void removeItem(int index) {

		if ((index < list.length) && (index > 0)) {
			String[] newList = new String[list.length - 1];
			for (int i = 0; i < index; i++) {
				newList[i] = list[i];
			}
			for (int i = index + 1; i < list.length; i++) {
				newList[i - 1] = list[i];
			}
			list = newList;
		}

		if ((list.length > 0) && (index == 0)) {
			String[] newList = new String[list.length - 1];
			for (int i = 0; i < newList.length; i++) {
				if (list.length > 1)
					newList[i] = list[i + 1];
			}
			if (list.length == 1) {
				visible = false;
			}
			list = newList;
		}

	}

	public void addItem(String item) {
		visible = true;
		String[] newList = new String[list.length + 1];
		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		newList[list.length] = item;
		list = newList;
	}

	// SETTERS
	public void setTitle(String name) {
		this.name = name;
	}

	public void fill() {
		color = KGraphics.getDefaultColorInt();
		fill = true;
	}

	public void noFill() {
		fill = false;
	}

	public void fill(Color c) {
		bgcolor = c.getRGB();
		fill = true;
	}

	public void noFill(Color c) {
		bgcolor = c.getRGB();
		fill = false;
	}

	public void setFontColor(Color c) {
		color = c.getRGB();
	}

	public void setFontSize(int s) {
		fontSize = s;
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
		if ((x < WIDTH - width) && (x >= 0))
			posYg = x;
		if ((y < HEIGHT - height) && (y >= 0))
			posXg = y;
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

	public void update(String[] list) {
		visible = true;
		this.list = list;
	}

	public void setScroll(int i) {
		scroll = i;
	}

	public void setSelected(int s) {
		selected = s;
	}

	// GETTERS

	public int getSelected() {
		return selected;
	}

	public int getListLength() {
		return list.length;
	}

	public int getScroll() {
		return scroll;
	}

}
