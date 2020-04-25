package frusdev.com.cbot;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    // Creating EditText.
    EditText nametxt,contacttxt ;
    SharedPreferences prf;
    // Creating button;
    Button Register;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String NameHolder, ContactHolder ;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl ="https://rewkett.000webhostapp.com/insert.php";


    public Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=(Button)findViewById(R.id.button1);
        contacttxt=findViewById(R.id.contact);
        nametxt=findViewById(R.id.name);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Date currentTime = Calendar.getInstance().getTime();

                    NameHolder = nametxt.getText().toString();
                    ContactHolder = contacttxt.getText().toString();

                    if (NameHolder.isEmpty() && ContactHolder.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter all details to proceed further.", Toast.LENGTH_LONG).show();
                    } else {

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", NameHolder); // Storing string
                        editor.putString("contactno", ContactHolder); // Storing string
                        editor.putBoolean("status", true); // Storing boolean
                        editor.commit(); // commit changes


                        prf = getSharedPreferences("MyPref", 0);

                        UserRegistration(NameHolder, ContactHolder, currentTime.toString());
                        Intent t = new Intent(MainActivity.this, chat.class);
                        startActivity(t);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ooops! No WiFi/Mobile Networks Connected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    @Override
    public void onBackPressed() {
        // super.onBackPressed();
      //  Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }


    public void UserRegistration(final String username, final String contactno, final String createdon){

        // Showing progress dialog at user registration time.
//        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
//        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
//                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
//                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error",""+volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("User_name", username);
                params.put("User_contact", contactno);
                params.put("Created_on", createdon);

                Log.i("Info","user name: "+NameHolder.toString()+"contact: "+ContactHolder.toString());

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }



}
