package base.pace.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vp60132n on 4/7/2018.
 */

public class TabActivity extends AppCompatActivity implements FirstTabFragment.PrevNextButtonClickListener,SecondTabFragment.PrevNextButtonClickListenerSecondTab {

    public static final String TAG = "TabActivity";
    public static final int FONT_SIZE = 25;
    public static final String FIRST_PERSON_INFO = "FIRST_PERSON_INFO";
    public static final String PREV_BUTTON = "PREV_BUTTON";
    public static final String NEXT_BUTTON = "NEXT_BUTTON";

    // For the visibility of next and previous buttons
    private int mPrevButtonVisibility = View.GONE;
    private int mNextButtonVisibility = View.GONE;

    private int mCurrentTab = 1;

    private ArrayList<PersonInfo> mTabList = new ArrayList<>();

    private LinearLayout mBottomLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mBottomLinearLayout = findViewById(R.id.tabActivityBottomLL);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mTabList = (ArrayList<PersonInfo>) intent.getSerializableExtra(MainActivity.LIST);
        setBottomTabLayout();

        FirstTabFragment firstTabFragment = new FirstTabFragment();
        Bundle bundle = setBundleForNextFragment();
        firstTabFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.rowLL,firstTabFragment).commit();
    }

    // To set the bottom horizontal list of text views
    public void setBottomTabLayout(){
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        RelativeLayout.LayoutParams paramRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramRelative.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Iterate over the list which we got from first activity and create 4 clickable text view at the bottom (Note : These textviews will be created in activity)
        int i=0;
        for(PersonInfo pi : mTabList){
            TextView textView = new TextView(this);
            textView.setText(pi.getmName());
            textView.setLayoutParams(param);
            textView.setTextSize(FONT_SIZE);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(addTabClickListener());
            textView.setTag(++i);
            mBottomLinearLayout.addView(textView);
            mBottomLinearLayout.setLayoutParams(paramRelative);
        }
    }

    // Click listener of the bottom text views
    private View.OnClickListener addTabClickListener(){
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView)view;
                mCurrentTab = (Integer) tv.getTag();
                replaceFragmentWithNewContents();
            }
        };

        return clickListener;
    }

    public int getmCurrentTab() {
        return mCurrentTab;
    }

    public void setmCurrentTab(int mCurrentTab) {
        this.mCurrentTab = mCurrentTab;
    }

    // This method is common in both the interfaces (FirstTabFragment's and SecondTabFragment's), ideally it can be different but the purpose of both interface is same hence kept the same name
    @Override
    public void onButtonClick(String buttonType) {
        findNextTab(buttonType);
        replaceFragmentWithNewContents();
    }

    // To replace the current with other fragment
    private void replaceFragmentWithNewContents(){
        Bundle b = setBundleForNextFragment();

        if(getFragmentManager().findFragmentById(R.id.rowLL) instanceof FirstTabFragment){
            SecondTabFragment secondTabFragment = new SecondTabFragment();
            secondTabFragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.rowLL,secondTabFragment).commit();
        }
        else{
            FirstTabFragment firstTabFragment = new FirstTabFragment();
            firstTabFragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.rowLL,firstTabFragment).commit();
        }
    }

    // Setting the bundle value for next value and seting the mCurrentTab which represents the tab number
    private Bundle setBundleForNextFragment(){
        setButtonVisibility();
        mCurrentTab--;
        Bundle bundle = new Bundle();
        bundle.putSerializable(FIRST_PERSON_INFO, mTabList.get(mCurrentTab));
        bundle.putInt(PREV_BUTTON, mPrevButtonVisibility);
        bundle.putInt(NEXT_BUTTON,mNextButtonVisibility);
        mCurrentTab++;
        return bundle;
    }

    // Setting the visibility of Next and Previous buttons
    private void setButtonVisibility(){
        if(mCurrentTab==1){
            //Next only visible  -- if list size is 1 then no button should be visible
            if(mTabList.size() > 1 ){
                mNextButtonVisibility = View.VISIBLE;
                mPrevButtonVisibility = View.GONE;
            }
            else{
                mNextButtonVisibility = View.GONE;
                mPrevButtonVisibility = View.GONE;
            }

        }
        else if(mCurrentTab==mTabList.size()){
            // Prev only visible
            mNextButtonVisibility = View.GONE;
            mPrevButtonVisibility = View.VISIBLE;
        }
        else{
            // Both Prev and Next visible
            mNextButtonVisibility = View.VISIBLE;
            mPrevButtonVisibility = View.VISIBLE;
        }
    }

    // Find which button is clicked and change the value of mCurrentTab accordingly
    private void findNextTab(String buttonType){
        switch (buttonType){
            case NEXT_BUTTON:
                if(mTabList.size()>mCurrentTab)
                    mCurrentTab++;
                break;
            case PREV_BUTTON:
                if(mCurrentTab!=1)
                    mCurrentTab--;
                break;
        }
    }
}
