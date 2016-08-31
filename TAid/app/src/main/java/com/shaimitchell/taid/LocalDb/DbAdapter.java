package com.shaimitchell.taid.LocalDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shaimitchell.taid.Models.InstructorModel;
import com.shaimitchell.taid.Models.StudentModel;
import com.shaimitchell.taid.Models.TeachingAssistantModel;
import com.shaimitchell.taid.Models.TutorialModel;

/**
 * Created by shaimitchell on 16-07-16.
 */
public class DbAdapter {

    // Database fields
    private SQLiteDatabase database;
    private TAidDbHandler dbHandler;

    public DbAdapter(Context context) {
        dbHandler = TAidDbHandler.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public void addStudent(StudentModel student) throws SQLException {
        ContentValues values = new ContentValues();

        values.put(DbContract.StudentTable.COLUMN_NAME_URL, student.getUrl());
        values.put(DbContract.StudentTable.COLUMN_NAME_UNIVERSITY_ID, student.getUniversityId());
        values.put(DbContract.StudentTable.COLUMN_NAME_STUDENT_NUMBER, student.getStudentNumber());
        values.put(DbContract.StudentTable.COLUMN_NAME_FIRST_NAME, student.getFirstName());
        values.put(DbContract.StudentTable.COLUMN_NAME_LAST_NAME, student.getLastName());
        values.put(DbContract.StudentTable.COLUMN_NAME_EMAIL, student.getEmail());
        values.put(DbContract.StudentTable.COLUMN_NAME_UPDATED, student.getUpdated());

        this.open();
        database.beginTransaction();
        try {
            long newRowId;
            newRowId = database.insertWithOnConflict(DbContract.StudentTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

            if (newRowId == -1) {
                Log.d("INSERTION_ERROR", "Failure to insert row");
            }
            Log.d("INSERTION", Long.toString(newRowId));
        } catch (Exception e) {
            Log.d("INSERTION_ERROR", "Failure to insert row");
        }
        database.setTransactionSuccessful();
        database.endTransaction();

        this.close();
    }

    public Cursor getAllStudents(){
        String query = "Select * FROM " + DbContract.StudentTable.TABLE_NAME;

        this.open();
        Cursor cursor = database.rawQuery(query,null);
//        this.close();

        return cursor;
    }

    public void addInstructor(InstructorModel instructor) throws SQLException {
        ContentValues values = new ContentValues();

        values.put(DbContract.InstructorTable.COLUMN_NAME_URL, instructor.getUrl());
        values.put(DbContract.InstructorTable.COLUMN_NAME_UNIVERSITY_ID, instructor.getUniversityId());
        values.put(DbContract.InstructorTable.COLUMN_NAME_FIRST_NAME, instructor.getFirstName());
        values.put(DbContract.InstructorTable.COLUMN_NAME_LAST_NAME, instructor.getLastName());
        values.put(DbContract.InstructorTable.COLUMN_NAME_EMAIL, instructor.getEmail());
        values.put(DbContract.InstructorTable.COLUMN_NAME_UPDATED, instructor.getUpdated());

        this.open();
        database.beginTransaction();
        try {
            long newRowId;
            newRowId = database.insertWithOnConflict(DbContract.InstructorTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

            if (newRowId == -1) {
                Log.d("INSERTION_ERROR", "Failure to insert row");
            }
            Log.d("INSERTION", Long.toString(newRowId));
        } catch (Exception e) {
            Log.d("INSERTION_ERROR", "Failure to insert row");
        }
        database.setTransactionSuccessful();
        database.endTransaction();

        this.close();
    }

    public Cursor getAllInstructors(){
        String query = "Select * FROM " + DbContract.InstructorTable.TABLE_NAME;

        this.open();
        Cursor cursor = database.rawQuery(query,null);

        return cursor;
    }

    public void addTeachingAssistant(TeachingAssistantModel teachingAssistant) throws SQLException {
        ContentValues values = new ContentValues();

        values.put(DbContract.TeachingAssistantTable.COLUMN_NAME_URL, teachingAssistant.getUrl());
        values.put(DbContract.TeachingAssistantTable.COLUMN_NAME_UNIVERSITY_ID, teachingAssistant.getUniversityId());
        values.put(DbContract.TeachingAssistantTable.COLUMN_NAME_FIRST_NAME, teachingAssistant.getFirstName());
        values.put(DbContract.TeachingAssistantTable.COLUMN_NAME_LAST_NAME, teachingAssistant.getLastName());
        values.put(DbContract.TeachingAssistantTable.COLUMN_NAME_EMAIL, teachingAssistant.getEmail());
        values.put(DbContract.TeachingAssistantTable.COLUMN_NAME_UPDATED, teachingAssistant.getUpdated());

        this.open();
        database.beginTransaction();
        try {
            long newRowId;
            newRowId = database.insertWithOnConflict(DbContract.TeachingAssistantTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

            if (newRowId == -1) {
                Log.d("INSERTION_ERROR", "Failure to insert row");
            }
            Log.d("INSERTION", Long.toString(newRowId));
        } catch (Exception e) {
            Log.d("INSERTION_ERROR", "Failure to insert row");
        }
        database.setTransactionSuccessful();
        database.endTransaction();

        this.close();
    }

    public Cursor getAllTeachingAssistants(){
        String query = "Select * FROM " + DbContract.TeachingAssistantTable.TABLE_NAME;

        this.open();
        Cursor cursor = database.rawQuery(query,null);

        return cursor;
    }

    public void addTutorial(TutorialModel tutorial) throws SQLException {
        ContentValues values = new ContentValues();

        values.put(DbContract.TutorialTable.COLUMN_NAME_URL, tutorial.getUrl());
        values.put(DbContract.TutorialTable.COLUMN_NAME_CODE, tutorial.getCode());
        values.put(DbContract.TutorialTable.COLUMN_NAME_TA, tutorial.getTaIDs().toString());
        values.put(DbContract.TutorialTable.COLUMN_NAME_STUDENTS, tutorial.getStudentIDs().toString());
        values.put(DbContract.TutorialTable.COLUMN_NAME_UPDATED, tutorial.getUpdated());

        this.open();
        database.beginTransaction();
        try {
            long newRowId;
            newRowId = database.insertWithOnConflict(DbContract.TutorialTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

            if (newRowId == -1) {
                Log.d("INSERTION_ERROR", "Failure to insert row");
            }
            Log.d("INSERTION", Long.toString(newRowId));
        } catch (Exception e) {
            Log.d("INSERTION_ERROR", "Failure to insert row");
        }
        database.setTransactionSuccessful();
        database.endTransaction();

        this.close();
    }

    public Cursor getAllTutorials(){
        String query = "Select * FROM " + DbContract.TutorialTable.TABLE_NAME;

        this.open();
        Cursor cursor = database.rawQuery(query,null);

        return cursor;
    }

    public Cursor getTutorial(String code){
        String query = "SELECT * FROM "+DbContract.TutorialTable.TABLE_NAME+" WHERE "+
                DbContract.TutorialTable.COLUMN_NAME_CODE+ " = "+"\""+code+"\"";

        this.open();
        Cursor cursor = database.rawQuery(query, null);

        return cursor;


    }

    public void updateTutorial(TutorialModel tutorial){
        ContentValues values = new ContentValues();

        values.put(DbContract.TutorialTable.COLUMN_NAME_URL, tutorial.getUrl());
        values.put(DbContract.TutorialTable.COLUMN_NAME_CODE, tutorial.getCode());
        values.put(DbContract.TutorialTable.COLUMN_NAME_TA, tutorial.getTaIDs().toString());
        values.put(DbContract.TutorialTable.COLUMN_NAME_STUDENTS, tutorial.getStudentIDs().toString());
        values.put(DbContract.TutorialTable.COLUMN_NAME_UPDATED, tutorial.getUpdated());

        String whereClause = DbContract.TutorialTable.COLUMN_NAME_CODE+" = "+"\""+
                tutorial.getCode()+"\"";

        this.open();
        database.beginTransaction();
        try {
            int numRowsAffected;
            numRowsAffected = database.update(DbContract.TutorialTable.TABLE_NAME, values,whereClause, null);

            if (numRowsAffected != 1) {
                Log.d("UPDATE_ERROR", "Failure to update row");
            }
            Log.d("UPDATE", Long.toString(numRowsAffected));
        } catch (Exception e) {
            Log.d("UPDATE_ERROR", "Failure to update row");
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        this.close();


    }

    public void resetDb(){
        open();
        database.execSQL(dbHandler.DELETE_STUDENT_TABLE);
        database.execSQL(dbHandler.DELETE_INSTRUCTOR_TABLE);
        database.execSQL(dbHandler.DELETE_TEACHING_ASSISTANT_TABLE);

        database.execSQL(dbHandler.CREATE_STUDENT_TABLE);
        database.execSQL(dbHandler.CREATE_INSTRUCTOR_TABLE);
        database.execSQL(dbHandler.CREATE_TEACHING_ASSISTANT_TABLE);
        close();
    }
}
