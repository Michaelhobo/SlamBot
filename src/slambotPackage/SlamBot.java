package slambotPackage;

import java.util.ArrayList;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;

public class SlamBot
{
	private TouchSensor ts;
	private UltrasonicSensor us;
	private int turnTime;

	private int carWidth;

	private Node currentPosition;
	private Node originNode;

	private int currentDirection;

	private Map map;

	private Timer myTimer;

	private int deltaT;

	public SlamBot()
	{
		turnTime = 850;
		currentDirection = 0;
		carWidth = 20;

		ts = new TouchSensor(SensorPort.S1);
		SensorPort.S1.addSensorPortListener(new PressureListener());
		us = new UltrasonicSensor(SensorPort.S2);
		us.continuous();

		currentPosition = new Node(0, 0);
		originNode = new Node(0, 0);
		map = new Map();

		deltaT = 1000;

		myTimer = new Timer(deltaT, new myTimerListener());
		myTimer.start();
	}

	public class PressureListener implements SensorPortListener
	{
		@Override
		public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue)
		{
			if(ts.isPressed())
			{
				System.out.println(us.getDistance());
				Motor.A.stop();
				Motor.B.stop();
				onBump();
			}
		}
	}

	public Node createNewNode(int distance, int direction, Node currentPosition)
	{
		Node newNode = null;
		if (direction == 0) newNode = new Node(currentPosition.getX() - distance, currentPosition.getY());
		if (direction == 1) newNode = new Node(currentPosition.getX() + distance, currentPosition.getY());
		if (direction == 2) newNode = new Node(currentPosition.getX(), currentPosition.getY() + distance);
		if (direction == 3) newNode = new Node(currentPosition.getX(), currentPosition.getY() - distance);
		return newNode;
	}
	public void addToCluster(Node n)
	{
		ArrayList<Integer> indexOfMatches = new ArrayList<Integer>();
		for (int i = 0; i < map.getNodeClusters().size(); i++)
		{	
			if (map.getNodeClusters().get(i).shouldAdd(n, carWidth)) indexOfMatches.add(i);	
		}

		if (indexOfMatches.size()==1)
		{
			map.getNodeClusters().get(indexOfMatches.get(0)).add(n);
		}
		else if (indexOfMatches.size()==0){
			NodeCluster nc = new NodeCluster();
			nc.add(n);
			map.getNodeClusters().add(nc);
		}
		else 
		{
			map.getNodeClusters().get(indexOfMatches.get(0)).add(n);
			for (int i = indexOfMatches.size()-1; i > 0; i--)
			{
				for (int j = 0; j < map.getNodeClusters().get(i).size(); j++) 
				{
					map.getNodeClusters().get(indexOfMatches.get(0)).add(map.getNodeClusters().get(indexOfMatches.get(0)).get(j));
				}
				map.getNodeClusters().remove(map.getNodeClusters().get(i));
			}
		}
	}

	public class myTimerListener implements TimerListener
	{
		@Override
		public void timedOut()
		{
			if (us.getMode() != UltrasonicSensor.MODE_OFF && us.getActualMode() != UltrasonicSensor.MODE_OFF)
			{
				monitorPosition();
				addToCluster(createNewNode(us.getDistance() + 9, currentDirection, currentPosition));
				System.out.println("Distance to Object: " + us.getDistance() + ", Current position: X: " + currentPosition.getX() + ", Y: " + currentPosition.getY());
				map.draw();
			}
		}
	}

	//Decision making starts here.

	private void onBump()
	{
		Motor.A.backward();
		Motor.B.backward();
		turn(1);
	}

	private void turn(int num) //number of 90 degree clockwise turns
	{
		us.off();
		if (num>0)
		{
			Motor.A.forward();
			Motor.B.backward();
			try {
				Thread.sleep(turnTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Motor.A.stop();
			Motor.B.stop();
			normalMovement();
		}
		else 
		{
			Motor.A.backward();
			Motor.B.forward();
			try {
				Thread.sleep(turnTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Motor.A.stop();
			Motor.B.stop();
			normalMovement();
		}
		currentDirection = (currentDirection + 1) % 4;
		us.continuous();
	}

	public void normalMovement()
	{
		Motor.A.forward();
		Motor.B.forward();
	}


	//Decision making ends here.



	//Position monitoring starts here.

	/*
	 * This method, called repeatedly, takes the velocity of the robot,
	 * and every fixed delta t, it calculates the delta distance. The
	 * currentPosition is then updated.
	 */
	public void monitorPosition()
	{
		double speed = Motor.A.getSpeed()/1000;
		double deltaD;

		deltaD = speed * deltaT;
		if (currentDirection == 0)
		{
			currentPosition.setY(currentPosition.getY() + deltaD);
		}
		else if(currentDirection == 2)
		{
			currentPosition.setY(currentPosition.getY() - deltaD);
		}
		else if(currentDirection == 1)
		{
			currentPosition.setX(currentPosition.getX() + deltaD);
		}
		else if(currentDirection == 3)
		{
			currentPosition.setX(currentPosition.getX() - deltaD);
		}
	}
	//Position monitoring ends here.
}