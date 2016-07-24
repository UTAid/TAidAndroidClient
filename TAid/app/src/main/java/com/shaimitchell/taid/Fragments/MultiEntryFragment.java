package com.shaimitchell.taid.Fragments;

import android.content.Context;
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
import com.shaimitchell.taid.LocalDb.TAidDbHandler;
import com.shaimitchell.taid.Models.Student;
import com.shaimitchell.taid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        View mView = inflater.inflate(R.layout.fragment_student, container, false);
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

        Student student;

        if (mCursor == null) {

            try {
                JSONObject jsonResponse = new JSONObject(dbEntry);
                JSONArray resultsResponse = jsonResponse.optJSONArray("results");

                for (int i = 0; i < resultsResponse.length(); i++) {
                    JSONObject jsonData = resultsResponse.getJSONObject(i);
                    student = new Student(jsonData.getString("url"),
                            jsonData.getString("university_id"),
                            jsonData.getString("student_number"),
                            jsonData.getString("first_name"),
                            jsonData.getString("last_name"),
                            jsonData.getString("email"));

                    entry = "url: \t" + student.getUrl() + "\n" +
                            "university_id: \t" + student.getUniversityId() + "\n" +
                            "student_number: \t" + student.getStudentNumber() + "\n" +
                            "first_name: \t" + student.getFirstName() + "\n" +
                            "last_name: \t" + student.getLastName() + "\n" +
                            "email: \t" + student.getEmail() + "\n";

                    DbAdapter dbAdapter = new DbAdapter(getContext());
                    dbAdapter.addStudent(student);
                    TextView mTextView = new TextView(getContext());
                    mTextView.setText(entry);
                    root.addView(mTextView);
                }
            } catch (JSONException e) {
                Log.d("JsonException", e.toString());
            }
        }else{
            if(mCursor.moveToFirst()) {
                try {
                    while (!mCursor.isAfterLast()) {
                        entry = "url: \t" + mCursor.getString(1) + "\n" +
                                "university_id: \t" + mCursor.getString(2) + "\n" +
                                "student_number: \t" + mCursor.getString(3) + "\n" +
                                "first_name: \t" + mCursor.getString(4) + "\n" +
                                "last_name: \t" + mCursor.getString(5) + "\n" +
                                "email: \t" + mCursor.getString(6) + "\n";
                        TextView mTextView = new TextView(getContext());
                        mTextView.setText(entry);
                        root.addView(mTextView);
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
        String universityID;
        String firstName;
        String lastName;
        String email;

        String entry="";

        try {
            JSONObject jsonResponse = new JSONObject(dbEntry);
            JSONArray resultsResponse = jsonResponse.optJSONArray("results");

            for (int i = 0; i < resultsResponse.length(); i++){
                JSONObject jsonData = resultsResponse.getJSONObject(i);
                universityID = jsonData.getString("university_id");
                firstName = jsonData.getString("first_name");
                lastName = jsonData.getString("last_name");
                email = jsonData.getString("email");

                entry = "university_id: \t" + universityID +"\n"+
                        "first_name: \t" + firstName +"\n"+
                        "last_name: \t" + lastName +"\n"+
                        "email: \t" + email  +"\n";
                TextView mTextView = new TextView(getContext());
                mTextView.setText(entry);
                root.addView(mTextView);
            }
        }catch(JSONException e){
            Log.d("JsonException", e.toString());
        }
    }

    /**
     * sets up the fragment to be display teaching assistant data
     * @param root - the root layout of the fragment
     */
    private void setupTAResponse(LinearLayout root){
        String universityID;
        String firstName;
        String lastName;
        String email;

        String entry="";

        try {
            JSONObject jsonResponse = new JSONObject(dbEntry);
            JSONArray resultsResponse = jsonResponse.optJSONArray("results");

            for (int i = 0; i < resultsResponse.length(); i++){
                JSONObject jsonData = resultsResponse.getJSONObject(i);
                universityID = jsonData.getString("university_id");
                firstName = jsonData.getString("first_name");
                lastName = jsonData.getString("last_name");
                email = jsonData.getString("email");

                entry = "university_id: \t" + universityID +"\n"+
                        "first_name: \t" + firstName +"\n"+
                        "last_name: \t" + lastName +"\n"+
                        "email: \t" + email  +"\n";
                TextView mTextView = new TextView(getContext());
                mTextView.setText(entry);
                root.addView(mTextView);
            }
        }catch(JSONException e){
            Log.d("JsonException", e.toString());
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

    }
}
