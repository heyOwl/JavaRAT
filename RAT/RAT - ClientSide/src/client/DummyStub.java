package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class DummyStub {
	private static final String IP = "192.168.1.5";
	private static final int PORT = 3055;
	public static void main(String[] args) {
		try {
			Socket s = new Socket(IP,PORT);
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true) {
				String sel = reader.readLine();
				switch(sel) {
					case "PING":	writer.println(s.getRemoteSocketAddress());
									writer.flush();
									break;
					case "CLOSE":	s.close();
									return;
					case "PCNAME":	InetAddress addr = InetAddress.getLocalHost();
									writer.println(addr.getHostName());
									writer.flush();
									break;
					case "OS":		writer.println(System.getProperty("os.name"));
									writer.flush();
									break;
					case "IP":		writer.println(s.getRemoteSocketAddress());
									writer.flush();
									break;
					default:		break;
				}
			}
		}
		catch (Exception e) {}
	}

}
