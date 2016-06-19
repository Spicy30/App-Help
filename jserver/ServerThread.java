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
	private BufferedWriter fout;


	@SuppressWarnings("unused")
	private ServerThread(){
	}

	public ServerThread(BufferedReader in, BufferedWriter out) {
		clientin = in;
		clientout = out;
	}

	@Override
	public void run() {

		while(true) {

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
					fin = new BufferedReader(new FileReader("data"));
					String jsonstr = fin.readLine();
					JSONArray arr = new JSONArray(jsonstr);
					JSONArray retarr = new JSONArray();
					for(Object req: arr){
						if(((JSONObject)req).get("accepted").equals("F"))
							retarr.put(req);
					}
					String data = retarr.toString();
					clientout.write(data);
					clientout.newLine();
					clientout.flush();
					fin.close();

				}
				else if(usermsg.equals("2"))	// establish new request
				{
					System.out.println("Now usermsg == 2");
					JSONArray requests;
					// read data from file
					fin = new BufferedReader(new FileReader("data"));
					String data = fin.readLine();
					fin.close();
					if(data != null)
						requests = new JSONArray(data);
					else
						requests = new JSONArray();
					// read request from client
					String reqstr = clientin.readLine();
					JSONObject reqjson = new JSONObject(reqstr);
					requests.put(reqjson);
					String writeback = requests.toString();

					// update the file
					fout = new BufferedWriter(new FileWriter("data"));
					fout.write(writeback);
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
						if(ob.get("nickname").equals(nickname) && ob.get("cellphone").equals(cellphone))
						{
							ob.put("accepted", "T");
							ob.put("helpcell", helpcell);
							ob.put("helpname", helpname);
							String jsonstr = ob.toString();
							clientout.write("Successful");
							clientout.newLine();
							clientout.flush();
							requests.put(i, ob);
							changed = true;
							break;
						}
					}

					if(changed == false){
						clientout.write("Unsuccessful");
						clientout.newLine();
						clientout.flush();
					}
					if(changed == true)
					{
						fout = new BufferedWriter(new FileWriter("data"));
						String output = requests.toString();
						fout.write(output);
						fout.flush();
						fout.close();
					}

				}
				else if(usermsg.equals("4")) // check acception of a request by ( cell, name )
				{
					// read data from client
					String cellphone = clientin.readLine();
					String nickname = clientin.readLine();

					System.out.println("cellphone: " + cellphone);
					System.out.println("nickname: " + nickname);
					// read file
					fin = new BufferedReader(new FileReader("data"));
					String data = fin.readLine();
					JSONArray requests = new JSONArray(data);
					fin.close();

					// check acception
					boolean acp = false;
					boolean find = false;
					JSONObject ob = new JSONObject();
					for(int i = 0; i < requests.length(); i++)
					{
						ob = (JSONObject)requests.get(i);
						if(ob.get("cellphone").equals(cellphone)
				        	&& ob.get("nickname").equals(nickname))
						{
							if(ob.get("accepted").equals("T"))
								acp = true;
							find = true;
							break;
						}
					}

					// tell the client if the request is accepted
					if(find == false)
						ob = new JSONObject();

					if(acp == true)
					{
						System.out.println("Accepted");
						clientout.write("1\n");
					}
					else
					{
						System.out.println("NOT Accepted");
						clientout.write("2\n");
					}

					clientout.write(ob.toString());
					clientout.newLine();
					clientout.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
