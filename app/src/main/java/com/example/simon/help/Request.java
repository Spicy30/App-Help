package com.example.simon.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class Request extends AppCompatActivity {

    private Button AcceptButton;
    private Button BackButton;

    private TextView TitleTextView;
    private TextView NicknameTextView;
    private TextView CellphoneTextView;
    private TextView ContentTextView;

    private EditText nicknameEditText;
    private EditText cellphoneEditText;

    private String title;
    private String nickname_requester;
    private String cellphone_requester;
    private String content;
    private String nickname_replier;
    private String cellphone_replier;

    private JSONObject obj = new JSONObject();

    private String ServerIP = config.server_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        getViews();
        getRequest();
        showRequest();
        processControllers();
    }

    private void getViews() {
        AcceptButton = (Button)findViewById(R.id.accept);
        BackButton = (Button)findViewById(R.id.back);

        TitleTextView = (TextView)findViewById(R.id.title_content);
        NicknameTextView = (TextView)findViewById(R.id.nickname_requester);
        CellphoneTextView = (TextView)findViewById(R.id.cellphone_requester);
        ContentTextView = (TextView)findViewById(R.id.content_content);

        nicknameEditText = (EditText)findViewById(R.id.nickname_replier);
        cellphoneEditText = (EditText)findViewById(R.id.cellphone_replier);
    }

    private void getRequest() {

        Intent data = getIntent();
        Bundle extras_request = data.getExtras();

        title = extras_request.getString("Title");
        nickname_requester = extras_request.getString("Nickname");
        cellphone_requester = extras_request.getString("Cellphone");
        content = extras_request.getString("Content");

    }

    private void showRequest() {

        TitleTextView.setText(title);
        NicknameTextView.setText(nickname_requester);
        CellphoneTextView.setText(cellphone_requester);
        ContentTextView.setText(content);
    }


    private void processControllers() {

        //add Accept button listener
        View.OnClickListener AcceptListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getEditText();

                    // checkIfFilled() : to showAlert if no information or accepted
                    if (checkIfNotAccepted() && checkIfFilled()) {

                        // TODO: send data to server
                        // tell this request was accepted (by ID?)
                        // send replier information
                        sendDataToServer();


                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        AcceptButton.setOnClickListener(AcceptListener);

        //add Back Button listener
        View.OnClickListener BackListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        };
        BackButton.setOnClickListener(BackListener);
    }

    private void getEditText() {
        nickname_replier = nicknameEditText.getText().toString();
        cellphone_replier = cellphoneEditText.getText().toString();
    }

    private boolean checkIfFilled() {

        if(nickname_replier.matches(""))
            showAlert(R.string.warning,R.string.no_nickname);
        else if(cellphone_replier.matches(""))
            showAlert(R.string.warning,R.string.no_cellphone);
        else
            return true;
        return false;
    }

    private boolean checkIfNotAccepted() {

        // TODO : check from server (maybe from all information)
        // if accepted
        //      showAlert(R.string.warning,R.string.accepted);
        //      return false;
        // else
        //      return true;

        // temp

        try {
            acceptThread atd = new acceptThread(ServerIP, 3000, nickname_replier, cellphone_replier, obj);
            atd.start();
            atd.join();

            String climsg = (String) obj.get("tf");
            System.out.println("CLIMSG: " + climsg);

            if(climsg.equals("T"))
            {
                showAlert(R.string.warning,R.string.accepted);
                return false;
            }
            else
            {
                // not accepted
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showAlert(R.string.warning,R.string.something_wrong);
        return false;
    }

    private void showAlert(int titleId, int messageId){
        new AlertDialog.Builder(Request.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void sendDataToServer() {

        // TODO
        try {
            acceptRequestThread artd = new acceptRequestThread(ServerIP, 3000, nickname_requester, cellphone_requester, nickname_replier, cellphone_replier);
            artd.start();
            artd.join();

            showAlert(R.string.notice, R.string.successful);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
