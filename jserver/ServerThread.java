import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread {
	private String usermsg = "";
	private BufferedReader clientin;
	private PrintWriter clientout;
	private BufferedReader fin;
	private PrintWriter fout;

	
	@SuppressWarnings("unused")
	private ServerThread(){
	}
	
	public ServerThread(BufferedReader in) {
		clientin = in;
	}

	@Override
	public void run(){
		
		try {
			fin = new BufferedReader(new FileReader("data"));
			fout = new PrintWriter(new FileWriter("data"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				usermsg = clientin.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// handle input
			if(usermsg.equals("1"))		// send back all requests data
			{
			}
			else if(usermsg.equals("2"))	// establish new request
			{
				
			}
			else if(usermsg.equals("3"))	// accept a request
			{
			}
			else				// wtf
			{
			}
		}
	}
}
