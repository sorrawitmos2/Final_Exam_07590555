package com.example.finalexam07590555;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.example.finalexam07590555.db.DatabaseHelper;
import com.example.finalexam07590555.db.account;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<account> mAccount = new ArrayList<>();
    DatabaseHelper dbHelper;
    SQLiteDatabase accountDB;
    Cursor mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(MainActivity.this);
        accountDB = dbHelper.getWritableDatabase();

        mCursor = accountDB.rawQuery("SELECT " + DatabaseHelper.COL_FULLNAME+","+DatabaseHelper.COL_USERNAME+", "+DatabaseHelper.COL_PASSWORD+" FROM " + DatabaseHelper.TABLE_ACCOUNT, null);

        mCursor.moveToFirst();
        account ac ;

        while(!mCursor.isAfterLast()){
            ac = new account(0,mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_FULLNAME)),
                    mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_USERNAME)),
                    mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_PASSWORD)));
            mAccount.add(ac);
            mCursor.moveToNext();
        }
        //LOG
        Log.v("list","###LIST_USERNAME###");
        for(account c :mAccount){
            Log.v("USERNAME", c.username);
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.username_edit_text);
                String username = usernameEditText.getText().toString();

                EditText passwordEditText = findViewById(R.id.password_edit_text);
                String password = passwordEditText.getText().toString();

                if(username.length() > 0 && password.length() > 0) {
                    boolean check = false;
                    for (account ac : mAccount) {
                        if (username.equals(ac.username) && password.equals(ac.password)) {
                            Toast.makeText(MainActivity.this,/*String*/"Welcome " + ac.fullname, Toast.LENGTH_SHORT).show();
                            check = true;
                            break;
                        }
                    }
                    if(check == false){
                        Toast.makeText(MainActivity.this,"Invalid username or password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this,"All fields are required", Toast.LENGTH_SHORT).show();

                }

            }
        });


        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
