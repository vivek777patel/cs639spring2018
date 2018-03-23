package base.vivek.cs639springhw4.fragments;

import android.util.Log;

/**
 * Created by vp60132n on 3/12/2018.
 */

class CircleProperties {

    private int mCircleColor;
    private int mCircleSize;
    private int mCircleSpeed;

    public CircleProperties(int circleColor, int circleSize, int circleSpeed) {
        mCircleColor = circleColor;
        mCircleSize = circleSize;
        mCircleSpeed = circleSpeed;
    }

    public void increaseSpeed() {
        ++mCircleSpeed;
    }

    public void decreaseSpeed() {
        if(mCircleSpeed > 1) --mCircleSpeed; // check for speed value 0 as speed cant be zero
        Log.i("CircleProperties","Speed Value : "+mCircleSpeed);
    }

    public int getmCircleColor() {
        return mCircleColor;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public int getmCircleSize() {
        return mCircleSize;
    }

    public void setmCircleSize(int mCircleSize) {
        this.mCircleSize = mCircleSize;
    }

    public int getmCircleSpeed() {
        return mCircleSpeed;
    }

    public void setmCircleSpeed(int mCircleSpeed) {
        this.mCircleSpeed = mCircleSpeed;
    }
}

