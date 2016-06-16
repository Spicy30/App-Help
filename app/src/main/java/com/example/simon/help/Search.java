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

public class Search extends AppCompatActivity {

    private TextView TitleTextView;
    private TextView NicknameRequesterTextView;
    private TextView CellphoneRequesterTextView;
    private TextView ContentTextView;
    private TextView NicknameReplierTextView;
    private TextView CellphoneReplierTextView;

    String title;
    String nickname_requester;
    String cellphone_requester;
    String content;
    String nickname_replier;
    String cellphone_replier;


    String nickname_for_search;
    String cellphone_for_search;




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
                showDialog(1);
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
            case 1:
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
            case 1:
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

        getDataFromServer();  // via nickname_for_search , cellphone_for_search
        showRequest();

    }

    private void backToHome() {

        Intent i = new Intent(Search.this,HomeActivity.class);
        startActivity(i);
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

    }

    private void showRequest() {


        TitleTextView.setText(title);
        NicknameRequesterTextView.setText(nickname_requester);
        CellphoneRequesterTextView.setText(cellphone_requester);
        ContentTextView.setText(content);

        NicknameReplierTextView.setText(nickname_replier);
        CellphoneReplierTextView.setText(cellphone_replier);

        // temp
        NicknameReplierTextView.setText(nickname_for_search);
        CellphoneReplierTextView.setText(cellphone_for_search);
    }
}
