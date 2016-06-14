import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketServer {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = null;
		Socket socket = null;
		BufferedReader in;
		PrintWriter out;
		Thread std;
		try {
			ss = new ServerSocket(3000);
			System.out.println("Waiting!");
			socket = ss.accept();
			System.out.println("Client connected");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		std = new ServerThread(in, out);
		std.start();
		if(std.isAlive())
			System.out.println("Thread running");
	}
}
