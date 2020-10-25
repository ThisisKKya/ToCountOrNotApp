package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.toaccountornot.utils.Accounts;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
                List<Accounts> list = LitePal.findAll(Accounts.class);
                for (Accounts account:list) {
                    Log.d("DatabaseActivity","first" + account.getFirst());
                    Log.d("DatabaseActivity","second" + account.getSecond());
                    Log.d("DatabaseActivity","id" + account.getId());
                    Log.d("DatabaseActivity","price" + account.getPrice());
//                    Log.d("DatabaseActivity","time" + account.getTime());
                    Log.d("DatabaseActivity","member" + account.getMember());
                    Log.d("DatabaseActivity","card" + account.getCard());
                }
            }
        });
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accounts account = new Accounts();
                account.setFirst("饮食");
                account.setSecond("晚餐");
                account.setPrice(30.12);
                Date d1 = new Date(2000-1900,9-1,28);
//                account.setTime(d1);
                account.save();
            }
        });
        List<Accounts> list = LitePal.findAll(Accounts.class);
        for (Accounts account:list) {
            Log.d("DatabaseActivity","first" + account.getFirst());
            Log.d("DatabaseActivity","second" + account.getSecond());
            Log.d("DatabaseActivity","id" + account.getId());
            Log.d("DatabaseActivity","price" + account.getPrice());
//                    Log.d("DatabaseActivity","time" + account.getTime());
            Log.d("DatabaseActivity","member" + account.getMember());
            Log.d("DatabaseActivity","card" + account.getCard());
        }
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Accounts> list = LitePal.findAll(Accounts.class);
                for (Accounts account:list) {
                    Log.d("DatabaseActivity","first" + account.getFirst());
                    Log.d("DatabaseActivity","second" + account.getSecond());
                    Log.d("DatabaseActivity","id" + account.getId());
                    Log.d("DatabaseActivity","price" + account.getPrice());
//                    Log.d("DatabaseActivity","time" + account.getTime());
                    Log.d("DatabaseActivity","member" + account.getMember());
                    Log.d("DatabaseActivity","card" + account.getCard());
                }
            }
        });
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Accounts.class,"id == ?","2");
            }
        });
    }
}
