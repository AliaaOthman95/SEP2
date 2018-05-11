package com.example.aliaa.sep2.viewController;


        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;


        import com.example.aliaa.sep2.R;
        import com.example.aliaa.sep2.controller.Engine;
        import com.example.aliaa.sep2.model.User;

        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    Engine engine = Engine.getInstance(MainActivity.appContext);
    EditText name ;
    EditText email;
    EditText password;
    EditText creditNum;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        creditNum=(EditText) findViewById(R.id.creditCard);
        ok =(Button) findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userName = name.getText().toString();
                String userCreditCard = creditNum.getText().toString();
                Pattern peamil = Pattern.compile("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}");
                Matcher m1 = peamil.matcher(userEmail);

                Pattern pPass = Pattern.compile("[0-9]{8,10}");
                Matcher m2 = pPass.matcher(userPassword);
                if (userPassword.isEmpty()) {
                    password.setError("Enter Password");
                    return ;
                }else if (!m1.find()){
                    email.setError("email not Valid");
                    return;
                }
                if (userEmail.isEmpty()) {
                    email.setError("Enter email");
                    return ;
                }/*else if (!m1.find()){
                    email.setError("Email not Valid");
                    return;
                }*/
                if (userName.isEmpty()) {
                    name.setError("Enter Name");
                    return ;
                }
                if (userCreditCard.isEmpty()) {
                    creditNum.setError("Enter Creditcardnum");
                    return ;
                }
                User newUser = new User(name.getText().toString(),email.getText().toString(),password.getText().toString(),creditNum.getText().toString(),0);
                if (engine.addUser(newUser)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Zeft",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}

