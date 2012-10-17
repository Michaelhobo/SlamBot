package slambotPackage;

import java.io.IOException;
import java.util.ArrayList;

public class Map
{
	ArrayList<NodeCluster> map;
	
	public Map()
	{
		map = new ArrayList<NodeCluster>();
	}
	
	public void addNodeCluster(NodeCluster nodeCluster)
	{
		map.add(nodeCluster);
	}
	
	public ArrayList<NodeCluster> getNodeClusters()
	{
		return map;
	}
	
	public void draw()
	{
		for (int i = 0; i < map.size(); i++)
		{
			NodeCluster temp = map.get(i);
			String clusterString = "";
			for (int j = 0; j < temp.size(); j++)
			{
				Node temp1 = temp.get(j);
				clusterString += "(" + temp1.getX() + ", " + temp1.getY() + ")" + "; ";
			}
			try
			{
				Bluetooth1.write("Cluster " + i + ": " + clusterString);
			}
			catch (IOException e)
			{
			}
		}
	}
}
