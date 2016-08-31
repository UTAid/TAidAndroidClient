package com.shaimitchell.taid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shaimitchell.taid.LocalDb.DbAdapter;

import java.util.Objects;

/**
 * Activity to display the class list from the tutorial that was most recently clicked on
 */
public class TutorialRecordDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_record_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        final String stuIds = intent.getStringExtra("stuIds");
        final String taIDs = intent.getStringExtra("taIDs");
        final String code = intent.getStringExtra("code");
        LinearLayout root = (LinearLayout) findViewById(R.id.view_container);
        TextView tutTitle = (TextView)findViewById(R.id.tut_title);
        DbAdapter dbAdapter = new DbAdapter(this);
        int count;

        String[] stuIdArray = stuIds.replace("\"", "").split(",");

        for (int i = 0; i<stuIdArray.length; i++) {
            Log.d("SHIT", stuIdArray[i]);
        }

        TextView mTextView;
        String record;

        tutTitle.setText(code+"-"+tutTitle.getText());

        Cursor mCursor = dbAdapter.getAllStudents();

        if (mCursor.moveToFirst()){
            try{
//                Log.d("balls1", mCursor.getString(2));
                count = stuIdArray.length;
                while(!mCursor.isAfterLast()){
//                    Log.d("balls2", mCursor.getString(2));
                    if (count > 0) {
                        for (int i = 0; i < stuIdArray.length; i++) {
                            Log.d("balls3", stuIdArray[i]);
                            Log.d("balls4", mCursor.getString(2));
                            Log.d("balls5", String.valueOf(Objects.equals(mCursor.getString(2), stuIdArray[i])));
                            if (Objects.equals(mCursor.getString(2), stuIdArray[i])) {
                                Log.d("balls4", mCursor.getString(2));
                                mTextView = new TextView(this);
                                record = "Name:\t" + mCursor.getString(5) + ", " + mCursor.getString(4) + "\n" +
                                        "Student Number:\t" + mCursor.getString(2);
                                mTextView.setText(record);
                                root.addView(mTextView);
                                count --;
                            }
                        }
                        mCursor.moveToNext();
                    }else{
                        break;
                    }
                }
            }catch(Exception e){
                Log.d("TEST", e.toString());
            }
        }

        Button button = (Button)findViewById(R.id.add_student_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(TutorialRecordDisplayActivity.this,
                        NewStudentRecordActivity.class);

                mIntent.putExtra("code", code);
                startActivity(mIntent);
            }
        });
//        String[] array = {  "Code: \t"+code,
//                "stuIDs: \t"+stuIds,
//                "taIDs: \t"+taIDs};
//
//        for(int i=0; i<array.length; i++){
//            mTextView = new TextView(this);
//            mTextView.setText(array[i]);
//            root.addView(mTextView);
//        }
    }

}
