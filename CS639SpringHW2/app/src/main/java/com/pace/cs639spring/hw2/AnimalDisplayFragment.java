package com.pace.cs639spring.hw2;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vivek on 2/11/18.
 */

public class AnimalDisplayFragment extends Fragment {

    public static ListView mListView;
    public final String TAG = "AnimalDisplayFragment";

    View mAnimalDisplayView;
    EditText mAddDescEditText;
    Button mAddDescBtn;
    AnimalDisplayListViewAdapter mAdapter;
    ImageButton colorBtn1,colorBtn2,colorBtn3,colorBtn4,colorBtn5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mAnimalDisplayView = inflater.inflate(R.layout.animal_display, container, false);
        mListView = (ListView)mAnimalDisplayView.findViewById(R.id.imgTxtViewLstView);

        configureListView();
        return mAnimalDisplayView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAddDescBtn = mAnimalDisplayView.findViewById(R.id.descAddBtn);
        mAddDescEditText = mAnimalDisplayView.findViewById(R.id.descAddEditText);
        mAddDescBtn.setOnClickListener(addDescBtnOnClickListener());

        colorBtn1 = mAnimalDisplayView.findViewById(R.id.colorBtn1);
        colorBtn2 = mAnimalDisplayView.findViewById(R.id.colorBtn2);
        colorBtn3 = mAnimalDisplayView.findViewById(R.id.colorBtn3);
        colorBtn4 = mAnimalDisplayView.findViewById(R.id.colorBtn4);
        colorBtn5 = mAnimalDisplayView.findViewById(R.id.colorBtn5);

        colorBtn1.setOnClickListener(colorButtonClickListener());
        colorBtn2.setOnClickListener(colorButtonClickListener());
        colorBtn3.setOnClickListener(colorButtonClickListener());
        colorBtn4.setOnClickListener(colorButtonClickListener());
        colorBtn5.setOnClickListener(colorButtonClickListener());

    }

    public View.OnClickListener colorButtonClickListener(){
        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Inside ColorButton OnClick Event");

                ImageButton ib = (ImageButton) view;
                ColorDrawable dc = (ColorDrawable)ib.getBackground();
                int colorId = dc.getColor();

                //Check if Any Animal/Bird is selected, if selected change the color else display Toast message
                if(mAdapter.mSelectedAnimalRow!=null){
                    mAdapter.mSelectedAnimalRow.mImageView.setColorFilter(colorId);
                }
                else{
                    Toast.makeText(view.getContext(),R.string.image_not_selected,Toast.LENGTH_SHORT).show();
                }
            }
        };
        return vocl;
    }

    public View.OnClickListener addDescBtnOnClickListener(){
        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDesc = mAddDescEditText.getText().toString();
                if(newDesc.equalsIgnoreCase("")){
                    Toast.makeText(mAnimalDisplayView.getContext() , R.string.desc_not_added, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(null!=mAdapter.mSelectedAnimalRow){
                    mAdapter.mSelectedAnimalRow.mAnimalDescList.add(newDesc);
                    mAddDescEditText.setText("");
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mAddDescEditText.getWindowToken(), 0);
                }
                else{
                    Toast.makeText(mAnimalDisplayView.getContext() , R.string.image_not_selected, Toast.LENGTH_SHORT).show();
                }
            }
        };

        return vocl;
    }

    public void configureListView(){
        List<AnimalDescription> animalDescriptionList = new ArrayList<>();

        animalDescriptionList.add(new AnimalDescription(R.drawable.bird, Arrays.asList(getResources().getString(R.string.bird_description),"sdfsss","ssss"

        ),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.dog, Arrays.asList(getResources().getString(R.string.dog_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.cat, Arrays.asList(getResources().getString(R.string.cat_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.whale, Arrays.asList(getResources().getString(R.string.whale_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.gorilla, Arrays.asList(getResources().getString(R.string.gorilla_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.kangaroo, Arrays.asList(getResources().getString(R.string.kangaroo_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.kite, Arrays.asList(getResources().getString(R.string.kite_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.monkey, Arrays.asList(getResources().getString(R.string.monkey_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.parrot, Arrays.asList(getResources().getString(R.string.parrot_description)),0));
        animalDescriptionList.add(new AnimalDescription(R.drawable.rabbit, Arrays.asList(getResources().getString(R.string.rabbit_description)),0));

        mAdapter = new AnimalDisplayListViewAdapter(mAnimalDisplayView.getContext(), animalDescriptionList);
        mListView.setAdapter(mAdapter);

    }
}
