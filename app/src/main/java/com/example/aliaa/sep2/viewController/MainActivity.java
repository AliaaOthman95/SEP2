package com.example.aliaa.sep2.viewController;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aliaa.sep2.controller.Engine;
import com.example.aliaa.sep2.model.SQLiteDBHelper;
import  com.example.aliaa.sep2.R;


public class MainActivity extends AppCompatActivity {
    SQLiteDBHelper database;
    EditText email;
    EditText password;
    Button login;
    Button signUp;
    public static Context appContext;
    Engine engine ;
   // LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = this.getBaseContext();
        engine = Engine.getInstance(appContext);

       // presenter = new LoginPresenter(this,engine);

        // fields
        email = (EditText)findViewById(R.id.email);
        password =(EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signUp =(Button) findViewById(R.id.signUp);
      //  errorMessageView = (TextView) findViewById(R.id.errorMessage);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = email.getText().toString();
                String userpassword = password.getText().toString();
                if (userpassword.isEmpty()) {
                    password.setError("Enter Password");
                    return ;
                }
                if (userEmail.isEmpty()) {
                    email.setError("Enter email");
                    return ;
                }
                if(!engine.findUser(email.getText().toString(), password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Sign Up First",
                            Toast.LENGTH_LONG).show();
                } else{
                    Intent intent = new Intent(getApplicationContext(), BookShelf.class);
                    startActivity(intent);
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }

        public String getEmail(){
            return email.getText().toString();
        }
        public String getPassword(){
            return password.getText().toString();
        }
        public void showUsernameError (){
            password.setError("error");
        }
}
