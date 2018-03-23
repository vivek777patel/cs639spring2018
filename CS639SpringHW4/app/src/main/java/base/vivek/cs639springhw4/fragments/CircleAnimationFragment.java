package base.vivek.cs639springhw4.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import base.vivek.cs639springhw4.R;


/**
 * Created by vp60132n on 3/12/2018.
 */

public class CircleAnimationFragment extends Fragment {

    private final String TAG = "CircleAnimationFragment";
    private final String NUMBER_REGEX = "[0-9]+";
    private final String PLUS = "PLUS";
    private final String MINUS = "MINUS";

    private RecyclerView mRecyclerView;
    private CustomCircleRecyclerViewAdapter mAdapter;
    private List<CircleProperties> mCircleList = new ArrayList<>();
    private Button mAddButton, mPlusButton, mMinusButton;
    private EditText mRadiusEditText, mSpeedEditText;
    private int mSelectedColor = Integer.MIN_VALUE;
    View mSelectedColorView;
    private TextView mSciSpeedValueTextView;
    private RelativeLayout mCciRL,mSciRL;

    int mSelectedListPosition = Integer.MIN_VALUE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_animation_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAddButton = view.findViewById(R.id.addButton);
        mRadiusEditText = view.findViewById(R.id.radiusEditText);
        mSpeedEditText = view.findViewById(R.id.speedEditText);
        mCciRL = view.findViewById(R.id.cciRL);
        mSciRL = view.findViewById(R.id.sciRL);
        mSciSpeedValueTextView = view.findViewById(R.id.sciSpeedValueTextView);
        mPlusButton = view.findViewById(R.id.plusButton);
        mPlusButton.setTag(PLUS);
        mMinusButton = view.findViewById(R.id.minusButton);
        mMinusButton.setTag(MINUS);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureListView();
        addColorClickListeners();
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String radiusString = mRadiusEditText.getText().toString();
                String speedString = mSpeedEditText.getText().toString();
                if (radiusString.isEmpty()) {
                    generateToastMessage(R.string.radius_required);
                    return;
                } else if (!radiusString.matches(NUMBER_REGEX) || radiusString.equalsIgnoreCase("0")) {
                    generateToastMessage(R.string.radius_must_be_number);
                    return;
                }

                if (speedString.isEmpty()) {
                    generateToastMessage(R.string.speed_required);
                    return;
                } else if (!speedString.matches(NUMBER_REGEX) || speedString.equalsIgnoreCase("0")) {
                    generateToastMessage(R.string.speed_must_be_number);
                    return;
                }

                if (mSelectedColor == Integer.MIN_VALUE) {
                    generateToastMessage(R.string.color_required);
                    return;
                }
                appendDataToRecyclerView(Integer.parseInt(radiusString), Integer.parseInt(speedString), mSelectedColor);

                // Clear Selection
                mRadiusEditText.setText("");
                mSpeedEditText.setText("");
                // Clear out Color selection from color views
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(mSelectedColor); // Changes this drawbale to use a single color instead of a gradient
                mSelectedColorView.setBackground(gd);
                mSelectedColor = Integer.MIN_VALUE;

                // To hide keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mRadiusEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mSpeedEditText.getWindowToken(), 0);
            }
        });
    }

    // Add new object to recycler view
    private void appendDataToRecyclerView(int radius, int speed, int color) {
        mCircleList.add(new CircleProperties(color, radius, speed));
        mAdapter.notifyDataSetChanged();
    }

    private void configureListView() {
        //mCircleList.add(new CircleProperties(Color.BLACK,5,5));
        mAdapter = new CustomCircleRecyclerViewAdapter(getActivity(), mCircleList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if(mSelectedListPosition==position){ // To de select selected position
                    mCciRL.setVisibility(View.VISIBLE);
                    mSciRL.setVisibility(View.INVISIBLE);
                    mSelectedListPosition = Integer.MIN_VALUE;
                    return;
                }
                mSelectedListPosition = position;
                CircleProperties selectedCircleProperties = mAdapter.getItem(position);
                updateSpeedOfSelectedObject(selectedCircleProperties);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        // No need to set RecyclerListener as RecyclerView.Adapter has onViewRecycled method which we can override
    }

    private void updateSpeedOfSelectedObject(CircleProperties properties){
        mCciRL.setVisibility(View.INVISIBLE);
        mSciRL.setVisibility(View.VISIBLE);
        mSciSpeedValueTextView.setText(Integer.toString(properties.getmCircleSpeed()));
        sciButtonClickListeners(properties);
    }

    private void sciButtonClickListeners(final CircleProperties properties){
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = v.getTag().toString();
                switch(tag){
                    case PLUS:
                        properties.increaseSpeed();
                        mSciSpeedValueTextView.setText(Integer.toString(properties.getmCircleSpeed()));
                        mAdapter.notifyDataSetChanged();
                        break;
                    case MINUS:
                        properties.decreaseSpeed();
                        mSciSpeedValueTextView.setText(Integer.toString(properties.getmCircleSpeed()));
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        mMinusButton.setOnClickListener(clickListener);
        mPlusButton.setOnClickListener(clickListener);
    }
    // Color Buttons/Views click listener
    private void addColorClickListeners() {
        View.OnClickListener colorListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                int selectedColor = Integer.parseInt(b.getTag().toString());
                Drawable background = b.getBackground();
                GradientDrawable gd = new GradientDrawable();

                if(mSelectedColor != selectedColor){
                    gd.setCornerRadius(5);
                    gd.setStroke(5, 0xFF000000);
                    mSelectedColor = selectedColor;
                }
                else
                    mSelectedColor = Integer.MIN_VALUE;
                gd.setColor(selectedColor);
                b.setBackground(gd);
                mSelectedColorView = b;
            }
        };
        getView().findViewById(R.id.red).setOnClickListener(colorListener);
        getView().findViewById(R.id.orange).setOnClickListener(colorListener);
        getView().findViewById(R.id.green).setOnClickListener(colorListener);
        getView().findViewById(R.id.blue).setOnClickListener(colorListener);
        getView().findViewById(R.id.yellow).setOnClickListener(colorListener);

        getView().findViewById(R.id.red).setTag(getResources().getColor(R.color.colorRed));
        getView().findViewById(R.id.orange).setTag(getResources().getColor(R.color.colorOrange));
        getView().findViewById(R.id.green).setTag(getResources().getColor(R.color.colorGreen));
        getView().findViewById(R.id.blue).setTag(getResources().getColor(R.color.colorBlue));
        getView().findViewById(R.id.yellow).setTag(getResources().getColor(R.color.colorYellow));
    }

    /*
    *   Method to display Toast message
    *   Created just for readability of validation code
    * */
    private void generateToastMessage(int id) {
        Toast.makeText(mAddButton.getContext(), id, Toast.LENGTH_SHORT).show();
    }
}
