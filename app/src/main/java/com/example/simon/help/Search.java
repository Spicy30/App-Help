package com.example.simon.help;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

public class Search extends AppCompatActivity {

    private TextView TitleTextView;
    private TextView NicknameRequesterTextView;
    private TextView CellphoneRequesterTextView;
    private TextView ContentTextView;
    private TextView NicknameReplierTextView;
    private TextView CellphoneReplierTextView;

    private String title;
    private String nickname_requester;
    private String cellphone_requester;
    private String content;
    private String nickname_replier;
    private String cellphone_replier;


    private String nickname_for_search;
    private String cellphone_for_search;

    private static final int DIALOG_SEARCH=0;
    private JSONObject obj = new JSONObject();
    private String ServerIP = config.server_ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getViews();
    }

    // menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate option menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                showDialog(DIALOG_SEARCH);
                return true;
            case R.id.action_home:
                backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // dialog


    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog dialogDetails = null;

        switch (id) {
            case DIALOG_SEARCH:
                LayoutInflater inflater = LayoutInflater.from(this);
                View dialogview = inflater.inflate(R.layout.dialog_search, null);
                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(Search.this);
                dialogbuilder.setTitle(R.string.search);
                dialogbuilder.setView(dialogview);
                dialogDetails = dialogbuilder.create();
                break;
        }
        return dialogDetails;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALOG_SEARCH:
                final AlertDialog searchDialog = (AlertDialog) dialog;
                final Button OKButton = (Button) searchDialog.findViewById(R.id.ok);
                final Button CancelButton = (Button) searchDialog.findViewById(R.id.cancel);
                final EditText NicknameEditText = (EditText) searchDialog.findViewById(R.id.search_nickname);
                final EditText CellphoneEditText = (EditText) searchDialog.findViewById(R.id.search_cellphone);

                View.OnClickListener OKListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nickname_for_search = NicknameEditText.getText().toString();
                        cellphone_for_search = CellphoneEditText.getText().toString();

                        if(checkIfFilled()) {
                            searchDialog.dismiss();
                            search();
                        }
                    }
                };
                OKButton.setOnClickListener(OKListener);

                View.OnClickListener CancelListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        searchDialog.dismiss();
                    }
                };
                CancelButton.setOnClickListener(CancelListener);

                break;
        }
    }


    private void search() {

        clearRequestView();
        getDataFromServer();  // via nickname_for_search , cellphone_for_search
        showRequest();

    }

    private void backToHome() {

        finish();
    }



    private boolean checkIfFilled() {

        if(nickname_for_search.matches(""))
            showAlert(R.string.warning,R.string.no_nickname);
        else if(cellphone_for_search.matches(""))
            showAlert(R.string.warning,R.string.no_cellphone);
        else
            return true;
        return false;
    }


    private void showAlert(int titleId, int messageId){
        new AlertDialog.Builder(Search.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void getViews() {

        TitleTextView = (TextView)findViewById(R.id.title_content);
        NicknameRequesterTextView = (TextView)findViewById(R.id.nickname_requester);
        CellphoneRequesterTextView = (TextView)findViewById(R.id.cellphone_requester);
        ContentTextView = (TextView)findViewById(R.id.content_content);

        NicknameReplierTextView = (TextView)findViewById(R.id.nickname_replier);
        CellphoneReplierTextView = (TextView)findViewById(R.id.cellphone_replier);


    }


    private void getDataFromServer() {

        // TODO
        try {
            acceptThread atd = new acceptThread(ServerIP, 3000, nickname_for_search, cellphone_for_search, obj);
            atd.start();
            atd.join();

            String climsg = (String) obj.get("tf");
            System.out.println("CLIMSG: " + climsg);

            if(climsg.equals("T"))
            {
                title = (String)obj.get("title");
                nickname_requester = (String)obj.get("nickname");
                cellphone_requester = (String)obj.get("cellphone");
                content = (String)obj.get("body");
                nickname_replier = (String)obj.get("helpname");
                cellphone_replier = (String)obj.get("helpcell");
            }
            else if(climsg.equals("F"))
            {
                // not accepted
                title = (String)obj.get("title");
                nickname_requester = (String)obj.get("nickname");
                cellphone_requester = (String)obj.get("cellphone");
                content = (String)obj.get("body");
                nickname_replier = "None";
                cellphone_replier = "None";
            }
            else if(climsg.equals("N"))
            {
                // not found, clear all fields
                clearRequestView();
                showAlert(R.string.notice, R.string.not_found);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRequest() {

        TitleTextView.setText(title);
        NicknameRequesterTextView.setText(nickname_requester);
        CellphoneRequesterTextView.setText(cellphone_requester);
        ContentTextView.setText(content);

        NicknameReplierTextView.setText(nickname_replier);
        CellphoneReplierTextView.setText(cellphone_replier);
    }

    private void clearRequestView() {

        title = "None";
        nickname_requester = "None";
        cellphone_requester = "None";
        content = "None";
        nickname_replier = "None";
        cellphone_replier = "None";
    }
}
