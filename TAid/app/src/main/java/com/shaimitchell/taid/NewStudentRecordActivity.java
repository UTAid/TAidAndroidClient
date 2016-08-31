package com.shaimitchell.taid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shaimitchell.taid.LocalDb.DbAdapter;
import com.shaimitchell.taid.Models.StudentModel;
import com.shaimitchell.taid.Models.TutorialModel;

public class NewStudentRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_record_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        Intent intent = getIntent();

        final String code = intent.getStringExtra("code");
//
//        final String universityId = intent.getStringExtra("university_id");
//        final String studentNumber = intent.getStringExtra("student_number");
//        final String firstName = intent.getStringExtra("first_name");
//        final String lastName = intent.getStringExtra("last_name");
//        final String email = intent.getStringExtra("email");
        LinearLayout root = (LinearLayout) findViewById(R.id.view_container);

        final EditText mEdit1 = new EditText(this);
        final EditText mEdit2 = new EditText(this);
        final EditText mEdit3 = new EditText(this);
        final EditText mEdit4 = new EditText(this);
        final EditText mEdit5 = new EditText(this);

        TextView tutTitle = (TextView)findViewById(R.id.tut_title);
        tutTitle.setText("New Student");




//        mEdit1.setText(firstName);
        mEdit1.setHint(R.string.first_name);

//        mEdit2.setText(lastName);
        mEdit2.setHint(R.string.last_name);

//        mEdit3.setText(universityId);
        mEdit3.setHint(R.string.university_id);

//        mEdit4.setText(studentNumber);
        mEdit4.setHint(R.string.student_number);

//        mEdit5.setText(email);
        mEdit5.setHint(R.string.email);

        root.addView(mEdit1);
        root.addView(mEdit2);
        root.addView(mEdit3);
        root.addView(mEdit4);
        root.addView(mEdit5);

        Button editButton = (Button) findViewById(R.id.add_student_button);
        editButton.setText(R.string.update_record);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(NewStudentRecordActivity.this,
                        MainActivity.class);

                final String firstName = mEdit1.getText().toString();
                final String lastName = mEdit2.getText().toString();
                String universityID = mEdit3.getText().toString();
                String studentNumber = mEdit4.getText().toString();
                String email = mEdit5.getText().toString();

                DbAdapter dbAdapter = new DbAdapter(context);

                StudentModel newStudent = new StudentModel("", universityID, studentNumber,
                        firstName, lastName, email);

                Cursor cursor = dbAdapter.getTutorial(code);
                TutorialModel updatedTutorial = new TutorialModel();
                newStudent.setUpdated();

                try{
                    cursor.moveToFirst();
                    updatedTutorial.setUrl(cursor.getString(0));
                    updatedTutorial.setCode(cursor.getString(1));
                    updatedTutorial.setTaIDs(cursor.getString(2));
                    updatedTutorial.setStudentIDs(cursor.getString(3)+",\""+universityID+"\"");

                    dbAdapter.addStudent(newStudent);
                    dbAdapter.addTutorial(updatedTutorial);

                    // top of stack and open back up to the main page
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mIntent);

                    Toast.makeText(context,"Record Added", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Log.d("EXCEPTION", e.toString());
//                    Toast.makeText(context,"Record NOT Added", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }

}

