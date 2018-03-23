package base.vivek.cs639springhw4.fragments;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import java.util.List;

import base.vivek.cs639springhw4.R;
import base.vivek.cs639springhw4.custom.CustomCircle;

/**
 * Created by Vivek on 3/11/18.
 */

public class CustomCircleListViewAdapter extends BaseAdapter {

    Context mContext;
    List<CircleProperties> mCirclePropertiesList;
    View.OnClickListener mClickListener;
    private static final String TAG = "CustomCircleListView";

    CustomCircleListViewAdapter(Context context, List<CircleProperties> circlePropertiesList, View.OnClickListener clickListener) {
        mContext = context;
        mCirclePropertiesList = circlePropertiesList;
        mClickListener = clickListener;
    }

    public AbsListView.RecyclerListener mRecyclerListener = new AbsListView.RecyclerListener() {

        public void onMovedToScrapHeap(View view) {
            RelativeLayout rl = (RelativeLayout)view;
            CustomCircle c = (CustomCircle)rl.getChildAt(0); // To get the hierarchical customview RL>>CustomView
            c.stop();
        }

    };

    @Override
    public int getCount() {
        return mCirclePropertiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCirclePropertiesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_items, null);
            viewHolder = new ViewHolder((CustomCircle)convertView.findViewById(R.id.customCircle)); // creating new item/ViewHolder
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();  //reusing item/ViewHolder

        CircleProperties circleProperties = (CircleProperties) getItem(position);
        viewHolder.mCustomCircle.setmCircleSpeed(circleProperties.getmCircleSpeed());
        viewHolder.mCustomCircle.setmCircleSize(circleProperties.getmCircleSize());
        viewHolder.mCustomCircle.setmCircleColor(circleProperties.getmCircleColor());
        viewHolder.mCustomCircle.setOnClickListener(mClickListener);
        viewHolder.mCustomCircle.setTag(position);
        viewHolder.mCustomCircle.start();
        return convertView;
    }

    /*// To Stop Animation by implementing AbsListView.RecyclerListener in this class
    @Override
    public void onMovedToScrapHeap(View view) {
        CustomCircle c = (CustomCircle)view;
        Log.i(TAG,"In Moved To Scrap");
        c.stop();
    }
*/
    private class ViewHolder {

        CustomCircle mCustomCircle;

        ViewHolder(CustomCircle customCircle) {
            mCustomCircle = customCircle;
        }
    }
}