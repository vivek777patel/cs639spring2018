package com.pace.cs639spring.hw2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kachi on 2/7/18.
 */

public class AnimalDisplayRecyclerViewAdapter extends RecyclerView.Adapter<AnimalDisplayRecyclerViewAdapter.ViewHolder>{

    public final String TAG = "AnimalDisplyRecycleView"; //Can't go beyond 23 characters
    public Context mContext;

    ViewHolder mSelectedAnimalRow;
    List<AnimalDescription> mAnimalList;

    public AnimalDisplayRecyclerViewAdapter(List<AnimalDescription> animalList){
        mAnimalList = animalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_text_linearlayout, parent, false);
        return mSelectedAnimalRow = new ViewHolder(itemView,"FirstTime");
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AnimalDescription ad = mAnimalList.get(position);
        holder.mImageView.setImageResource(ad.mAnimalImageId);
        holder.mTextView.setText(ad.mAnimalDescList != null && !ad.mAnimalDescList.isEmpty() ? ad.mAnimalDescList.get(0):"");
        holder.mAnimalDescList = ad.mAnimalDescList;
        holder.mAnimalDescListIndex = 0;
        final ViewHolder vh = holder;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(vh);
            }
        };
        holder.mImageView.setOnClickListener(listener);
        holder.mTextView.setOnClickListener(listener);

        /*holder.mImageView.setOnClickListener(animalImageViewOnClickListener(viewHolder));*/
        // Setting on click listener for next and delete buttons
        holder.mNextDescBtn.setOnClickListener(nextDescBtnOnClickListener());
        holder.mDeleteBtn.setOnClickListener(deleteBtnOnClickListener());
    }

    public View.OnClickListener deleteBtnOnClickListener(){
        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currIndex = mSelectedAnimalRow.mAnimalDescListIndex;

                if(mSelectedAnimalRow.mAnimalDescList.size()>0)
                    mSelectedAnimalRow.mAnimalDescList.remove(currIndex);
                else {
                    Toast.makeText(mContext, R.string.no_desc_to_delete, Toast.LENGTH_SHORT).show();
                    return;
                }

                currIndex=mSelectedAnimalRow.mAnimalDescListIndex=
                        mSelectedAnimalRow.mAnimalDescList.size()==0
                                ? -1 : --mSelectedAnimalRow.mAnimalDescListIndex;

                if(currIndex>=0)
                    mSelectedAnimalRow.mTextView.setText(mSelectedAnimalRow.mAnimalDescList.get(currIndex));
                else{
                    mSelectedAnimalRow.mTextView.setText("");
                }
            }
        };
        return vocl;
    }

    public View.OnClickListener nextDescBtnOnClickListener(){
        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currIndex =
                        mSelectedAnimalRow.mAnimalDescListIndex =
                                mSelectedAnimalRow.mAnimalDescListIndex==mSelectedAnimalRow.mAnimalDescList.size()-1 ?
                                        0 : ++mSelectedAnimalRow.mAnimalDescListIndex;

                if(mSelectedAnimalRow.mAnimalDescList.size()!=0)
                    mSelectedAnimalRow.mTextView.setText(mSelectedAnimalRow.mAnimalDescList.get(currIndex));
                else
                    Toast.makeText(mContext, R.string.no_desc_to_show, Toast.LENGTH_SHORT).show();
            }
        };
        return vocl;
    }

    @Override
    public int getItemCount() {
        return mAnimalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView;
        public Button mNextDescBtn;
        public Button mDeleteBtn;

        // Using for description delete and to display next description
        public List<String> mAnimalDescList;
        public int mAnimalDescListIndex;
        public String mTag;

        public ViewHolder(View view,String tag){
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.animalImg);
            mTextView = (TextView) view.findViewById(R.id.animalTxt);
            mNextDescBtn = (Button)view.findViewById(R.id.descNextBtn);
            mDeleteBtn = (Button)view.findViewById(R.id.descDelBtn);
            mTag = tag;
        }

    }

    private OnItemClickListener onItemClickListener;
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
