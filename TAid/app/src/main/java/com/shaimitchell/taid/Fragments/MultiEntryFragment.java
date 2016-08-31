package com.shaimitchell.taid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shaimitchell.taid.Enums.FRAG_TYPE;
import com.shaimitchell.taid.LocalDb.DbAdapter;
import com.shaimitchell.taid.Models.InstructorModel;
import com.shaimitchell.taid.Models.StudentModel;
import com.shaimitchell.taid.Models.TeachingAssistantModel;
import com.shaimitchell.taid.Models.TutorialModel;
import com.shaimitchell.taid.R;
import com.shaimitchell.taid.StudentRecordDisplayActivity;
import com.shaimitchell.taid.TutorialRecordDisplayActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment used to display data from the database when there are supposed to be multiple entries
 */
public class MultiEntryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ENTRY = "ENTRY";
    private static final String FRAG_TYPE = "FRAG_TYPE";

    private String dbEntry;
    private String fragTitle;
    private FRAG_TYPE fragmentType;
    private OnFragmentInteractionListener mListener;
    private Cursor mCursor;

    public MultiEntryFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param entry - the entries to be displayed
     * @param fragType - the type of data that is to be
     */
    public static MultiEntryFragment newInstance(String entry, FRAG_TYPE fragType,
                                                 @Nullable Cursor cursor) {

        MultiEntryFragment fragment = new MultiEntryFragment();
        Bundle args = new Bundle();
        String fType;
        args.putString(ENTRY, entry);

        switch(fragType){
            case STUDENT:
                fType = "STUDENTS";
                break;
            case INSTRUCTOR:
                fType = "INSTRUCTORS";
                break;
            case TEACHING_ASSISTANT:
                fType = "TEACHING ASSISTANTS";
                break;
            case TUTORIAL:
                fType = "TUTORIALS";
                break;
            case PRACTICAL:
                fType = "PRACTICALS";
                break;
            default:
                fType = "BLAH";
                break;
        }
        args.putString(FRAG_TYPE, fType);
        fragment.setArguments(args);
        fragment.fragmentType = fragType;
        if (cursor != null){
            fragment.mCursor = cursor;
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbEntry = getArguments().getString(ENTRY);
            fragTitle = getArguments().getString(FRAG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        TextView mTextView;

        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_multi_entry, container, false);
        LinearLayout root = (LinearLayout) mView.findViewById(R.id.multi_entry_fragment);
        TextView fTitle = (TextView)root.findViewById(R.id.fragment_title);
        fTitle.setText(fragTitle);
        switch (fragmentType){
            case STUDENT:
                setupStudentResponse(root);
                break;
            case INSTRUCTOR:
                setupInstructorResponse(root);
                break;
            case TEACHING_ASSISTANT:
                setupTAResponse(root);
                break;
            case TUTORIAL:
                setupTutorialResponse(root);
                break;
            case PRACTICAL:
                setupPracticalResponse(root);
                break;
            default:
                break;
        }
        return mView;
    }

    public void onInteractionChanged(boolean bool) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bool);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean bool);
    }

    /**
     * sets up the fragment to be display student data
     * @param root - the root layout of the fragment
     */
    private void setupStudentResponse(LinearLayout root){

        String entry;
        StudentModel student;

        if (mCursor == null) {

            try {
                JSONObject jsonResponse = new JSONObject(dbEntry);
                JSONArray resultsResponse = jsonResponse.optJSONArray("results");

                for (int i = 0; i < resultsResponse.length(); i++) {
                    JSONObject jsonData = resultsResponse.getJSONObject(i);
                    final String url = jsonData.getString("url");
                    final String universityId = jsonData.getString("university_id");
                    final String studentNumber = jsonData.getString("student_number");
                    final String firstName = jsonData.getString("first_name");
                    final String lastName = jsonData.getString("last_name");
                    final String email = jsonData.getString("email");
                    student = new StudentModel(url, universityId, studentNumber, firstName, lastName,email);

                    entry = student.getLastName() +", "+ student.getFirstName()+"\n"+
                            R.string.student_number+": "+student.getStudentNumber()+"\n";
//                    entry = "url: \t" + student.getUrl() + "\n" +
//                            "university_id: \t" + student.getUniversityId() + "\n" +
//                            "student_number: \t" + student.getStudentNumber() + "\n" +
//                            "first_name: \t" + student.getFirstName() + "\n" +
//                            "last_name: \t" + student.getLastName() + "\n" +
//                            "email: \t" + student.getEmail() + "\n";

                    DbAdapter dbAdapter = new DbAdapter(getContext());
                    dbAdapter.addStudent(student);
                    TextView mTextView = new TextView(getContext());
                    mTextView.setText(entry);
                    root.addView(mTextView);

                    // This click listener allows each TextView that is created to start a new
                    // intent and  and display more info on the record that has been clicked on
                    mTextView.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Intent mIntent = new Intent(getActivity(), StudentRecordDisplayActivity.class);
                            mIntent.putExtra("url", url);
                            mIntent.putExtra("university_id", universityId);
                            mIntent.putExtra("student_number", studentNumber);
                            mIntent.putExtra("first_name", firstName);
                            mIntent.putExtra("last_name", lastName);
                            mIntent.putExtra("email", email);
                            getActivity().startActivity(mIntent);
                        }
                    });
                }
            } catch (JSONException e) {
                Log.d("JsonException", e.toString());
            }
        }else{
            if(mCursor.moveToFirst()) {
                try {
                    while (!mCursor.isAfterLast()){
                        final String url = mCursor.getString(1);
                        final String universityId = mCursor.getString(2);
                        final String studentNumber = mCursor.getString(3);
                        final String firstName = mCursor.getString(4);
                        final String lastName = mCursor.getString(5);
                        final String email = mCursor.getString(6);

                        entry = mCursor.getString(5) +", " + mCursor.getString(4) + "\n" +

                                "student_number: \t" + mCursor.getString(3) + "\n";

                        TextView mTextView = new TextView(getContext());
                        mTextView.setText(entry);
                        root.addView(mTextView);
                        mTextView.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                TextView textView = (TextView)v;
                                Intent mIntent = new Intent(getActivity(), StudentRecordDisplayActivity.class);
                                mIntent.putExtra("url", url);
                                mIntent.putExtra("university_id", universityId);
                                mIntent.putExtra("student_number", studentNumber);
                                mIntent.putExtra("first_name", firstName);
                                mIntent.putExtra("last_name", lastName);
                                mIntent.putExtra("email", email);
                                getActivity().startActivity(mIntent);
                            }
                        });
                        mCursor.moveToNext();
                    }
                }catch (Exception e){
                    Log.d("ERROR", e.toString());
                }
            }
        }
    }

    /**
     * sets up the fragment to be display instructor data
     * @param root - the root layout of the fragment
     */
    private void setupInstructorResponse(LinearLayout root){

        String entry;
        InstructorModel instructor;

        if (mCursor == null) {

            try {
                JSONObject jsonResponse = new JSONObject(dbEntry);
                JSONArray resultsResponse = jsonResponse.optJSONArray("results");

                for (int i = 0; i < resultsResponse.length(); i++) {
                    JSONObject jsonData = resultsResponse.getJSONObject(i);
                    final String url = jsonData.getString("url");
                    final String universityId = jsonData.getString("university_id");
                    final String firstName = jsonData.getString("first_name");
                    final String lastName = jsonData.getString("last_name");
                    final String email = jsonData.getString("email");
                    instructor = new InstructorModel(url, universityId, firstName, lastName,email);

                    entry = "url: \t" + instructor.getUrl() + "\n" +
                            "university_id: \t" + instructor.getUniversityId() + "\n" +
                            "first_name: \t" + instructor.getFirstName() + "\n" +
                            "last_name: \t" + instructor.getLastName() + "\n" +
                            "email: \t" + instructor.getEmail() + "\n";

                    DbAdapter dbAdapter = new DbAdapter(getContext());
                    dbAdapter.addInstructor(instructor);
                    TextView mTextView = new TextView(getContext());
                    mTextView.setText(entry);
                    mTextView.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            TextView textView = (TextView)v;
                            Intent mIntent = new Intent(getActivity(), StudentRecordDisplayActivity.class);
                            mIntent.putExtra("url", url);
                            mIntent.putExtra("university_id", universityId);
                            mIntent.putExtra("first_name", firstName);
                            mIntent.putExtra("last_name", lastName);
                            mIntent.putExtra("email", email);
                            getActivity().startActivity(mIntent);
                        }
                    });
                    root.addView(mTextView);
                }
            } catch (JSONException e) {
                Log.d("JsonException", e.toString());
            }
        }else{
            if(mCursor.moveToFirst()) {
                try {
                    while (!mCursor.isAfterLast()) {
                        final String url = mCursor.getString(1);
                        final String universityId = mCursor.getString(2);
                        final String firstName = mCursor.getString(3);
                        final String lastName = mCursor.getString(4);
                        final String email = mCursor.getString(5);
                        entry = mCursor.getString(3) + " " + mCursor.getString(4);

                        TextView mTextView = new TextView(getContext());
                        mTextView.setText(entry);
                        mTextView.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                Intent mIntent = new Intent(getActivity(), StudentRecordDisplayActivity.class);
                                mIntent.putExtra("url", url);
                                mIntent.putExtra("university_id", universityId);
                                mIntent.putExtra("first_name", firstName);
                                mIntent.putExtra("last_name", lastName);
                                mIntent.putExtra("email", email);
                                getActivity().startActivity(mIntent);
                            }
                        });
                        root.addView(mTextView);
                        mCursor.moveToNext();
                    }
                }catch (Exception e){
                    Log.d("ERROR", e.toString());
                }
                mCursor.close();
            }
        }
    }

    /**
     * sets up the fragment to be display teaching assistant data
     * @param root - the root layout of the fragment
     */
    private void setupTAResponse(LinearLayout root){

        String entry;
        TeachingAssistantModel teachingAssistant;

        if (mCursor == null) {

            try {
                JSONObject jsonResponse = new JSONObject(dbEntry);
                JSONArray resultsResponse = jsonResponse.optJSONArray("results");

                for (int i = 0; i < resultsResponse.length(); i++) {
                    JSONObject jsonData = resultsResponse.getJSONObject(i);
                    teachingAssistant = new TeachingAssistantModel(jsonData.getString("url"),
                            jsonData.getString("university_id"),
                            jsonData.getString("first_name"),
                            jsonData.getString("last_name"),
                            jsonData.getString("email"));

                    entry = "url: \t" + teachingAssistant.getUrl() + "\n" +
                            "university_id: \t" + teachingAssistant.getUniversityId() + "\n" +
                            "first_name: \t" + teachingAssistant.getFirstName() + "\n" +
                            "last_name: \t" + teachingAssistant.getLastName() + "\n" +
                            "email: \t" + teachingAssistant.getEmail() + "\n";

                    DbAdapter dbAdapter = new DbAdapter(getContext());
                    dbAdapter.addTeachingAssistant(teachingAssistant);
                    TextView mTextView = new TextView(getContext());
                    mTextView.setText(entry);
                    root.addView(mTextView);
                }
            } catch (JSONException e) {
                Log.d("JsonException", e.toString());
            }
        }else{
            if(mCursor.moveToFirst()) {
                ArrayList<String> test = new ArrayList<>();
                try {
                    while (!mCursor.isAfterLast()) {
                        entry = mCursor.getString(3) + " " + mCursor.getString(4);

                        /**TextView mTextView = new TextView(getContext());
                        mTextView.setText(entry);
                        root.addView(mTextView);**/
                        mCursor.moveToNext();
                    }
                }catch (Exception e){
                    Log.d("ERROR", e.toString());
                }
                mCursor.close();
            }
        }
    }

    /**
     * sets up the fragment to be display practical data
     * @param root - the root layout of the fragment
     */
    private void setupPracticalResponse(LinearLayout root){

    }

    /**
     * sets up the fragment to be display tutorial data
     * @param root - the root layout of the fragment
     */
    private void setupTutorialResponse(LinearLayout root){
        String entry;
        TutorialModel tutorial;

        if (mCursor == null) {

            try {
                JSONObject jsonResponse = new JSONObject(dbEntry);
                JSONArray resultsResponse = jsonResponse.optJSONArray("results");

                for (int i = 0; i < resultsResponse.length(); i++) {
                    JSONObject jsonData = resultsResponse.getJSONObject(i);
                    String studentData = jsonData.getString("students");
                    String taData = jsonData.getString("ta");

                    // get the student ids and ta ids into separate arrays
                    String students = studentData.replace("[", "").replace("]", "");
                    String tas = taData.replace("[", "").replace("]", "");

                    tutorial = new TutorialModel(jsonData.getString("url"),
                            jsonData.getString("code"), tas, students);

                    final String url = tutorial.getUrl();
                    final String code = tutorial.getCode();
                    final String stuIDs = tutorial.getStudentIDs();
                    final String taIDs = tutorial.getTaIDs();
                    entry = "Code: \t" + code + "\n"+
                            "Student IDs: "+stuIDs+"\n"+
                            "TA IDs: " + taIDs+"\n";
//                    for (int j = 0; j< studentArray.length;j++ ){
//                        Log.d("shit", "Student: "+studentArray[j]);
//                        entry += studentArray[j];
//                        if (j+1 < studentArray.length){
//                            entry += ", ";
//                        }else{
//                            entry += "\n";
//                        }
//                    }
//                    entry += "TA IDs: ";
//                    for (int k = 0; k < taArray.length; k++){
//                        Log.d("shit", "TA: "+taArray[k]);
//                        entry += taArray[k];
//                        if (k+1 < taArray.length){
//                            entry += ", ";
//                        }else{
//                            entry += "\n";
//                        }
//                    }

                    DbAdapter dbAdapter = new DbAdapter(getContext());
                    dbAdapter.addTutorial(tutorial);
                    TextView mTextView = new TextView(getContext());
                    mTextView.setText(entry);
                    mTextView.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Intent mIntent = new Intent(getActivity(),
                                    TutorialRecordDisplayActivity.class);
                            mIntent.putExtra("url", url);
                            mIntent.putExtra("stuIds", stuIDs);
                            mIntent.putExtra("taIDs", taIDs);
                            mIntent.putExtra("code", code);
                            getActivity().startActivity(mIntent);
                        }
                    });
                    root.addView(mTextView);
                }
            } catch (JSONException e) {
                Log.d("JsonException", e.toString());
            }
        }else{
            if(mCursor.moveToFirst()) {
                ArrayList<String> test = new ArrayList<>();
                try {
                    while (!mCursor.isAfterLast()) {
                        entry = "Code: \t" + mCursor.getString(1) + "\n"+
                                "Student IDs: "+mCursor.getString(3)+"\n"+
                                "TA IDs: " + mCursor.getString(2)+"\n";

                        final String url = mCursor.getString(0);
                        final String code = mCursor.getString(1);
                        final String stuIDs = mCursor.getString(3);
                        final String taIDs = mCursor.getString(2);

                        TextView mTextView = new TextView(getContext());
                        mTextView.setText(entry);
                        mTextView.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                Intent mIntent = new Intent(getActivity(),
                                        TutorialRecordDisplayActivity.class);
                                mIntent.putExtra("url", url);
                                mIntent.putExtra("stuIds", stuIDs);
                                mIntent.putExtra("taIDs", taIDs);
                                mIntent.putExtra("code", code);
                                getActivity().startActivity(mIntent);
                            }
                        });
                        root.addView(mTextView);
                        mCursor.moveToNext();
                    }
                }catch (Exception e){
                    Log.d("ERROR", e.toString());
                }
                mCursor.close();
            }
        }
    }
}
