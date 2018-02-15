package com.pace.cs639spring.hw2;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kachi on 2/7/18.
 */

public class AnimalDisplayListViewAdapter extends BaseAdapter {

    public final String TAG = "AnimalDisplayListView"; //Can't go beyond 23 characters
    public Context mContext;

    ViewHolder mSelectedAnimalRow;
    List<AnimalDescription> mAnimalList;
    Map<Integer,ViewHolder> mAnimalHM = new HashMap<>();

    AnimalDisplayListViewAdapter(Context context, List<AnimalDescription> exampleObjectList) {
        mContext = context;
        mAnimalList = exampleObjectList;
    }

    @Override
    public int getCount() {
        return mAnimalList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnimalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.image_text_linearlayout, null);
            ViewHolder viewHolder = new ViewHolder((ImageView) convertView.findViewById(R.id.animalImg),
                    (TextView) convertView.findViewById(R.id.animalTxt),(Button)convertView.findViewById(R.id.descNextBtn),(Button)convertView.findViewById(R.id.descDelBtn));
            convertView.setTag(viewHolder);
        }

        AnimalDescription object = (AnimalDescription) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.mImageView.setImageResource(object.mAnimalImageId);
        viewHolder.mImageView.setTag(object.mAnimalImageId);
        viewHolder.mTextView.setText(object.mAnimalDescList != null && !object.mAnimalDescList.isEmpty() ? object.mAnimalDescList.get(0):"");
        viewHolder.mAnimalDescList = object.mAnimalDescList;
        viewHolder.mAnimalDescListIndex = 0;

        viewHolder.mImageView.setOnClickListener(animalImageViewOnClickListener(viewHolder));
        // Setting on click listener for next and delete buttons
        viewHolder.mNextDescBtn.setOnClickListener(nextDescBtnOnClickListener());
        viewHolder.mDeleteBtn.setOnClickListener(deleteBtnOnClickListener());

        mAnimalHM.put(object.mAnimalImageId,viewHolder);

        return convertView;
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

    public View.OnClickListener animalImageViewOnClickListener(final ViewHolder viewHolder){

        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView selectedAnimal = (ImageView) v;
                int selectedAnimalId = (Integer)selectedAnimal.getTag();
                Log.i(TAG,"Selected Animal Id : "+selectedAnimalId);

                if(mSelectedAnimalRow!=null){
                    if(selectedAnimal!=mSelectedAnimalRow.mImageView){
                        mSelectedAnimalRow.mImageView.setColorFilter(R.color.colorBlack);
                        setVisibility(mSelectedAnimalRow,View.INVISIBLE);
                        setVisibility(viewHolder,View.VISIBLE);
                        mSelectedAnimalRow = viewHolder;
                    }
                }
                else{
                    setVisibility(viewHolder,View.VISIBLE);
                    mSelectedAnimalRow=viewHolder;
                }
            }
            public void setVisibility(ViewHolder animalViewHolder,int visibility){
                animalViewHolder.mTextView.setVisibility(visibility);
                animalViewHolder.mNextDescBtn.setVisibility(visibility);
                animalViewHolder.mDeleteBtn.setVisibility(visibility);
            }
        };

        return vocl;
    }

    static class ViewHolder {

        ImageView mImageView;
        TextView mTextView;
        Button mNextDescBtn;
        Button mDeleteBtn;

        // Using for description delete and to display next description
        List<String> mAnimalDescList;
        int mAnimalDescListIndex;

        ViewHolder(ImageView imageView, TextView textView,Button nextDescBtn,Button deleteBtn) {
            mImageView = imageView;
            mTextView = textView;
            mNextDescBtn = nextDescBtn;
            mDeleteBtn = deleteBtn;
        }

    }
}
