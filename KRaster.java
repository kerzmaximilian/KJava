

import java.awt.Color;

public class KRaster {

	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int width = 100;
	private int height = 100;
	private float scale = 10f;
	private int space = 30;
	private int lineWidth = 1;
	private int color = KGraphics.getFontColorInt();
	private int bgcolor = KGraphics.getBGColorInt();
	private int scolor = KGraphics.getDefaultColorInt();
	private String fontT = KGraphics.getDefaultFont();
	private int fontSize = KGraphics.getDefaultFontSize();
	private int borderColor = KGraphics.getFontColorInt();
	private String[] raster;
	private String[] flies;
	private String[] flyTitle;
	private KImage[] images;
	private boolean border = false;
	private boolean itemBorder = true;
	private boolean visible = true;
	private int displayType;
	private int perLine;
	private int imgWidth;

	private KGraph[] graph;
	private int graphLength;
	// Scroll
	private int posYI = 0;
	private int posXI = 0;
	private int xAdjustPos = 0;
	private int nextLine = 0;
	private boolean hitTop = true;
	private boolean hitBottom = false;
	private boolean cursor = false;
	private int scroll = 0;
	private int scrollCalc = 0;
	private int lineInter = 0;
	// selected
	private int mouseX = 0;
	private int mouseY = 0;
	private int highlight = -1;
	private int[][] positions;

	// CONSTRUCTORS
	public KRaster(int posX, int posY, int width, int height) {

		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		displayType = 0;
		this.raster = new String[0];
		this.positions = new int[raster.length + 1][2];

	}

	public KRaster(String[] raster, int posX, int posY, int width, int height) {

		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		displayType = 0;
		this.raster = raster;
		this.positions = new int[raster.length + 1][2];

		construct();
	}

	public KRaster(String[] raster, int posX, int posY, int width, int height,
			float scale, int type) {

		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		this.scale = scale;
		displayType = type;
		this.raster = raster;
		this.positions = new int[raster.length + 1][2];

		construct();
	}

	public KRaster(KImage[] images, int posX, int posY, int width, int height) {

		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		displayType = 1;
		this.images = images;
		this.raster = new String[images.length];
		this.positions = new int[raster.length + 1][2];
		this.scale = images[0].getImgWidth() / 10;
		this.imgWidth = images[0].getImgWidth();
		construct();
	}

	public KRaster(KGraph[] graph, int posX, int posY, int width, int height) {

		posXg = posX;
		posYg = posY;
		this.width = width;
		this.height = height;
		displayType = 2;
		this.graph = graph;
		this.raster = new String[graph.length];
		this.positions = new int[raster.length + 1][2];
		this.scale = graph[0].getXAxis() / 10;
		this.graphLength = graph[0].getYAxis();
		construct();
	}

	// FUNCTIONS

	private void construct() {

		perLine = (int) (width / (10 * scale + space));
		if (perLine == 0) {
			perLine = 1;
		}
	}

