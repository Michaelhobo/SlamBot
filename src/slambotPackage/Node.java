package slambotPackage;

public class Node {

	public double x;
	public double y;
	public Node(double xCoord, double yCoord)
	{
		x = xCoord;
		y = yCoord;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setX(double newX)
	{
		x = newX;
	}
	public void setY(double newY)
	{
		y = newY;
	}
}