package com.pace.cs639spring.hw2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vp60132n on 2/12/2018.
 */

public class AnimalDescription {

        int mAnimalImageId;
        ArrayList<String> mAnimalDescList;
        int mDescCurrentIndex;

        AnimalDescription(int animalImageId, List<String> animalDescList, int descCurrentIndex) {
            mAnimalImageId = animalImageId;
            mAnimalDescList = new ArrayList<>(animalDescList);
            mDescCurrentIndex = descCurrentIndex;
        }

}
