package base.vivek.cs639springhw3;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import base.vivek.cs639springhw3.charts.BarGraphView;

/**
 * Created by vp60132n on 2/24/2018.
 */

public class MainActivity extends AppCompatActivity {

    private final String DATE_REGEX = "(0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])";
    private final String NUMBER_REGEX = "[0-9]+";

    BarGraphView mCustomBarChart;
    Button mAddButton;
    EditText mDateEditText,mAttendanceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomBarChart = (BarGraphView) findViewById(R.id.custBarChart);
        mAddButton = (Button)findViewById(R.id.addButton);
        mDateEditText = (EditText) findViewById(R.id.dateEditText);
        mAttendanceEditText = (EditText)findViewById(R.id.attendanceEditText);

        // On Click Listener of Add Button
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateValue = mDateEditText.getText().toString();
                String attendanceCountString = mAttendanceEditText.getText().toString();
                // Validation of Date Field
                if(dateValue.equalsIgnoreCase("")){
                    generateToastMessage(R.string.date_required);
                    return;
                }
                else{
                    // Checking against the Regular Expression
                    if(!dateValue.matches(DATE_REGEX)){
                        generateToastMessage(R.string.date_format_wrong);
                        return;
                    }
                }
                // Validation of attendance field
                if(attendanceCountString.equalsIgnoreCase("")){
                    generateToastMessage(R.string.attendance_required);
                    return;
                }
                else{
                    // Validation of attendance field against the Regular Expression of Number
                    if(!attendanceCountString.matches(NUMBER_REGEX)){
                        generateToastMessage(R.string.enter_only_number_attendance);
                        return;
                    }
                }

                // To clear out both EditTexts
                mDateEditText.setText("");
                mAttendanceEditText.setText("");

                // To hide keyboard
                InputMethodManager imm = (InputMethodManager)getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mDateEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mAttendanceEditText.getWindowToken(), 0);

                // Calling the reDrawView method of Custom View class and re draw the chart based on entered values
                mCustomBarChart.reDrawView(Integer.parseInt(attendanceCountString),dateValue);
            }
        });

    }
    /*
    *   Method to display Toast message
    *   Created just for readability of validation code
    * */
    private void generateToastMessage(int id){
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
    }
}
