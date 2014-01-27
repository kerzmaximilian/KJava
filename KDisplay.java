
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class KDisplay extends Canvas implements Runnable {

	public static final int WIDTH = 1200;
	public static final int HEIGHT = WIDTH / 12 * 7;
	public static final String NAME = "Dmelano Manager";

	private JFrame frame;
	private boolean run;
	// whatever is inserted into screen[] is displayed in img!!
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] screen = ((DataBufferInt) img.getRaster().getDataBuffer())
			.getData();
	
	@SuppressWarnings("unused")
	private int tickCount = 0;
	@SuppressWarnings("unused")
	private KKeyEvent KE = new KKeyEvent(this);
	@SuppressWarnings("unused")
	private KMouseEvent ME = new KMouseEvent(this);
	@SuppressWarnings("unused")
	private KScrollEvent SE = new KScrollEvent(this);

	public KDisplay() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public synchronized void start() {
		run = true;
		new Thread(this).start();
	}

	public synchronized void stop() {

		run = false;
	}

	public void run() {

		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double nsPerTick = 1000000000D / 120D;
		int ticks = 0;
		int frames = 0;
		double delta = 0;

		// sets timing for tick() and render()
		while (run) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			// set to FALSE if ticks should equal render rate
			boolean shouldRender = false;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			if (shouldRender) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " Ticks, " + frames + " Frames");
				ticks = 0;
				frames = 0;
			}
		}
	}

	// UPDATE
	public void tick() {
		tickCount++;
		background();

		sendToScreen();

	}

	// RENDER
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	// Other
	private void background() {

		for (int i = 0; i < screen.length; i++) {
			screen[i] = KGraphics.getBGColorInt();
		}
	}

	// SEND TO SCREEN

	private void sendToScreen() {

	}
}
