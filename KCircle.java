

import java.awt.Color;

public class KCircle {
	private int posXg = 0;
	private int posYg = 0;
	private int WIDTH = KDisplay.WIDTH;
	private int HEIGHT = KDisplay.HEIGHT;
	private int radius = 0;
	private int angle1 = 0;
	private int angle2 = 360;
	private int aRange = 360;
	private int render = KGraphics.getDefaultCircleRender();
	private int lineWidth = KGraphics.getDefaultLineWidth();
	private int color = KGraphics.getDefaultColorInt();
	private int[] setOff;

	private boolean visible = true;

	public KCircle(int x, int y, int r) {
		radius = r;
		if ((x < WIDTH - radius) && (x - radius >= 0))
			posYg = x;
		if ((y < HEIGHT - radius) && (y - radius >= 0))
			posXg = y;

		angle1 = 0;
		angle2 = 360;
		aRange = 360 * 2;
		construct();
	}

	public KCircle(int x, int y, int r, int a1, int a2) {
		radius = r;
		if ((x < WIDTH - radius) && (x - radius >= 0))
			posYg = x;
		if ((y < HEIGHT - radius) && (y - radius >= 0))
			posXg = y;

		angle1 = a1;
		angle2 = a2;
		defaultRender();
		construct();
	}

	private void construct() {
		aRange = (int) ((int) (angle2 - angle1)*render);
		float ren = 1f / render;
		setOff = new int[aRange * lineWidth];
		for (int k = 0; k < lineWidth; k++) {
			for (int i = 0; i < aRange; i++) {
				int x = posXg
						+ (int) ((radius - k) * (float) Math.cos(MathCalc
								.getRadian(((float) (angle1 + (i * ren))))));
				int y = posYg
						+ (int) ((radius - k) * (float) Math.sin(MathCalc
								.getRadian(((float) (angle1 + (i * ren))))));

				setOff[i + (k * aRange)] = WIDTH * x + y;
			}
		}

	}

	public int[] add(int[] global) {
		int[] screen = global;

		if (visible == true) {
			for (int i = 0; i < setOff.length; i++) {
				screen[setOff[i]] = color;
			}
		}

		return screen;
	}

	// SETTERS
	public void fill() {
		color = KGraphics.getDefaultColorInt();
		lineWidth = radius;
		construct();
	}

	public void noFill() {
		lineWidth=KGraphics.getDefaultLineWidth();
	}

	public void fill(Color c) {
		color = c.getRGB();
		lineWidth = radius;
		construct();
	}

	public void noFill(Color c) {
		color=c.getRGB();
		lineWidth=KGraphics.getDefaultLineWidth();
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}

	public void setRadius(int r) {
		radius = r;
		construct();
	}

	public void setAngle(int a1, int a2) {
		angle1 = a1;
		angle2 = a2;
		aRange = (a2 - a1) * 2;
		construct();
	}

	public void translate(int x, int y) {
		if ((x < WIDTH - radius) && (x - radius >= 0))
			posYg = x;
		if ((y < HEIGHT - radius) && (y - radius >= 0))
			posXg = y;
		construct();
	}

	public void reDraw(int x, int y, int r, int a1, int a2) {
		radius = r;
		if ((x < WIDTH - radius) && (x - radius >= 0))
			posYg = x;
		if ((y < HEIGHT - radius) && (y - radius >= 0))
			posXg = y;
		angle1 = a1;
		angle2 = a2;
		aRange = (a2 - a1) * 2;

		construct();
	}

	public void setLineWidth(int lw) {
		lineWidth = lw;
	}

	private void defaultRender(){
			render=1;
		if((radius>19)&&(render<2))
			render=2;
		if((radius>49)&&(render<3))
			render=3;
		if((radius>74)&&(render<4))
			render=4;
		if(radius>150)
			render=2;
	}
	public void setRender(int r) {
		render = r;
	}
	// GETTERS

}
