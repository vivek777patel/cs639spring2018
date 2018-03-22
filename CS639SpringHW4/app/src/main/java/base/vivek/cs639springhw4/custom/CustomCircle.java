package base.vivek.cs639springhw4.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import base.vivek.cs639springhw4.R;

/**
 * Created by vp60132n on 3/11/2018.
 */

public class CustomCircle extends View {

    private final String TAG = "CustomCircle"; // Constant for Log TAG
    private final int DEFAULT_CIRCLE_SIZE = 5;
    private final int DEFAULT_CIRCLE_SPEED = 10;
    private final int DEFAULT_CIRCLE_COLOR = Color.BLUE;

    private Context mContext;
    private Paint mCirclePaint;
    // Defining the attribute variables
    private int mCircleColor, mCircleSpeed, mCircleSize;

    private boolean mFirstTimeCheck = Boolean.TRUE;
    private boolean mMovingRight = Boolean.TRUE;
    private boolean mStopMoving = Boolean.FALSE;

    private int mPadding = dpToPx(5);
    private int mCircleX, mCircleY, mMaxX;

    static final long SECOND_IN_MILLIS = 10;

    private Runnable mUpdateRunnable = new Runnable() {

        @Override
        public void run() {
            moveCircle();
        }
    };

    public CustomCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomCircle, 0, 0);
        try {
            mCircleColor = a.getColor(R.styleable.CustomCircle_circleColor, DEFAULT_CIRCLE_COLOR);
            mCircleSpeed = dpToPx(a.getInteger(R.styleable.CustomCircle_circleSpeed, DEFAULT_CIRCLE_SIZE));
            //mCircleSize = a.getDimensionPixelSize(R.styleable.CustomCircle_circleSize, DEFAULT_CIRCLE_SPEED);
            mCircleSize = dpToPx(a.getInteger(R.styleable.CustomCircle_circleSize, DEFAULT_CIRCLE_SPEED));
        } finally {
            a.recycle();
        }
        init();
    }

    public void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCircleX = mPadding + mCircleSize;
        start();
    }

    // Method to start animation
    public void start() {
        removeCallbacks(mUpdateRunnable); //remove the runnable. We're resetting things
        mCirclePaint.setColor(mCircleColor);
        if(mStopMoving)
            mStopMoving = !mStopMoving;
        moveCircle();
        postDelayed(mUpdateRunnable, SECOND_IN_MILLIS);
    }

    // Method to stop animation
    public void stop(){
        Log.i(TAG,"In Stop");
        removeCallbacks(mUpdateRunnable);
    }

    protected void moveCircle() {
        // Call onDraw again and again
        invalidate();
        requestLayout();

        // Set the logic for the circle movement right-->left-->right
        if (mMovingRight) {
            mCircleX += mCircleSpeed;
            if (mCircleX >= mMaxX)// Once circle reaches at right most side change its movement towards left.
                mMovingRight = !mMovingRight;
        } else {
            mCircleX -= mCircleSpeed;
            if (mCircleX <= mCircleSize)// Once circle reaches at left most side change its movement towards right.
                mMovingRight = !mMovingRight;
        }
        if(!mStopMoving)
            postDelayed(mUpdateRunnable, SECOND_IN_MILLIS); //re attaching runnable for infinite loop
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFirstTimeCheck) {
            mCircleY = getHeight() / 2;
            mMaxX = getWidth() - mCircleSize;
            mCircleX = getWidth() / 2;
            mFirstTimeCheck = !mFirstTimeCheck;
        }
        canvas.drawCircle(mCircleX, mCircleY, mCircleSize, mCirclePaint);
    }

    // To set measurement of our custom view which gets called after constructor automatically as its a lifecycle method
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = widthMeasureSpec + getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();

        // set height of view as size of Circle
        int desiredHeight = mCircleSize * 2 + getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureDimension(desiredWidth, widthMeasureSpec),
                measureDimension(desiredHeight, heightMeasureSpec));
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {// For specific values like 16dp/20dp etc
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {// For wrap_contents
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    /*
    *   Method used to convert dp values to pixels
    * */
    private int dpToPx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
    }

    public int getmCircleColor() {
        return mCircleColor;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public int getmCircleSpeed() {
        return mCircleSpeed;
    }

    public void setmCircleSpeed(int mCircleSpeed) {
        this.mCircleSpeed = dpToPx(mCircleSpeed);
    }

    public int getmCircleSize() {
        return mCircleSize;
    }

    public void setmCircleSize(int mCircleSize) {
        this.mCircleSize = dpToPx(mCircleSize);
    }

    public int getmCircleX() {
        return mCircleX;
    }

    public int getmCircleY() {
        return mCircleY;
    }
}
