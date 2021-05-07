package com.mkurbanov.minecraftskins.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.mkurbanov.minecraftskins.App;
import com.mkurbanov.minecraftskins.data.HomeModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<List<HomeModel.Yo4List>> list = new MutableLiveData<>(new ArrayList<>());

    public HomeViewModel() {
        //code
    }


    public void loadData() {
        HomeModel homeModel = new Gson().fromJson(loadJSONFromAsset(), HomeModel.class);
        list.postValue(homeModel.yo4_list);
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = App.getInstance().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}