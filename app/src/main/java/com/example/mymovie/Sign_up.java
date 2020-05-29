package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovie.networkconnection.NetworkConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Sign_up extends AppCompatActivity {
    NetworkConnection networkConnection=null;
    private int year;
    private int month;
    private int day;
    private List<String> state_list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        networkConnection=new NetworkConnection();
        final TextView t_nuname=findViewById(R.id.nu_name);
        final TextView t_nusurname=findViewById(R.id.nu_surname);
        final TextView t_nuaddress=findViewById(R.id.nu_address);
        final Spinner t_nustate=findViewById(R.id.nu_state);
        final TextView t_nupostcode=findViewById(R.id.nu_postcode);
        final DatePicker t_nudob=findViewById(R.id.nu_dob);
        final TextView t_nupwd=findViewById(R.id.nu_pwd);
        final TextView t_nuemail=findViewById(R.id.nu_email);
        final RadioButton t_nuwoman=findViewById(R.id.female);
        final RadioButton t_numan=findViewById(R.id.male);
        Button b_signUp=findViewById(R.id.sign_up);
        state_list.add("New South Wales");
        state_list.add("Queensland");
        state_list.add("South Australia");
        state_list.add("Victoria");
        state_list.add("Western Australia");
        state_list.add("Tasmania");
        state_list.add("ACT");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t_nustate.setAdapter(adapter);



        Calendar calendar=Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        t_nudob.init(2000, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Sign_up.this.year=year;
                Sign_up.this.month=monthOfYear+1;
                Sign_up.this.day=dayOfMonth;
            }
        });


        b_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nuname=t_nuname.getText().toString();
                String nuaddress=t_nuaddress.getText().toString();
                String nustate=t_nustate.getSelectedItem().toString();
                String nusurname=t_nusurname.getText().toString();
                String nupostcode=t_nupostcode.getText().toString();
                String nuemail=t_nuemail.getText().toString();
                String nupwd=t_nupwd.getText().toString();
                String nudob=year+"-"+month+"-"+day;
                String nugender="";
                String password_hash="";
                if(t_nuwoman.isChecked()){nugender="woman";}
                if(t_numan.isChecked()){nugender="man";}
                try {
                    MessageDigest md = MessageDigest.getInstance("sha256");
                    md.update(nupwd.getBytes());
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
                String[] data=new String[9];
                data[0]=nuname;
                data[1]=nusurname;
                data[2]=nugender;
                data[3]=nuaddress;
                data[4]=nustate;
                data[5]=nupostcode;
                data[6]=nudob;
                data[7]=password_hash;
                data[8]=nuemail;
                Sign_upTask sign_upTask=new Sign_upTask();
                sign_upTask.execute(data);

            }
        });
    }

    private class Sign_upTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings){
            String username=strings[0];
            String surname=strings[1];
            String gender=strings[2];
            String address=strings[3];
            String state=strings[4];
            String postcode=strings[5];
            String dob_str=strings[6];
            String hash_pwd=strings[7];
            String email=strings[8];


            return networkConnection.addUser(username,surname,gender,address,state,postcode,dob_str,hash_pwd,email);
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Existed"))
            {
                AlertDialog.Builder builder  = new AlertDialog.Builder(Sign_up.this);
                builder.setTitle("Sign up failed" ) ;
                builder.setMessage("Existed email" ) ;
                builder.setPositiveButton("ok" ,  null );
                builder.show();

            }else{
                AlertDialog.Builder builder  = new AlertDialog.Builder(Sign_up.this);
                builder.setTitle("Sign up successfully" ) ;
                builder.setMessage("Welcome" ) ;
                builder.setPositiveButton("ok" ,  null );
                builder.show();
            }

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Sign_up.this, Sign_in.class);
                    startActivity(intent);
                }
            }.start();

        }
    }
}
