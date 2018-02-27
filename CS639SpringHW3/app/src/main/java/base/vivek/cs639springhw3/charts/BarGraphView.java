package base.vivek.cs639springhw3.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import base.vivek.cs639springhw3.R;

/**
 * Created by vp60132n on 2/24/2018.
 */

public class BarGraphView extends View {

    // Defining constants
    private final String TAG = "CustomBarChart"; // Constant for Log TAG
    private final int DEFAULT_MAX_NO_OF_BARS = 5; // Set the default max no of bars

    private Context mContext;// As of now unused

    // Defining the attribute variables
    private int mMaxBarColor,mNoOfMaxBar,mOtherBarColor;
    private Paint mBarPaint,mGridPaint;
    private TreeMap<String,Integer> mDateAttendance = new TreeMap<>(); // Taken the TreeMap in order to sort data according to date automatically

    private String mTitle="";// As of now unused but can be used to show the Title on bar

    private int mPadding=dpToPx(20), mSpacing = dpToPx(10),
            mColumnSpacing = dpToPx(20),mLeftBottomSpaceForText = dpToPx(20), mTextSize = dpToPx(16),
            mMaxWidthOfBar = dpToPx(50);

    private int mArrayMaxValue; // Maximum value of the list
    private float mHeightOfChart; // Height of chart or height of Max value bar

    public BarGraphView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,R.styleable.CustomBarChart,0, 0);
        try {
            mMaxBarColor = a.getInteger(R.styleable.CustomBarChart_maxBarColor, Color.BLUE);
            mOtherBarColor = a.getInteger(R.styleable.CustomBarChart_otherBarColor, Color.BLACK);
            mNoOfMaxBar = a.getInteger(R.styleable.CustomBarChart_noOfMaxBar, DEFAULT_MAX_NO_OF_BARS);
            mTitle = a.getString(R.styleable.CustomBarChart_title); // As of now unused but can be used to show the Title on bar
        } finally {
            a.recycle();
        }
        init(0,"");
    }
    /*
    *   Method to initialize/add the data
    *   the argument "value" is no of students attended the class on specific day "barLabel"
    * */
    public void init(int value, String barLabel) { // Similar to addData() which was mentioned in details of Homework3 on Blackboard
        // Initializing/Assigning the default values for bar-chart

        //TODO : write a getter method for getting vertical label based on max no of bar

        // Condition check whether init method is called from constructor or from Add button click from MainActivity
        // Check against "" as barLabel can't be blank or "" when init is called from Add button click from MainActivity
        if(!barLabel.equalsIgnoreCase(""))
            setValuesOfBarAndLabel(value,barLabel);

        // Set the default paint value for bars
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.FILL);
        mBarPaint.setColor(mOtherBarColor);

        // Set the default paint value for X and Y Axis
        mGridPaint = new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(Color.BLACK);

        mArrayMaxValue = Math.max(mArrayMaxValue,value);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO : Write logic to display bar chart
        final int height = getHeight();//To get view height
        final int width = getWidth();// To get view width
        final float gridLeft = mPadding;

        mHeightOfChart = height - mPadding - mLeftBottomSpaceForText;
        final float gridBottom = mHeightOfChart;

        final float gridTop = mPadding;
        final float gridRight = width - mPadding;

        // Draw Grid Lines (X Axis and Y Axis)
        canvas.drawLine(gridLeft, gridBottom, gridLeft, gridTop,mGridPaint);
        canvas.drawLine(gridLeft, gridBottom, gridRight, gridBottom,mGridPaint);

        mGridPaint.setTextSize(mTextSize);
        canvas.drawText("0", gridLeft-mLeftBottomSpaceForText, gridBottom, mGridPaint);
        if(0!=mArrayMaxValue && Integer.MIN_VALUE!=mArrayMaxValue){
            canvas.drawText(mArrayMaxValue+"", gridLeft-mLeftBottomSpaceForText, gridTop+mSpacing, mGridPaint);
        }

        int dataCount = mDateAttendance.size();

        // Draw Bars
        // Width of the bars as per number of bars
        float totalColumnSpacing = mSpacing * (dataCount + 1);
        // Calculating the width of the bar based on varied number of bars
        float columnWidth = Math.min((gridRight - gridLeft - totalColumnSpacing) / dataCount,mMaxWidthOfBar);
        float columnLeft = gridLeft + mColumnSpacing;
        float columnRight = columnLeft + columnWidth;

        for (Map.Entry<String,Integer> entry : mDateAttendance.entrySet()) {

            int attendanceValue = entry.getValue();
            String date = entry.getKey();
            // Calculate top of column based on percentage.
            float top = gridTop + getBarHeight(attendanceValue);
            // Set the bar color based on its value (different color for max value bar)
            if(attendanceValue == mArrayMaxValue && mDateAttendance.size()!=1)
                mBarPaint.setColor(mMaxBarColor);
            else
                mBarPaint.setColor(mOtherBarColor);

            if(gridBottom<top){
                top = gridBottom - (top - gridBottom);
            }
            canvas.drawRect(columnLeft, top, columnRight, gridBottom, mBarPaint);
            canvas.drawText(date, columnLeft, gridBottom+mLeftBottomSpaceForText, mGridPaint);
            // Shift over left/right column bounds
            columnLeft = columnRight + mColumnSpacing;
            columnRight = columnLeft + columnWidth;
        }
    }

    /*
    *   Method to extract the relative height of the bar based on the Chart Height
    */
    private float getBarHeight(int value){
        float percentageOfMaxValue = (value * 100)/mArrayMaxValue;
        return mHeightOfChart - (mHeightOfChart * percentageOfMaxValue)/100;
    }

    /*
    *   Method to set the value to TreeMap
    *   Also checks for the max no of bar condition, If max bar limit reached remove the very older value (in this case very first element)
    * */
    private void setValuesOfBarAndLabel(int value, String barLabel){
        if(mDateAttendance.size() == mNoOfMaxBar){
            mDateAttendance.remove(mDateAttendance.firstKey());
        }
        mDateAttendance.put(barLabel,value);
    }


    /*
    *   Method called when new value is added to the list
    *   This method is called on OnClickListener of Add button of MainActivity
    * */
    public void reDrawView(int value, String barLabel){
        init(value, barLabel);
        invalidate(); // To indicate canvas and re draw with new values
        requestLayout();
    }

    /*
    *   Method used to convert dp values to pixels
    * */
    private int dpToPx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
    }

}
