package base.vivek.cs639springhw4.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import base.vivek.cs639springhw4.R;
import base.vivek.cs639springhw4.custom.CustomCircle;

/**
 * Created by vp60132n on 3/12/2018.
 */

public class CustomCircleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "CustomCircleRecycler"; // Constant for Log TAG
    View.OnClickListener mClickListener;
    Context mContext;
    List<CircleProperties> mCustomCircleList;


    public CustomCircleRecyclerViewAdapter(Context context, List<CircleProperties> customCircleList, View.OnClickListener clickListener) {
        mContext = context;
        mCustomCircleList = customCircleList;
        mClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.list_items, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CircleProperties circleProperties = mCustomCircleList.get(position);
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
        viewHolder.mCustomCircle.setmCircleColor(circleProperties.getmCircleColor());
        viewHolder.mCustomCircle.setmCircleSize(circleProperties.getmCircleSize());
        viewHolder.mCustomCircle.setmCircleSpeed(circleProperties.getmCircleSpeed());
        viewHolder.mCustomCircle.setTag(position);
        viewHolder.mCustomCircle.setOnClickListener(mClickListener);
        viewHolder.mCustomCircle.start(); // To start animation again
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder){
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
        Log.i(TAG,"view recycled ");

        viewHolder.mCustomCircle.stop();
    }


    @Override
    public int getItemCount() {
        return mCustomCircleList.size();
    }

    public CircleProperties getItem(int position) {
        return mCustomCircleList.get(position);
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private CustomCircle mCustomCircle;

        RecyclerViewHolder(View view) {
            super(view);
            mCustomCircle = view.findViewById(R.id.customCircle);
        }

        public CustomCircle getmCustomCircle() {
            return mCustomCircle;
        }
    }
}
