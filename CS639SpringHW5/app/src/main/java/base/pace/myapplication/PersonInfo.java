package base.pace.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by vp60132n on 4/6/2018.
 */
public class PersonInfo implements Serializable {
//public class PersonInfo implements Parcelable {
    private String mName;
    private String mBio;

    public PersonInfo(String name,String bio){
        mName = name;
        mBio = bio;
    }

     public PersonInfo(Parcel in) {
        mName = in.readString();
        mBio = in.readString();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmBio() {
        return mBio;
    }

    public void setmBio(String mBio) {
        this.mBio = mBio;
    }

}
