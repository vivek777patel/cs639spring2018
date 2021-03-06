package base.pace.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by vp60132n on 4/07/2018.
 */

public class FirstTabFragment extends Fragment {

    public static final String TAG = "FirstTabFragment";
    TextView mNameTextView, mBioTextView;
    Button mNextButton, mPrevButton;

    // Click listener interface, so data can be passed to activity
    interface PrevNextButtonClickListener {
        void onButtonClick(String buttonType);
    }
    PrevNextButtonClickListener mPrevNextButtonClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab , vg, false);

        mNameTextView = (TextView)v.findViewById(R.id.lvNameTextView);
        mBioTextView = (TextView)v.findViewById(R.id.lvBioTextView);
        mPrevButton = (Button)v.findViewById(R.id.previousTabButton);
        mNextButton = (Button)v.findViewById(R.id.nextTabButton);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get the bundle arguments
        Bundle args = getArguments();
        PersonInfo personInfo = (PersonInfo) args.get(TabActivity.FIRST_PERSON_INFO);

        mNameTextView.setText(personInfo.getmName());
        mBioTextView.setText(personInfo.getmBio());

        mNextButton.setVisibility((Integer) args.get(TabActivity.NEXT_BUTTON));
        mPrevButton.setVisibility((Integer) args.get(TabActivity.PREV_BUTTON));

        addPrevNextButtonClickListener();
    }

    // Click listener for Next and Previous buttons
    private void addPrevNextButtonClickListener(){
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button clickedButton = (Button) view;
                int selectedButtonId = clickedButton.getId();
                switch(selectedButtonId){
                    case R.id.previousTabButton:
                        mPrevNextButtonClickListener.onButtonClick(TabActivity.PREV_BUTTON);
                        break;
                    case R.id.nextTabButton:
                        mPrevNextButtonClickListener.onButtonClick(TabActivity.NEXT_BUTTON);
                        break;
                }
            }
        };

        mNextButton.setOnClickListener(buttonListener);
        mPrevButton.setOnClickListener(buttonListener);
    }

    // To attach the clicklistener interface
    @Override
    public void onAttach(Activity a){
        super.onAttach(a);
        try{
            mPrevNextButtonClickListener = (PrevNextButtonClickListener)a;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }

}