	public int[] add(int[] global) {
		int[] screen = global;

		nextLine = 0;
		xAdjustPos = 0;
		if (visible == true) {

			if ((border == true) && (posXg - 10 >= 0) && (posYg - 10 >= 0)) {
				KRect rect = new KRect(posXg - 10, posYg - 10, width + 20,
						height + 20);
				rect.noFill(new Color(borderColor));
				rect.setLineWidth(lineWidth);
				screen = rect.add(screen);
			}
			if (raster != null) {
				for (int j = 0; j < raster.length + 1; j++) {

					// dynamic X and Y positions
					posXI = posXg + (((int) (10 * scale)) + space) * j
							- xAdjustPos;
					posYI = posYg + (((int) (10 * scale)) + space) * nextLine
							+ scroll;
					if ((graph != null) && (j < graph.length)) {
						posYI = posYg + (graphLength + space + 70) * (nextLine)
								+ scroll;
					}
					positions[j][0] = posXI;
					positions[j][1] = posYI;

					if ((displayType == 0) && (j < raster.length)) {
						// Folder (Project)
						if ((posYI - ((int) scale) < height)
								&& (posYI - ((int) scale * 9) >= 0)) {
							KRect rect1 = new KRect(posXI, posYI,
									(int) (3 * scale), (int) (8 * scale));
							rect1.fill(new Color(color));
							if (highlight == j)
								rect1.fill(new Color(scolor));
							screen = rect1.add(screen);
							KRect rect2 = new KRect(posXI, posYI + 10,
									(int) (10 * scale), (int) (8f * scale));
							rect2.fill(new Color(color));
							if (highlight == j)
								rect2.fill(new Color(scolor));
							screen = rect2.add(screen);
							// labels
							KWrapper wrap = new KWrapper(raster[j], posXI,
									posYI - 4 + (int) (10 * scale),
									(int) (10 * scale), 20, true);
							wrap.setFont(fontT);
							wrap.setFontSize(fontSize);
							wrap.setFontColor(new Color(color));
							if (highlight == j)
								wrap.setFontColor(new Color(scolor));
							wrap.setWrapToLine(true);
							screen = wrap.add(screen);

						}

						// caption
						if ((flies != null) && (j < flies.length)
								&& (posYI - (int) scale < height)
								&& (posYI - ((int) scale * 9) >= 0)) {

							KFont font = new KFont(fontT, 20);
							font.print(flies[j]);
							font.translate(posXI + (int) (9 * scale)-font.getStringWidth(), posYI + 5
									+ (int) (scale));
							font.setFontColor(new Color(bgcolor));
							screen = font.add(screen);

						}

					}

					if ((displayType == 1) && (j < raster.length)) {
						// DISPAYING IMAGES
						if (images == null) {
							if ((posYI - ((int) scale) < height)
									&& (posYI - ((int) scale * 9) >= 0)) {
								KRect rect1 = new KRect(posXI, posYI,
										(int) (10 * scale), (int) (10 * scale));
								if (highlight == j)
									rect1.fill(new Color(scolor));
								screen = rect1.add(screen);
							}
						} else {
							if ((posYI - ((int) scale) < height)
									&& (posYI - ((int) scale * 10) >= 0)) {
								images[j].translate(posXI, posYI);
								if (itemBorder == true)
									images[j].setBorder(new Color(color));
								if (highlight == j)
									images[j].setBorder(new Color(scolor));
								screen = images[j].add(screen);
							}

							// caption
							if ((flyTitle != null) && (j < flyTitle.length)
									&& (posYI - (int) scale < height)
									&& (posYI - ((int) scale * 9) >= 0)) {

								KWrapper wrap = new KWrapper(flyTitle[j],
										posXI, posYI, (int) (10 * scale) - 10,
										20, true);
								wrap.setFont(fontT);
								wrap.setFontSize(fontSize);
								wrap.setFontColor(new Color(scolor));
								wrap.setHighlight(true);
								wrap.setWrapToLine(true);
								screen = wrap.add(screen);

							}
						}
					}

					if ((displayType == 0) && (j == raster.length)) {
						// Add Folder (Project)

						if ((posYI - ((int) scale) < height)
								&& (posYI - ((int) scale * 9) >= 0)) {
							KRect rect1 = new KRect(posXI, posYI,
									(int) (3 * scale), (int) (8 * scale));
							rect1.fill(new Color(color));
							if (highlight == j)
								rect1.fill(new Color(scolor));
							screen = rect1.add(screen);
							KRect rect2 = new KRect(posXI, posYI + 10,
									(int) (10 * scale), (int) (8f * scale));
							rect2.fill(new Color(color));
							if (highlight == j)
								rect2.fill(new Color(scolor));
							screen = rect2.add(screen);
							KLine line1 = new KLine(posXI + ((int) (5 * scale))
									- 5, posYI + (int) (scale * 3), posXI
									+ ((int) (5 * scale)) - 5, posYI
									+ (int) (scale * 7));
							line1.fill(new Color(bgcolor));
							line1.setLineWidth(10);
							screen = line1.add(screen);
							KLine line2 = new KLine(
									posXI + ((int) (3 * scale)), posYI
											+ (int) (scale * 5) - 5, posXI
											+ ((int) (7 * scale)), posYI
											+ (int) (scale * 5) - 5);
							line2.fill(new Color(bgcolor));
							line2.setLineWidth(10);
							screen = line2.add(screen);
						}

					}

					if ((displayType == 1) && (j == raster.length)) {
						// Add IMAGE (Project)
						if ((posYI - ((int) scale) < height)
								&& (posYI - ((int) scale * 9) >= 0)) {
							KRect rect1 = new KRect(posXI, posYI, imgWidth,
									imgWidth);
							rect1.noFill(new Color(color));
							if (highlight == j)
								rect1.fill(new Color(scolor));
							screen = rect1.add(screen);
							KLine line1 = new KLine(posXI + ((int) (5 * scale))
									- 5, posYI + (int) (scale * 3), posXI
									+ ((int) (5 * scale)) - 5, posYI
									+ (int) (scale * 7));
							line1.fill(new Color(color));
							line1.setLineWidth(10);
							screen = line1.add(screen);
							KLine line2 = new KLine(
									posXI + ((int) (3 * scale)), posYI
											+ (int) (scale * 5) - 5, posXI
											+ ((int) (7 * scale)), posYI
											+ (int) (scale * 5) - 5);
							line2.fill(new Color(color));
							line2.setLineWidth(10);
							screen = line2.add(screen);
						}
					}

					if ((displayType == 2) && (j < raster.length)) {

						if (perLine == 1)
							posXI = posXg;

						graph[j].translate(posYI, posXI);

						if ((posYI > posYg - 5)
								&& (posYI + graph[j].getYAxis() < height))
							screen = graph[j].add(screen);

						if ((j == 0) && (perLine == 1))
							nextLine++;
					}

					if (j != 0) {
						if (perLine - 1 == j % perLine) {
							nextLine++;
							xAdjustPos += perLine * (int) (10 * scale)
									+ perLine * space;

						}
					}
				}
			}
		}
		return screen;
	}

