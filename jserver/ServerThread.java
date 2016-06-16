import java.io.*;
import org.json.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread {
	private String usermsg = null;
	private BufferedReader clientin;
	private BufferedWriter clientout;
	private BufferedReader fin;
	private PrintWriter fout;


	@SuppressWarnings("unused")
	private ServerThread(){
	}

	public ServerThread(BufferedReader in, BufferedWriter out) {
		clientin = in;
		clientout = out;
	}

	@Override
	public void run() {

		/*try {
			fin = new BufferedReader(new FileReader("data"));
			fout = new PrintWriter(new FileWriter("data"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/


		while(true) {
			/*try {
				Thread.sleep(500);
			}
			catch(Exception e){
				e.printStackTrace();
			}*/
			try {
				usermsg = clientin.readLine();
				if(usermsg != null){
					System.out.println(usermsg);
				}
				else{
					continue;
				}
				if(usermsg.equals("1"))		/* send back all requests data */
				{
					System.out.println("Now got inside usermsg == 1");
					// open for reading json string
					//fin = new BufferedReader(new FileReader("data"));
					//String data = fin.readLine();
					JSONArray arr = new JSONArray();
					JSONObject obj = new JSONObject();
					obj.put("title", "測試範例");
					arr.put(obj);
					obj = new JSONObject();
					obj.put("title", "範例2");
					arr.put(obj);
					clientout.write(arr.toString());
					clientout.newLine();
					clientout.flush();
					//fin.close();

				}
				else if(usermsg.equals("2"))	// establish new request
				{
					// read data from file
					fin = new BufferedReader(new FileReader("data"));
					String data = fin.readLine();
					fin.close();
					JSONArray requests = new JSONArray(data);

					// read request from client
					String reqstr = clientin.readLine();
					JSONObject reqjson = new JSONObject(reqstr);
					requests.put(reqjson);
					String writeback = requests.toString();

					// update the file
					fout = new PrintWriter(new FileWriter("data"));
					fout.println(writeback);
					fout.flush();
					fout.close();
				}
				else if(usermsg.equals("3"))	// accept a request ( input: ( nickname + cellphone ) of both side  )
				{
					// read from file
					fin = new BufferedReader(new FileReader("data"));
					String data = fin.readLine();
					fin.close();
					JSONArray requests = new JSONArray(data);

					// read request from client
					String cellphone = clientin.readLine();
					String nickname = clientin.readLine();
					String helpcell = clientin.readLine();
					String helpname = clientin.readLine();

					// find match and accept
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
						fout = new PrintWriter(new FileWriter("data"));
						String output = requests.toString();
						fout.println(output);
						fout.flush();
						fout.close();
					}

				}
				else if(usermsg.equals("4")) // check acception of a request by ( cell, name )
				{
					// read data from client
					String cellphone = clientin.readLine();
					String nickname = clientin.readLine();

					// read file
					fin = new BufferedReader(new FileReader("data"));
					String data = fin.readLine();
					JSONArray requests = new JSONArray(data);
					fin.close();

					// check acception
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

					// tell the client if the request is accepted
					if(acp == true)
					{
						clientout.write("1");
						clientout.write(helpcell);
						clientout.write(helpname);
					}
					else
						clientout.write("2");		// not accepted
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
