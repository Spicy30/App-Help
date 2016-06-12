import java.io.*;
import org.json.*;
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
	public void run() {
		
		try {
			fin = new BufferedReader(new FileReader("data"));
			fout = new PrintWriter(new FileWriter("data"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		while(true) {
			try {

			try {
				usermsg = clientin.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(usermsg == "1")		/* send back all requests data */
			{
				// open for reading json string
				try {
					fin = new BufferedReader(new FileReader("data"));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			
				String data = fin.readLine();
				clientout.println(data);
				fin.close();				
			}
			else if(usermsg == "2")	// establish new request
			{
				try {
					fin = new BufferedReader(new FileReader("data"));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				String data = fin.readLine();
				fin.close();
				
				JSONArray requests = new JSONArray(data);
				String reqstr = clientin.readLine();
				JSONObject reqjson = new JSONObject(reqstr);
				requests.put(reqjson);
				String writeback = requests.toString();
	
				try {
					fout = new PrintWriter(new FileWriter("data"));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				fout.println(writeback);
				fout.flush();
				fout.close();	
			}
			else if(usermsg == "3")	// accept a request ( input: ( nickname + cellphone ) of both side  )
			{
				try {
					fin = new BufferedReader(new FileReader("data"));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				String data = fin.readLine();
				JSONArray requests = new JSONArray(data);
				String cellphone = clientin.readLine();
				String nickname = clientin.readLine();
				String helpcell = clientin.readLine();
				String helpname = clientin.readLine();
				
				JSONObject ob;
				boolean changed = false;
				for(int i = 0; i < requests.length(); i++)
				{
					ob = (JSONObject)requests.get(i);
					if((String)(ob.get("nickname")) == nickname && (String)(ob.get("cellphone")) == cellphone)
					{
						ob.put("accepted", "T");
						ob.put("helpcell", helpcell);
						ob.put("helpname", helpname);
						requests.put(i, ob);
						changed = true;
						break;
					}
				}
				
				if(changed == true)
				{
					try { 
						fout = new PrintWriter(new FileWriter("data"));
					} catch (IOException e) {
						e.printStackTrace();
					}

					String output = requests.toString();
					fout.println(output);
					fout.flush();
					fout.close();
				}
				
			}
			else if(usermsg == "4") // check acception of a request by ( cell, name )
			{
				String cellphone = clientin.readLine();				
				String nickname = clientin.readLine();
				
				try {
					fin = new BufferedReader(new FileReader("data"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				String data = fin.readLine();
				JSONArray requests = new JSONArray(data);
				fin.close();				

				boolean acp = false;
				JSONObject ob;
				String helpname = "";
				String helpcell = "";
				for(int i = 0; i < requests.length(); i++)
				{
					ob = (JSONObject)requests.get(i);
					if((String)(ob.get("cellphone")) == cellphone
				        && (String)(ob.get("nickname")) == nickname 
					&& (String)(ob.get("accepted")) == "T")
					{
						acp = true;
						helpname = (String)ob.get("helpname");
						helpcell = (String)ob.get("helpcell");
						break;
					}
				}
				
				if(acp == true)
				{
					clientout.println("1");
					clientout.println(helpcell);
					clientout.println(helpname);
				}		
				else
					clientout.println("2");		// not accepted
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
