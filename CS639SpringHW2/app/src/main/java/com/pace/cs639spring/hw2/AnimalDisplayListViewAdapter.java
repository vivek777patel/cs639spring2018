package com.pace.cs639spring.hw2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kachi on 2/7/18.
 */

public class AnimalDisplayListViewAdapter extends BaseAdapter {

    public final String TAG = "AnimalDisplayListView"; //Can't go beyond 23 characters
    public Context mContext;

    ViewHolder mSelectedAnimalRow;
    List<AnimalDescription> mAnimalList;

    int mSelectedIndex=-1;
    int mColor=R.color.colorBlack; //Setting default color to black

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

        int visibility = mSelectedIndex==position?View.VISIBLE:View.INVISIBLE;

        viewHolder.mImageView.setImageResource(object.mAnimalImageId);
        viewHolder.mImageView.setTag(object.mAnimalImageId);
        viewHolder.mTextView.setText(object.mAnimalDescList != null && !object.mAnimalDescList.isEmpty() ? object.mAnimalDescList.get(0):"");
        viewHolder.mAnimalDescList = object.mAnimalDescList;
        viewHolder.mAnimalDescListIndex = 0;

        // Setting onclick event on Imageview instead of item
        viewHolder.mImageView.setOnClickListener(animalImageViewOnClickListener(viewHolder));

        // Setting on click listener for next and delete buttons
        viewHolder.mNextDescBtn.setOnClickListener(nextDescBtnOnClickListener());
        viewHolder.mDeleteBtn.setOnClickListener(deleteBtnOnClickListener());

        viewHolder.mAnimalSelectedIndex = position;
        viewHolder.mImageView.setColorFilter(mSelectedIndex!=position ? R.color.colorBlack:mColor);

        setVisibility(viewHolder,visibility);

        return convertView;
    }


    public View.OnClickListener deleteBtnOnClickListener(){
        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check for the size in the Animal Description List if size is > 1, show Toast message as 1 description has to be present
                int currIndex = mSelectedAnimalRow.mAnimalDescListIndex;

                if(mSelectedAnimalRow.mAnimalDescList.size()>1) {
                    mSelectedAnimalRow.mAnimalDescList.remove(currIndex);
                    currIndex=mSelectedAnimalRow.mAnimalDescListIndex=
                            mSelectedAnimalRow.mAnimalDescListIndex==0?0:--mSelectedAnimalRow.mAnimalDescListIndex;// When delete first description set the list index again back to 0

                    mSelectedAnimalRow.mTextView.setText(mSelectedAnimalRow.mAnimalDescList.get(currIndex));
                }
                else {
                    Toast.makeText(mContext, R.string.no_desc_to_delete, Toast.LENGTH_SHORT).show();
                    return;
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

                // Check if more than one description is present and show message else change description
                if(mSelectedAnimalRow.mAnimalDescList.size()==1)
                    Toast.makeText(mContext, R.string.no_desc_to_show, Toast.LENGTH_SHORT).show();
                else
                    mSelectedAnimalRow.mTextView.setText(mSelectedAnimalRow.mAnimalDescList.get(currIndex));
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
                mSelectedIndex = viewHolder.mAnimalSelectedIndex;

                // Set global variable for selected animal row and change visibility accordingly
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

        };

        return vocl;
    }

    public void setVisibility(ViewHolder animalViewHolder,int visibility){
        animalViewHolder.mTextView.setVisibility(visibility);
        animalViewHolder.mNextDescBtn.setVisibility(visibility);
        animalViewHolder.mDeleteBtn.setVisibility(visibility);
    }

    static class ViewHolder {

        ImageView mImageView;
        TextView mTextView;
        Button mNextDescBtn;
        Button mDeleteBtn;

        // Using for description delete and to display next description
        List<String> mAnimalDescList;
        int mAnimalDescListIndex;
        int mAnimalSelectedIndex; // To get the selected index of image

        ViewHolder(ImageView imageView, TextView textView,Button nextDescBtn,Button deleteBtn) {
            mImageView = imageView;
            mTextView = textView;
            mNextDescBtn = nextDescBtn;
            mDeleteBtn = deleteBtn;
        }
    }
}
