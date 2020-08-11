package com.example.createrzone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;

import java.util.List;

public class CustomViewModel extends ViewModel implements ResponseClass {
    Repository repository;

    public void getRepository(RequestQueue requestQueue) {
        if (repository == null) {
            repository = new Repository(requestQueue, this);
        }
    }

    public MutableLiveData<List<String>> list = new MutableLiveData<List<String>>();

    public void setList(MutableLiveData<List<String>> _list) {
        this.list = _list;
    }

    public LiveData<List<String>> getList() {
        return list;
    }

    public void callAPI(String tag) {
        repository.getSearchResult(tag);
    }

    @Override
    public void getResponse(List<String> res) {
        list.setValue(res);
    }
}
