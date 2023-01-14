package tbc.uncagedmist.allstateelection.Adapter;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.uncagedmist.allstateelection.Ads.GoogleAds;
import tbc.uncagedmist.allstateelection.Common.Common;
import tbc.uncagedmist.allstateelection.Interface.ItemClickListener;
import tbc.uncagedmist.allstateelection.ItemActivity;
import tbc.uncagedmist.allstateelection.Model.Item;
import tbc.uncagedmist.allstateelection.MyApplication;
import tbc.uncagedmist.allstateelection.R;
import tbc.uncagedmist.allstateelection.ResultActivity;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;
    List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MyApplication.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        return new ItemViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso
                .get()
                .load(Common.CURRENT_ELECTION.image)
                .error(R.mipmap.ic_launcher)
                .into(holder.image);

        holder.name.setText(itemList.get(position).name);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view) {

                if (GoogleAds.mInterstitialAd != null)  {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CURRENT_ITEM = itemList.get(position);
                    context.startActivity(new Intent(context, ResultActivity.class));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView name;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);

            name.setSelected(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view);
        }
    }
}
