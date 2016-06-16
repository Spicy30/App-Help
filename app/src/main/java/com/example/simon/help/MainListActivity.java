package com.example.simon.help;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity {

    private ListView RequestListView;

    private ArrayList<String> requestList_title = new ArrayList<String>();

    private static Socket socket;

    private BufferedReader in;

    private PrintWriter out;

    private String ServerIP = "10.103.249.218";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        getViews();
        try {
            getRequestList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showRequestList();
        processControllers();
    }

    // menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate option menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainlist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addNewRequest();
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_home:
                backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewRequest() {

        Intent i = new Intent(MainListActivity.this,NewRequest.class);
        startActivity(i);
    }

    private void refresh() {
        ArrayList<String> tmp = new ArrayList<>(requestList_title);
        requestList_title.clear();
        int ret = 0;
        try {
            ret = getRequestList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        if(ret == 0) {
            new AlertDialog.Builder(MainListActivity.this)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.no_connection)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else if(ret == 1){
            showRequestList();
        }
    }

    private void backToHome() {

        Intent i = new Intent(MainListActivity.this,HomeActivity.class);
        startActivity(i);
    }






    private void getViews() {
        RequestListView = (ListView)findViewById(R.id.request_list);
    }

    private int getRequestList() throws IOException, JSONException {
        getAllRequestThread ct = new getAllRequestThread(ServerIP, 3000, requestList_title);
        ct.start();
        try {
            ct.join(300);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        if(ct.isAlive()) {
            return 0;
        }
        else {
            requestList_title.add("大李水餃 10顆");
            requestList_title.add("茶本味");
            requestList_title.add("吉野烤肉飯 烤肉飯");
            return 1;
        }
    }
    private void showRequestList() {
        //show list view
        int layoutId = android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, layoutId, requestList_title);
        RequestListView.setAdapter(adapter);
    }

    private void processControllers() {

        // add RequestListView listener(view)
        AdapterView.OnItemClickListener RequestListViewListener = new AdapterView.OnItemClickListener() {
            // 第一個參數是使用者操作的ListView物件
            // 第二個參數是使用者選擇的項目
            // 第三個參數是使用者選擇的項目編號，第一個是0
            // 第四個參數在這裡沒有用途
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Bundle extras = new Bundle();


                // extras.putString("ID", String.valueOf());

                Intent i = new Intent(MainListActivity.this,Request.class);
                i.putExtras(extras);
                startActivity(i);


            }

        };
        RequestListView.setOnItemClickListener(RequestListViewListener);

    }
}
