package com.mkurbanov.minecraftskins.ui.favorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mkurbanov.minecraftskins.R;
import com.mkurbanov.minecraftskins.ui.home.HomeAdapter;
import com.mkurbanov.minecraftskins.ui.home.HomeFragment;

public class FavFragment extends Fragment {
    FavViewModel viewModel;
    public static FavFragment instance;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        instance = this;
    }

    public static FavFragment getInstance() {
        if (instance == null)
            instance = new FavFragment();
        return instance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FavViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rc);

        FavAdapter adapter = new FavAdapter(getContext(), viewModel.list.getValue());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.list.observe(getViewLifecycleOwner(), yo4Lists -> {
            adapter.items = viewModel.list.getValue();
            adapter.notifyDataSetChanged();
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadData();
    }
}