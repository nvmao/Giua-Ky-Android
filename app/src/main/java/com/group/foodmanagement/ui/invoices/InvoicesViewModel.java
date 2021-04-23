package com.group.foodmanagement.ui.invoices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InvoicesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InvoicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Invoice fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}