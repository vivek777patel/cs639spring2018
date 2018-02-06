package com.pace.cs639spring.hw1;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kachi on 1/31/18.
 * Modified by Vivek on 2/6/2018.
 */

public class AnimalDisplayFragment extends Fragment {

    public final String TAG="AnimalDisplayFragment";

    // Some Class level variables of Application
    SingleRow dogRow,catRow,birdRow,selectedRow;
    ImageButton colorBtn1,colorBtn2,colorBtn3,colorBtn4,colorBtn5;
    Map<Integer,SingleRow> hm = new HashMap<Integer,SingleRow>();

    // Created a class which holds the component objects of Single row such as Animal/Bird ImageViews, Text Description
    class SingleRow{

        ImageView imageView;
        TextView textView;
        String typeString;
        int index;

        public SingleRow(String type,ImageView iv,TextView tv,int indx){
            typeString = type;
            imageView = iv;
            textView = tv;
            index = indx;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animal_display, container, false);

        // Creating an object of onClickListeners for ImageView (Animal/Bird image) and bottom Color Buttons
        ImageViewClickListener ivCL = new ImageViewClickListener();
        ColorButtonClickListener cbCL = new ColorButtonClickListener();

        /*  Creating the SingleRow objects for all the three Animals/Bird which holds the ImageView and TextView Component
        *   Setting ImageView's OnClickListener
        *   Setting ImageView's Color to white to display them as not selected
        */
        dogRow = new SingleRow("Dog",(ImageView)view.findViewById(R.id.dogImg),(TextView)view.findViewById(R.id.dogTxt),R.id.dogImg);
        dogRow.imageView.setOnClickListener(ivCL);
        dogRow.imageView.setColorFilter(R.color.colorWhite, PorterDuff.Mode.SRC_IN);

        catRow = new SingleRow("Cat",(ImageView)view.findViewById(R.id.catImg),(TextView)view.findViewById(R.id.catTxt),R.id.catImg);
        catRow.imageView.setOnClickListener(ivCL);
        catRow.imageView.setColorFilter(R.color.colorWhite, PorterDuff.Mode.SRC_IN);

        birdRow = new SingleRow("Bird",(ImageView)view.findViewById(R.id.birdImg),(TextView)view.findViewById(R.id.birdTxt),R.id.birdImg);
        birdRow.imageView.setOnClickListener(ivCL);
        birdRow.imageView.setColorFilter(R.color.colorWhite, PorterDuff.Mode.SRC_IN);

        // Setting the HashMap to get the selected ImageView component while their onClick Event
        hm.put(R.id.dogImg,dogRow);
        hm.put(R.id.birdImg,birdRow);
        hm.put(R.id.catImg,catRow);

        // Setting the color button components and setting their onClickListener
        colorBtn1 = (ImageButton)view.findViewById(R.id.colorBtn1);
        colorBtn2 = (ImageButton)view.findViewById(R.id.colorBtn2);
        colorBtn3 = (ImageButton)view.findViewById(R.id.colorBtn3);
        colorBtn4 = (ImageButton)view.findViewById(R.id.colorBtn4);
        colorBtn5 = (ImageButton)view.findViewById(R.id.colorBtn5);

        colorBtn1.setOnClickListener(cbCL);
        colorBtn2.setOnClickListener(cbCL);
        colorBtn3.setOnClickListener(cbCL);
        colorBtn4.setOnClickListener(cbCL);
        colorBtn5.setOnClickListener(cbCL);

        return view;
    }

    class ImageViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG,"Inside ImageView OnClick Event (Animal/Bird Click)");

            ImageView iv = (ImageView) view;
            int ivId = iv.getId();
            // Check if any Animal/Bird is selected or not. If not selected then make their color as Black and set TextView visibility to Visible
            if(selectedRow==null){
                iv.setColorFilter(R.color.colorBlack);
                hm.get(ivId).textView.setVisibility(View.VISIBLE);
                selectedRow = hm.get(ivId);
            }
            else{
                // If same Animal/Bird is selected, Change its color to White and Text to Invisible
                if(selectedRow.index==ivId){
                    iv.setColorFilter(R.color.colorWhite, PorterDuff.Mode.SRC_IN);
                    hm.get(ivId).textView.setVisibility(View.INVISIBLE);
                    selectedRow = null;
                }// If any other Animal/Bird is selected, change the color or previous selected component to White and text to Invisible and set the new component
                else{
                    hm.get(selectedRow.index).imageView.setColorFilter(R.color.colorWhite, PorterDuff.Mode.SRC_IN);
                    hm.get(selectedRow.index).textView.setVisibility(View.INVISIBLE);
                    iv.setColorFilter(R.color.colorBlack);
                    hm.get(ivId).textView.setVisibility(View.VISIBLE);
                    selectedRow = hm.get(ivId);
                }
            }
        }
    }

    class ColorButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG,"Inside ColorButton OnClick Event");

            ImageButton ib = (ImageButton) view;
            ColorDrawable dc = (ColorDrawable)ib.getBackground();
            int colorId = dc.getColor();

            //Check if Any Animal/Bird is selected, if selected change the color else display Toast message
            if(selectedRow!=null){
                selectedRow.imageView.setColorFilter(colorId);
            }
            else{
                Toast.makeText(view.getContext(),"Please Select Any Image",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
