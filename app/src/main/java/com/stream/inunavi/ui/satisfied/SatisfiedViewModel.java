package com.stream.inunavi.ui.satisfied;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SatisfiedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SatisfiedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}