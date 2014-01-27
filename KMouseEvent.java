

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KMouseEvent implements MouseListener {


	private static int WIDTH = KDisplay.WIDTH;
	
	private static int mouseX=0;
	private static int mouseY=0;
	
	private static boolean released=true;
	private static boolean pressed=false;
	
	
	public KMouseEvent(KDisplay display) {
		display.addMouseListener( this);
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getX()+" "+e.getY());
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getX()+" "+e.getY());
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
		released=false;
		pressed = true;
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
		released=true;
		pressed = false;
	}
	
	public static int mousePos(){
		int posG = WIDTH*mouseY+mouseX;
		return posG;
	}
	
	public static int mousePosX(){
		return mouseX;
	}
	
	public static int mousePosY(){
		return mouseY;
	}
	
	public static boolean mouseIn(int posX, int posY, int width, int height){
		boolean in=false;
		if((mouseX>posX)&&(mouseX<posX+width)&&(mouseY>posY)&&(mouseY<posY+height)){
			in=true;
		}
		return in;
		
	}
	
	public static boolean released(){
		return released;
	}
	
	public static boolean pressed(){
		return pressed;
	}

}
