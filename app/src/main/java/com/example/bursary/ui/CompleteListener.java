package com.example.bursary.ui;

import com.example.bursary.FetchUserData;
import com.example.bursary.Upload;

import java.util.List;

public interface CompleteListener {
    default void onComplete(String downLoadUrl){

    }

    default void onUploadFetched(List<Upload> uploads){

    }

    default void onFetchUserDataFetched(List<FetchUserData> fetchUserDataLists){

    }



    default void onGenderGroup(int maleCount, int femaleCount,int others){

    }
}