	public void addActionListener() {

		if (KMouseEvent.mouseIn(posXg, posYg, width, height)) {

			borderColor = scolor;

			if (raster.length > 0) {
				hitTop = true;
				hitBottom = false;
				if (scroll < 0)
					hitTop = false;
				if ((graph == null)
						&& ((raster.length / perLine + 1)
								* ((int) scale * 10 + space) + scroll > height))
					hitBottom = true;
				if ((graph != null)
						&& (30+(graph.length/perLine+1) * (graph[0].getYAxis() + space+70) + scroll > height))
					hitBottom = true;

				int addScroll = KScrollEvent.getMoment();
				if ((hitBottom == true) && (addScroll < 0))
					scroll += addScroll;
				if ((hitTop == false) && (addScroll > 0))
					scroll += addScroll;

				scrollCalc = raster.length
						- (height / ((int) scale * 10) + space) - 1;
				if ((cursor == true) && (scrollCalc > -1)
						&& (lineInter != raster.length)) {

					if (lineInter < raster.length)
						scroll -= ((int) scale * 10) + space;
					if (lineInter > raster.length)
						scroll += ((int) scale * 10) + space;
					lineInter = raster.length;
				}
			}

			if (KMouseEvent.released() == false) {
				// find selected
				mouseX = KMouseEvent.mousePosX();
				mouseY = KMouseEvent.mousePosY();
				for (int i = 0; i < positions.length; i++) {
					if ((mouseX >= positions[i][0])
							&& (mouseX <= positions[i][0] + (int) scale * 10)
							&& (mouseY >= positions[i][1])
							&& (mouseY <= positions[i][1] * (int) scale * 10))
						highlight = i;
				}

			}

		} else {
			borderColor = color;
		}

	}

	// SETTERS

	public void setVisible(boolean b) {
		visible = b;
		construct();
	}

	public void setDimensions(int w, int h) {
		width = w;
		height = h;
		construct();
	}

	public void setBorder(boolean b) {
		border = b;
	}

	public void setItemBorder(boolean b) {
		itemBorder = b;
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

	public void setInfo(String[] info) {
		flies = info;
		construct();
	}

	public void setCaption(String[] fly) {
		flies = fly;
		construct();
	}

	public void setSpacing(int i) {
		space = i;
		construct();
	}

	public void highlight(int b) {
		highlight = b;
	}

	public void setFlyTitles(String[] ts) {
		flyTitle = ts;
		construct();
	}

	public void setFiles(String[] files) {
		raster = files;
		this.positions = new int[raster.length + 1][2];
		construct();
	}

	public void setImages(KImage[] images) {
		this.images = images;
		this.raster = new String[images.length];
		this.displayType = 1;
		this.positions = new int[raster.length + 1][2];
		this.scale = images[0].getImgWidth() / 10;
		this.imgWidth = images[0].getImgWidth();

		construct();
	}

	public void reset() {

		displayType = 0;
		this.images = null;
		this.raster = new String[0];
		this.positions = new int[raster.length + 1][2];

		construct();

	}
	
	public void setScroll(int scroll){
		this.scroll=scroll;
	}

	// GETTERS

	public int getSelected() {
		int ret = -1;
		if (raster != null) {
			if (highlight < raster.length)
				ret = highlight;
		}
		return ret;
	}

	public boolean addNew() {
		boolean ret = false;
		if (raster != null) {
			if (highlight == raster.length)
				ret = true;
		}
		return ret;
	}
	
	public int getScroll(){
		return scroll;
	}

}
