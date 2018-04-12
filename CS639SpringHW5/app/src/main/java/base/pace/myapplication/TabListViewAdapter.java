package base.pace.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vp60132n on 4/6/2018.
 */

public class TabListViewAdapter extends BaseAdapter {

    Context mContext;
    List<PersonInfo> mPersonInfoList;
    View.OnClickListener mClickListener;
    private static final String TAG = "TabListViewAdapter";

    public TabListViewAdapter(Context context, List<PersonInfo> personInfoList){
        mContext = context;
        mPersonInfoList = personInfoList;
    }

    @Override
    public int getCount() {
        return mPersonInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPersonInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_items, null);
            viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.lvNameTextView),
                    (TextView) convertView.findViewById(R.id.lvBioTextView)); // creating new item/ViewHolder
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();  //reusing item/ViewHolder

        PersonInfo personInfo = (PersonInfo) getItem(position);
        Log.i(TAG,"Name : "+personInfo.getmName());
        Log.i(TAG,"Bio : "+personInfo.getmBio());
        viewHolder.mNameTextView.setText(personInfo.getmName());
        viewHolder.mBioTextView.setText(personInfo.getmBio());

        viewHolder.mNameTextView.setTag(position);

        return convertView;
    }

    private class ViewHolder {

        TextView mNameTextView,mBioTextView;

        ViewHolder(TextView nameTextView,TextView bioTextView) {
            mNameTextView = nameTextView;
            mBioTextView = bioTextView;
        }
    }

}
