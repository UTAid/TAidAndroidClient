package com.shaimitchell.taid.LocalDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to handle to SQLite queries
 */
public class TAidDbHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TAid.db";

    // used for singleton pattern
    private static TAidDbHandler dbInstance;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DbContract.StudentTable.TABLE_NAME + " (" +
//                    DbContract.StudentTable. _ID + " INTEGER " + COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_STUDENT_NUMBER + TEXT_TYPE +" NOT NULL"+ COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_UNIVERSITY_ID + TEXT_TYPE + COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DbContract.StudentTable.COLUMN_NAME_UPDATED + " INTEGER " + COMMA_SEP +
                    " UNIQUE ("+ DbContract.StudentTable.COLUMN_NAME_STUDENT_NUMBER +
                    ") ON CONFLICT REPLACE );";

    public static final String DELETE_STUDENT_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.StudentTable.TABLE_NAME;

    public static final String CREATE_INSTRUCTOR_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DbContract.InstructorTable.TABLE_NAME + " (" +
//                    DbContract.InstructorTable. _ID + " INTEGER " + COMMA_SEP +
                    DbContract.InstructorTable.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    DbContract.InstructorTable.COLUMN_NAME_UNIVERSITY_ID + TEXT_TYPE + COMMA_SEP +
                    DbContract.InstructorTable.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.InstructorTable.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.InstructorTable.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DbContract.InstructorTable.COLUMN_NAME_UPDATED + " INTEGER " + COMMA_SEP +
                    " UNIQUE (" + DbContract.InstructorTable.COLUMN_NAME_UNIVERSITY_ID +
                    ") ON CONFLICT REPLACE );";

    public static final String DELETE_INSTRUCTOR_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.InstructorTable.TABLE_NAME;

    public static final String CREATE_TEACHING_ASSISTANT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DbContract.TeachingAssistantTable.TABLE_NAME + " (" +
//                    DbContract.TeachingAssistantTable. _ID + " INTEGER " + COMMA_SEP +
                    DbContract.TeachingAssistantTable.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    DbContract.TeachingAssistantTable.COLUMN_NAME_UNIVERSITY_ID + TEXT_TYPE +" NOT NULL" +COMMA_SEP +
                    DbContract.TeachingAssistantTable.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.TeachingAssistantTable.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.TeachingAssistantTable.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DbContract.TeachingAssistantTable.COLUMN_NAME_UPDATED + " INTEGER " + COMMA_SEP +
                    " UNIQUE ("+ DbContract.TeachingAssistantTable.COLUMN_NAME_UNIVERSITY_ID +
                    ") ON CONFLICT REPLACE );";

    public static final String DELETE_TEACHING_ASSISTANT_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.TeachingAssistantTable.TABLE_NAME;

    public static final String CREATE_TUTORIAL_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DbContract.TutorialTable.TABLE_NAME + " (" +
                    DbContract.TutorialTable.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    DbContract.TutorialTable.COLUMN_NAME_CODE + TEXT_TYPE +" NOT NULL"+ COMMA_SEP +
                    DbContract.TutorialTable.COLUMN_NAME_TA + TEXT_TYPE +COMMA_SEP +
                    DbContract.TutorialTable.COLUMN_NAME_STUDENTS + TEXT_TYPE + COMMA_SEP +
                    DbContract.TutorialTable.COLUMN_NAME_UPDATED + " INTEGER " + COMMA_SEP +
                    " UNIQUE ("+ DbContract.TutorialTable.COLUMN_NAME_CODE+
                    ") ON CONFLICT REPLACE );";

    public static final String DELETE_TUTORIAL_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.TutorialTable.TABLE_NAME;

    public static synchronized TAidDbHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (dbInstance == null) {
            dbInstance = new TAidDbHandler(context.getApplicationContext());
        }
        return dbInstance;
    }

    public TAidDbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_INSTRUCTOR_TABLE);
        db.execSQL(CREATE_TEACHING_ASSISTANT_TABLE);
        db.execSQL(CREATE_TUTORIAL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // add more for the other tables
        db.execSQL(DELETE_STUDENT_TABLE);
        db.execSQL(DELETE_INSTRUCTOR_TABLE);
        db.execSQL(DELETE_TEACHING_ASSISTANT_TABLE);
        db.execSQL(DELETE_TUTORIAL_TABLE);
        onCreate(db);
    }



}
