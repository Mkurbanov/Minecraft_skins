package com.mkurbanov.minecraftskins.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mkurbanov.minecraftskins.R;
import com.mkurbanov.minecraftskins.data.HomeModel;
import com.mkurbanov.minecraftskins.tools.Functions;

import java.io.IOException;
import java.io.InputStream;

public class HomeFragment extends Fragment {
    public static HomeFragment instance;
    HomeAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        instance = this;
    }

    public static HomeFragment getInstance() {
        if (instance == null)
            instance = new HomeFragment();
        return instance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rc);

        adapter = new HomeAdapter(getContext(), viewModel.list.getValue());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.list.observe(getViewLifecycleOwner(), yo4Lists -> {
            adapter.items = viewModel.list.getValue();
            adapter.notifyDataSetChanged();
        });

        viewModel.loadData();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.getAllFromDB();
        adapter.notifyDataSetChanged();
    }
}