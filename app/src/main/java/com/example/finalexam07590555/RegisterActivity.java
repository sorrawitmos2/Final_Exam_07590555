package com.example.finalexam07590555;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalexam07590555.db.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase accountDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        dbHelper = new DatabaseHelper(RegisterActivity.this);
        accountDB = dbHelper.getWritableDatabase();

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullnameEditText = findViewById(R.id.full_name_edit_text);
                String fullname = fullnameEditText.getText().toString();

                EditText usernameEditText = findViewById(R.id.username_edit_text);
                String username = usernameEditText.getText().toString();

                EditText passwordEditText = findViewById(R.id.password_edit_text);
                String password = passwordEditText.getText().toString();

                if(fullname.length() > 0 && username.length() > 0 && password.length() > 0){
                    ContentValues cv = new ContentValues();
                    cv.put("fullname",fullname);
                    cv.put("username",username);
                    cv.put("password",password);

                    accountDB.insert("account",null,cv);
                    Toast.makeText(RegisterActivity.this,/*String*/"Register successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this,/*String*/"All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
