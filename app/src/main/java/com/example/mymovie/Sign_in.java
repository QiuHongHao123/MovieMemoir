package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mymovie.networkconnection.NetworkConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Sign_in extends AppCompatActivity {

    NetworkConnection networkConnection=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        networkConnection=new NetworkConnection();
        Button b_signin = findViewById(R.id.b_signin);
        ToggleButton hide_password=findViewById(R.id.togglePwd);
        final TextView t_user_email=findViewById(R.id.user_name);
        final TextView t_user_password=findViewById(R.id.user_password);
        hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    t_user_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    t_user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        b_signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String password_hash = null;
                String user_email=t_user_email.getText().toString();
                String user_password=t_user_password.getText().toString();
                GetIdentifyTask getIdentifyTask=new GetIdentifyTask();
                try {
                    MessageDigest md = MessageDigest.getInstance("sha256");
                    md.update(user_password.getBytes());
                    byte b[] = md.digest();
                    int i;
                    StringBuffer buf = new StringBuffer("");
                    for (int offset = 0; offset < b.length; offset++) {
                        i = b[offset];
                        if (i < 0)
                            i += 256;
                        if (i < 16)
                            buf.append("0");
                        buf.append(Integer.toHexString(i));
                        password_hash=buf.toString();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                if((!user_email.isEmpty())&&(!password_hash.isEmpty())){
                    getIdentifyTask.execute(user_email,password_hash);
                }
                else{
                    AlertDialog.Builder builder  = new AlertDialog.Builder(Sign_in.this);
                    builder.setTitle("Sign in failed" ) ;
                    builder.setMessage("Empty email or password" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();

                }



            }
        });

        Button signupButton = findViewById(R.id.b_signup);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_in.this, Sign_up.class);
                startActivity(intent);
            }
        });
    }


    private class GetIdentifyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user_email = params[0];
            String user_password=params[1];
            String result=networkConnection.identify(user_email,user_password);

            return networkConnection.identify(user_email,user_password);
        }
        @Override
        protected void onPostExecute(String identify) {
            if(identify.equals("[]")){

                AlertDialog.Builder builder  = new AlertDialog.Builder(Sign_in.this);
                builder.setTitle("Sign in failed" ) ;
                builder.setMessage("Wrong email or password" ) ;
                builder.setPositiveButton("ok" ,  null );
                builder.show();
                TextView t_user_email=findViewById(R.id.user_name);
                TextView t_user_password=findViewById(R.id.user_password);
                t_user_email.setText("");
                t_user_password.setText("");
            }
            else {

                Intent intent = new Intent(Sign_in.this, HomeScreen.class);
                Bundle bundle=new Bundle();
                bundle.putString("identify", identify);
                intent.putExtras (bundle);
                startActivity(intent);
            }
        }
    }

}
