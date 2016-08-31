package com.shaimitchell.taid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Activity to display a student record that has been cicked on
 */
public class StudentRecordDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        final String universityId = intent.getStringExtra("university_id");
        final String studentNumber = intent.getStringExtra("student_number");
        final String firstName = intent.getStringExtra("first_name");
        final String lastName = intent.getStringExtra("last_name");
        final String email = intent.getStringExtra("email");
        LinearLayout root = (LinearLayout) findViewById(R.id.view_container);

        TextView mTextView;
//        TextView mTextview1 = new TextView(this);
//        TextView mTextview2 = new TextView(this);
//        TextView mTextview3 = new TextView(this);
//        TextView mTextview4 = new TextView(this);
//        TextView mTextview5 = new TextView(this);
//
//        String firstNameText = "First Name: \t" + firstName;
//        String lasttNameText = "Last Name: \t" + firstName;
//        String universityIdText = "University ID: \t" + universityId;
//        String studentNumberText = "Student Number: \t" + studentNumber;
//        String emailText = "Email: \t" + email;
//
//        mTextview1.setText(firstNameText);
//        mTextview2.setText(lasttNameText);
//        mTextview3.setText(universityIdText);
//        mTextview4.setText(studentNumberText);
//        mTextview5.setText(emailText);

        String[] array = {  "First Name: \t"+firstName,
                "Last Name: \t"+lastName,
                "University ID: \t"+universityId,
                "Student Number: \t"+studentNumber,
                "Email: \t"+email};

        for(int i=0; i<array.length; i++){
            mTextView = new TextView(this);
            mTextView.setText(array[i]);
            root.addView(mTextView);
        }




    }

}
