package slambotPackage;

import java.util.ArrayList;

public class NodeCluster extends ArrayList<Node>
{
	public boolean shouldAdd(Node n, int carWidth)
	{
		for (int i = 0; i < this.size(); i++)
		{
			Node current = this.get(i);
			if (Math.sqrt(Math.pow(current.getX()-n.getX(), 2) + Math.pow(current.getY()-n.getY(), 2)) < carWidth)
			{
				return true;
			}
		}

		return false;
	}
}