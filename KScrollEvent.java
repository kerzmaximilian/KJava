

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class KScrollEvent implements MouseWheelListener {

	private static int moment=0;
	private static boolean wheel=false;
	
	public KScrollEvent(KDisplay display) {
		display.addMouseWheelListener(this);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		wheel=true;
		moment = e.getWheelRotation();
	}
	
	private static int turns(){
		wheel=false;
		return moment;
	}
	
	public static int getMoment(){
		int turn = 0;
		if(wheel==true)
			turn=turns();
		return turn;
	}

}
