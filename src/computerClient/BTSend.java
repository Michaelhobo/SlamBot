package computerClient;
import java.io.DataInputStream;
import java.io.IOException;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

/**
 * This is a PC sample. It connects to the NXT, and then
 * sends an integer and waits for a reply, 100 times.
 * 
 * Compile this program with javac (not nxjc), and run it 
 * with java.
 * 
 * You need pccomm.jar and bluecove.jar on the CLASSPATH. 
 * On Linux, you will also need bluecove-gpl.jar on the CLASSPATH.
 * 
 * Run the program by:
 * 
 *   java BTSend 
 * 
 * Your NXT should be running a sample such as BTReceive or
 * SignalTest. Run the NXT program first until it is
 * waiting for a connection, and then run the PC program. 
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTSend {	
	public static void main(String[] args) {
		NXTConnector conn = new NXTConnector();
	
		conn.addLogListener(new NXTCommLogListener(){

			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: "+message);
				
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
				
			}
			
		} 
		);
		// Connect to any NXT over Bluetooth
		boolean connected = conn.connectTo("btspp://");
	
		
		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}
		
		DataInputStream dis = new DataInputStream(conn.getInputStream());
				
		while(true) {
			
			try {
				int length=dis.readInt();
				byte[] data=new byte[length];
				dis.read(data);
				String str=new String(data,"UTF-8");
				System.out.println(str);
				
//				double x = Double.parseDouble(str.substring(str.indexOf('(') + 1, str.indexOf(',')));
//				double y = Double.parseDouble(str.substring(str.indexOf(',') + 2, str.indexOf(')')));
				
			} catch (IOException ioe) {
				
			}
		}
	}
}
