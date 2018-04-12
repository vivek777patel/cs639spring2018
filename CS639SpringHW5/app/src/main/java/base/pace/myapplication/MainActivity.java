package base.pace.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView mListView;
    private EditText mNameEditText, mBioEditText;
    private Button mAddButton, mClearButton, mOpenTabButton;

    private Context mContext;

    public static final String LIST = "LIST";

    private List<PersonInfo> mPersonInfoList = new ArrayList<>();
    private TabListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        setViews();
        configureListView();
        setOnClickListeners();
    }

    // Method to set the object of all the views and assigned to a variable
    public void setViews() {
        mListView = findViewById(R.id.list);

        mNameEditText = findViewById(R.id.nameEditText);
        mBioEditText = findViewById(R.id.bioEditText);

        mAddButton = findViewById(R.id.addTabButton);
        mClearButton = findViewById(R.id.clearTabButton);
        mOpenTabButton = findViewById(R.id.openTabButton);
    }

    // To configure a list view
    public void configureListView() {
        mAdapter = new TabListViewAdapter(this, mPersonInfoList);
        mListView.setAdapter(mAdapter);
    }

    // On click listener for add button , clear button and Open tab button
    public void setOnClickListeners() {
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                String bio = mBioEditText.getText().toString();

                // Validation of the entered data
                if (name.isEmpty() || bio.isEmpty()) {
                    generateToastMessage(R.string.name_bio_required);
                    return;
                }

                appendDataToListView(name, bio); // To append the data to list view

                // To clear the text views
                mNameEditText.setText("");
                mBioEditText.setText("");

                // To hide keyboard
                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mNameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mBioEditText.getWindowToken(), 0);
            }
        });

        // clears listview entries
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        // opens the new activity and pass the first object of listview as well as list view directly (Note : No need to pass both first object and list separately only list should be passed)
        mOpenTabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mPersonInfoList.size()<=0){
                    generateToastMessage(R.string.no_value_in_list);
                    return;
                }
                Intent intent = new Intent(mContext, TabActivity.class);
                intent.putExtra(LIST,(Serializable)mPersonInfoList);
                startActivity(intent);
            }
        });
    }

    // Add new object to list view
    private void appendDataToListView(String name, String bio) {
        if(mPersonInfoList.size()<4){
            mPersonInfoList.add(new PersonInfo(name, bio));
            mAdapter.notifyDataSetChanged();
        }
        else
            generateToastMessage(R.string.only_four_tab_acceptable);
    }

    // Clear list view
    private void clearData() {
        mPersonInfoList.clear();
        mAdapter.notifyDataSetChanged();
    }

    // To generate Toast message
    private void generateToastMessage(int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
}
