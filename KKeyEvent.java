

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KKeyEvent implements KeyListener {

	private String charTyped = "";
	private int commands;
	private boolean keyPressed = false;
	boolean keyReleased = false;

	public KKeyEvent(KDisplay display) {
		display.addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keyReleased = false;
		keyPressed = true;
		commands = e.getKeyCode();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		keyPressed = true;
		charTyped = String.valueOf(e.getKeyChar());
	}

	public String getKey() {
		keyPressed = false;
		return charTyped;
	}

	public boolean pressed() {
		return keyPressed;
	}

	public boolean released() {
		return keyReleased;
	}

	public String intelligentString(String str) {
		String current = str;
		if (keyPressed == true) {
			if (commands == 8) {
				if (current.length() > 1) {
					current = current.substring(0, current.length() - 2);
				} else {
					current = "";
				}
			}
			current += getKey();
			charTyped="";
			keyPressed = false;
		}
		return current;
	}

	public String intelligentNo(String str) {
		String current = str;
		if (keyPressed == true) {
			if (commands == 8) {
				if (current.length() > 1) {
					current = current.substring(0, current.length() - 2);
				} else {
					current = "";
				}
			}
			String ints = getKey();
			if ((ints.equals("1")) || (ints.equals("2")) || (ints.equals("3"))
					|| (ints.equals("4")) || (ints.equals("5"))
					|| (ints.equals("6")) || (ints.equals("7"))
					|| (ints.equals("8")) || (ints.equals("9"))
					|| (ints.equals("0")))
				current += getKey();
			keyPressed = false;
		}
		return current;
	}

}
