package com.mkurbanov.minecraftskins.ui.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkurbanov.minecraftskins.App;
import com.mkurbanov.minecraftskins.data.HomeModel;
import com.mkurbanov.minecraftskins.data.database.models.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavViewModel extends ViewModel {
    public MutableLiveData<List<HomeModel.Yo4List>> list = new MutableLiveData<>(new ArrayList<>());

    public FavViewModel() {

    }


    public void loadData() {
        getData();
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (list.getValue() != null || !list.getValue().isEmpty())
                    list.getValue().clear();

                if (App.getInstance().db.favDao().getAll() == null)
                    return;

                for (Favorite favorite : App.getInstance().db.favDao().getAll())
                    list.getValue().add(new HomeModel.Yo4List(favorite.yo4f2, favorite.yo4t3, favorite.yo4i1, favorite.yo4_jo2x6, favorite.yo4_1apqg, favorite.yo4d4, favorite.yo4_6g));

                list.postValue(list.getValue());
            }
        }).start();
    }

}