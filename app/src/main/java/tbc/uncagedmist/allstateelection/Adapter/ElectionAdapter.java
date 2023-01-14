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
import tbc.uncagedmist.allstateelection.Model.Election;
import tbc.uncagedmist.allstateelection.MyApplication;
import tbc.uncagedmist.allstateelection.R;

public class ElectionAdapter extends RecyclerView.Adapter<ElectionAdapter.ElectionViewHolder> {

    Context context;
    List<Election> electionList;

    public ElectionAdapter(Context context, List<Election> electionList) {
        this.context = context;
        this.electionList = electionList;
    }

    @NonNull
    @Override
    public ElectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MyApplication.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        return new ElectionViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_election,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ElectionViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso
                .get()
                .load(electionList.get(position).image)
                .error(R.mipmap.ic_launcher)
                .into(holder.stateImage);

        holder.stateName.setText(electionList.get(position).name);
        holder.stateDesc.setText(electionList.get(position).description);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view) {
                if (GoogleAds.mInterstitialAd != null)  {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CURRENT_ELECTION = electionList.get(position);
                    context.startActivity(new Intent(context, ItemActivity.class));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return electionList.size();
    }

    public class ElectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName, stateDesc;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ElectionViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.stateImage);
            stateName = itemView.findViewById(R.id.stateName);
            stateDesc = itemView.findViewById(R.id.stateDesc);

            stateName.setSelected(true);
            stateDesc.setSelected(true);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view);
        }
    }
}
