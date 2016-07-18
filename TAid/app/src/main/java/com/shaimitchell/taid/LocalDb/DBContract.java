package com.shaimitchell.taid.LocalDb;

import android.provider.BaseColumns;

/**
 * Class to help manage the database's structure
 */
public class DbContract {

    public DbContract(){}

    public class StudentTable implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_UNIVERSITY_ID = "university_id";
        public static final String COLUMN_NAME_STUDENT_NUMBER = "student_number";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_EMAIL = "email";

    }
}
