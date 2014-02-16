

import java.awt.Color;

public class KGraphics {

	private static Color default_ = new Color(247, 200, 7);
	private static Color bg_default = new Color(100, 100, 100);
	private static Color font_default = new Color(245, 245, 245);
	private static Color highlight_default = new Color(247, 220, 17);
	private static Color[] defaults_ = { new Color(247, 200, 7),
			new Color(100, 102, 219), new Color(206, 102, 219),
			new Color(83, 109, 206), new Color(206, 102, 219),
			new Color(52, 33, 100), new Color(102, 219, 83),
			new Color(109, 247, 200), new Color(100, 200, 7),
			new Color(100, 200, 219), new Color(206, 102, 119),
			new Color(183, 109, 206), new Color(206, 202, 219),
			new Color(52, 33, 200), new Color(2, 219, 83),
			new Color(109, 147, 200), new Color(247, 200, 207),
			new Color(0, 102, 219), new Color(206, 2, 219),
			new Color(83, 109, 6), new Color(26, 152, 219),
			new Color(52, 133, 200), new Color(12, 219, 183),
			new Color(139, 207, 220), };
	private static int render_default = 2;
	private static int spacing_default = 4;
	private static int fontSize_default = 14;
	private static String fontType_default = "Arial";
	private static int imgWidth_default = 512;
	private static int lowResRatio = imgWidth_default / KDisplay.LOWRES;

	private static int lineWidth = 2;
	private static int pointWidth = 1;
	private static boolean KButtonFill = false;

	public KGraphics() {

	}

	// SETTERS SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
	public void setDefaultColor(int R, int G, int B) {
		default_ = new Color(R, G, B);
	}

	public void setBGColor(int R, int G, int B) {
		bg_default = new Color(R, G, B);
	}

	public void setFontColor(int R, int G, int B) {
		font_default = new Color(R, G, B);
	}

	public void setKButtonFill(boolean f) {
		KButtonFill = f;
	}

	public void setDefaultLineWidth(int a) {
		lineWidth = a;
	}

	public void setDefaultPointWidth(int p) {
		pointWidth = p;
	}

	public void setDefaultCircleRender(int r) {
		render_default = r;
	}

	public void setDefaultFont(String font) {
		fontType_default = font;
	}

	public void setHighlighColor(int R, int G, int B) {
		highlight_default = new Color(R, G, B);
	}

	public void setDefaultFontSize(int s) {
		fontSize_default = s;
	}

	public void setDefaultSpacing(int s) {
		spacing_default = s;
	}

	public void setDefaultImgWidth(int i) {
		imgWidth_default = i;
	}

	// GETTERS GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
	public static Color getDefaultColor() {
		return default_;
	}

	public Color getBGColor() {
		return bg_default;
	}

	public static int getDefaultColorInt() {
		return default_.getRGB();
	}

	public static int getBGColorInt() {
		return bg_default.getRGB();
	}

	public static int getFontColorInt() {
		return font_default.getRGB();
	}

	public static String getDefaultFont() {
		return fontType_default;
	}

	public static int getDefaultFontSize() {
		return fontSize_default;
	}

	public static boolean getKButtonFill() {
		return KButtonFill;
	}

	public static int getDefaultLineWidth() {
		return lineWidth;
	}

	public static int getDefaultPointWidth() {
		return pointWidth;
	}

	public static int getDefaultCircleRender() {
		return render_default;
	}

	public static int getHighlightColor() {
		return highlight_default.getRGB();
	}

	public static int getDefaultSpacing() {
		return spacing_default;
	}

	public static int getDefaultImgWidth() {
		return imgWidth_default;
	}

	public static int getLowResImgRatio() {
		return lowResRatio;
	}

	public static int[] getDeaultColors() {
		int[] colors = new int[defaults_.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = defaults_[i].getRGB();
		}
		return colors;
	}
	
	public static Color[] getDefaultColorsC(){
		return defaults_;
	}
}
