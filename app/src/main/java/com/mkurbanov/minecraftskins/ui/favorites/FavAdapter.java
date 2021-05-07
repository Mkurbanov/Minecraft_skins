package com.mkurbanov.minecraftskins.ui.favorites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mkurbanov.minecraftskins.App;
import com.mkurbanov.minecraftskins.R;
import com.mkurbanov.minecraftskins.data.HomeModel;
import com.mkurbanov.minecraftskins.data.database.models.Favorite;
import com.mkurbanov.minecraftskins.ui.InfoActivity;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<HomeModel.Yo4List> items;
    private Context context;
    private LayoutInflater layoutInflater;
    private int lastPos;
    private List<Favorite> favorites;

    public FavAdapter(Context context, List<HomeModel.Yo4List> items) {
        this.context = context;
        this.items = items;
        this.layoutInflater = LayoutInflater.from(context);
        getAllFromDB();
    }

    public void getAllFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (App.getInstance().db.favDao().getAll() != null)
                    favorites = App.getInstance().db.favDao().getAll();
            }
        }).start();
    }

    private Boolean isOnDB(String fileName) {
        if (favorites == null)
            return false;
        for (Favorite favorite : favorites)
            if (favorite.yo4f2.equals(fileName))
                return true;
        return false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_home, parent, false);
        VHItem vhItem = new VHItem(view);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VHItem vhItem = (VHItem) holder;
        HomeModel.Yo4List data = items.get(position);

        vhItem.imageView.setImageBitmap(App.getInstance().getFunctions().getBitmapFromAssets("images/" + data.yo4i1));
        vhItem.tvTitle.setText(data.yo4t3);
        vhItem.tvText.setText(data.yo4d4);

        if (isOnDB(data.yo4f2)) {
            vhItem.imageViewFav.setBackgroundResource(R.drawable.progress_bg);
            vhItem.imageViewFav.setColorFilter(context.getResources().getColor(R.color.white));
        } else {
            //items.remove(position);
        }

        vhItem.imageViewFav.setOnClickListener(v -> {
            new Thread(() -> App.getInstance().db.favDao().deleteByFileName(data.yo4f2)).start();
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        });

        vhItem.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("image", data.yo4i1);
                intent.putExtra("title", data.yo4t3);
                intent.putExtra("text", data.yo4d4);
                intent.putExtra("file", data.yo4f2);
                intent.putExtra("data", new Gson().toJson(data));
                intent.putExtra("fav", isOnDB(data.yo4f2));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        lastPos = position;
        return position;
        //return items.get(position).getTypeItem();
    }

    private class VHItem extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvText;
        ImageView imageView;
        ImageView imageViewFav;

        public VHItem(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imageViewFav = (ImageView) itemView.findViewById(R.id.image_fav);
            tvTitle = (TextView) itemView.findViewById(R.id.text_title);
            tvText = (TextView) itemView.findViewById(R.id.text_text);
        }
    }

}
