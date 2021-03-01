package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class SingleShot implements Runnable {
	private ServerSocket server;
	private ExecutorService executor;
	private ConcurrentHashMap <Socket, Streams> dialog;
	
	public SingleShot(ServerSocket server, ExecutorService executor, ConcurrentHashMap<Socket, Streams> dialog) {
		if (server == null || executor == null || dialog == null) throw new IllegalArgumentException();
		this.server = server;
		this.executor = executor;
		this.dialog = dialog;
	}

	@Override
	public void run() {
		try {
			Socket socket = server.accept();
			if (dialog.putIfAbsent(socket, new Streams(socket)) == null){
				System.out.println("Connected to: " + socket.getRemoteSocketAddress());
			}
			executor.submit(new SingleShot(server, executor, dialog));
		}
		catch (Exception e) { executor.submit(new SingleShot(server, executor, dialog)); }
	}

}
