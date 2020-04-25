package frusdev.com.cbot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.StringTokenizer;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static frusdev.com.cbot.DatabaseHelper.TABLE_NAME;


public class chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    private DBManager dbManager;
    Cursor cursor,cursor2;
    private SimpleCursorAdapter adapter;
    private DatabaseHelper dbHelper;
    static String ans="i dont have answer for it!";
    static String que;
    static List<String> quelist;
    // private static String ab;
    SQLiteDatabase db;
    String data;
    public int count=0,cnt=0;
    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.question, DatabaseHelper.answer };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        dbHelper=new DatabaseHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        deleteData();
        insertQuestionAnswers();


        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    cursor2 = dbManager.getData(String.valueOf(messageArea.getText().toString()));
//                    String messageText = messageArea.getText().toString();
//                    addMessageBox("You:\n" + messageText.toString(), 1);
//                    StringTokenizer st1 = new StringTokenizer(messageText);
//                    while (st1.hasMoreTokens()) {
//                        String data = st1.nextToken();
//                        while (cursor2.moveToNext()) {
//                            String key = cursor2.getString(cursor2.getColumnIndex("keyword"));
//                            if (key == data) {
//                                Toast.makeText(chat.this, "keyword matches", Toast.LENGTH_LONG).show();
//
//                                break;
//                            }
//                            else
//                            {
//                                Toast.makeText(chat.this, "no keyword matches", Toast.LENGTH_LONG).show();
//
//                                break;
//                            }
//                        }
//                    }
////                cursor = dbManager.getData(String.valueOf(messageArea.getText().toString()));
////                while (cursor.moveToNext()) {
////                    int index;
////                    index = cursor.getColumnIndexOrThrow("answer");
////                    ans = cursor.getString(index);
////                }
////                addMessageBox("Bot: \n " + ans, 2);
////                ans="i dont have answer for it!";
////
////                quelist=new ArrayList<>();
////
////
////
////                messageArea.setText("");
//                }
//                catch(Exception e)
//                {
//                    Log.d("msg error",""+e);
//                }
//            }
//        });
//


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        int xy=0;
                try {
                    String messageText = messageArea.getText().toString();
                    String[] words = messageText.split("\\s+");
                    xy=words.length;
                    addMessageBox("You:\n" + messageText.toString(), 1);
                    StringTokenizer st1 = new StringTokenizer(messageText);
                    while (st1.hasMoreTokens()) {
                        data = st1.nextToken();
                        cnt=getAnswers(data);
                        messageArea.setText("");


                    }
                }
                catch (Exception e)
                {
                    Log.d("ERROR",""+e);
                }
                if(cnt==xy)
                {
                        Log.d("Answer", "No answer found!");
                        addMessageBox(""+ans, 2);
                    messageArea.setText("");
                    count=0;

                }

            }
        });
    }


    public int getAnswers(String keys)
    {
        try
        {
            Cursor keywordsmatch = db.rawQuery("select * from ibot where keyword = ?", new String[]{keys});

            if (keywordsmatch != null&&keywordsmatch.moveToFirst()) {

                Log.d("Answer", "" + keywordsmatch.getString(2));
                addMessageBox(keywordsmatch.getString(2), 2);
                
            } else {
                count++;
//                Log.d("Answer", "No answer found!");
//              addMessageBox(""+ans, 2);
            }
            keywordsmatch.close();
        }
        catch (Exception e) {
            Log.d("ERROR",""+e);
//            addMessageBox(""+ans, 2);

        }
        return count;
    }


    public void deleteData()
    {
        try
        {
            dbManager = new DBManager(this);
            dbManager.open();

            dbManager.deleteData();
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }



    @Override
    public void onBackPressed() {
        // super.onBackPressed();
       // Toast.makeText(chat.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }



    public void insertQuestionAnswers()
    {


            try {
                dbManager = new DBManager(this);
                dbManager.open();
                dbManager.insert("Hello", "Hi IBot here!", "hello");
                dbManager.insert("Hi", "Hi IBot here!", "hi");
                dbManager.insert("What are the courses offered here?", "Courses offered for Bachelor of Engineering are as follows:\n1.Computer Engineering\n2.Mechanical Engineering\n3.Mechanical Sandwich Engineering\n4.Civil Engineering", "courses");
                dbManager.insert("Which companies visit for placements here?", "Major companies that visit us are-\n1. Nvidia\n2.GS Labs\n3.Schlumberger\n4.Tata Technologies\n5.L&T Infotech\n6.Vodafone\n7.Quickheal\n8.Vanderlande", "companies");
                dbManager.insert("What package is offered after placement?", "Well, if you are asking about average package then that was of 4 lacs p.a. and highest package was of 12 lacs p.a. last year.", "package");
                dbManager.insert("What is the fee structure for first year?", "For First Year Engineering, fees is Rs,106347 and for DSE, fees is Rs, 106297.", "fee");
                dbManager.insert("Which documents are needed at the time of admission?", "Documents required while taking admissions are:\n1.10th Standard/CBSE/Equivalent Exam Mark sheet\n2.12th Standard/CBSE/Equivalent Exam Mark sheet\n3.Leaving Certificate/Transfer Certificate\n4.Proof of Indian nationality/Domicile Certificate\n5.Caste Certificate(if applicable)\n6.Caste Validity(if applicable)\n7.Non creamy Certificate","documents");
                dbManager.insert("How can I contact your institute?", "You can call us at 02114 661 500 or 02114 661 666.","contact");
                dbManager.insert("First year facilities", "Facilities for first year include:\n1.Physics Lab\n2.Chemistry Lab\n3.Workshop\n4.Basic Civil Lab\n5.Basic Mechanical Lab\n6.Basic Electrical Lab", "first year");
                dbManager.insert("Computer department facilities", "Key features of Computer Science Engineering are:\n1.Smart classrooms\n2.Seminar hall\n3.Digital and Microprocessor Lab\n4.PG Research Lab\n5.Multimedia and Graphics Lab\n6.Mobile and Cloud Computing Lab", "computer");
                dbManager.insert("Civil department facilities", "Key features of Civil Engineering are:\n1.Geotechnical Lab\n2.Surveying Lab\n3.Geology Lab\n4.Engineering Mechanics Lab\n5.Computer Lab ", "civil");
                dbManager.insert("Mechanical department facilities", "Key features of Mechanical Engineering are:\n1.Fluid Mechanics and Machinery\n2.CAD lab\n3.Modelling Analysis Lab\n4.Mechatronics\n5.Industrial Fluid Power", "mechanical");
                dbManager.insert("College Infrastructure", "Our Infrastructure is inclusive of\n1.Well Equiped Library\n2.Wifi Campus\n3.Good and Hygenic Canteen Facilities\n4.Hostel Accommodation For Boys and Girls\n5.Sports and Gym facilities\n6.Efficient Transportation", "infrastructure");
            }

        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}