package slambotPackage;

import lejos.nxt.Button;

public class Main
{
	public static void main(String[] args)
	{
		Bluetooth1.connect();
		System.out.println("Connecting");
		SlamBot slammy = new SlamBot();
		slammy.normalMovement();
		//slammy.monitorPosition();
		Button.waitForAnyPress();
	}
}