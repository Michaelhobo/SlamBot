package slambotPackage;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Bluetooth1
{
	static BTConnection btc;
	static DataOutputStream dos;

	public static void connect(){
		btc = Bluetooth.waitForConnection();
		dos = btc.openDataOutputStream();
	}

	public static void write(String s) throws IOException{
		byte[] data = s.getBytes("UTF-8");
		dos.writeInt(data.length);
		dos.write(data);
		dos.flush();
	}

	public static void close() throws IOException{
		dos.close();
	}
}