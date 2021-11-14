package tbc.uncagedmist.allstateelection.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.Locale;

import tbc.uncagedmist.allstateelection.Ads.GoogleAds;
import tbc.uncagedmist.allstateelection.Common.Common;
import tbc.uncagedmist.allstateelection.MyApplication;
import tbc.uncagedmist.allstateelection.R;


public class VoterFragment extends Fragment {

    TextView txtName;

    Context context;

    @Override
    public void onAttach(@NonNull Activity activity) {
        context = activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MyApplication.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        View itemView = inflater.inflate(R.layout.fragment_voter, container, false);

        txtName = itemView.findViewById(R.id.txtName);

        txtName.setText(R.string.voter_id_card);

        if (MyApplication.getInstance().isShowAds()) {
            GoogleAds.loadNativeAds((Activity) context, (View) null, (ViewGroup) itemView.findViewById(R.id.admob_native_container), (NativeAdView) itemView.findViewById(R.id.native_ad_view));
        } else {
            itemView.findViewById(R.id.admob_native_container).setVisibility(View.GONE);
        }

        clickListener(itemView);

        return itemView;
    }

    private void clickListener(View itemView) {
        itemView.findViewById(R.id.voterApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.apply_online);

                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.APPLY_VOTER;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.voterDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CurrentName = context.getString(R.string.download_voter_id);


                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.DOWNLOAD_VOTER;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.voterEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.edit_voter_card);

                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.EDIT_VOTER;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.voterSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.search_voter);

                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.SEARCH_VOTER;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.voterTrack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.voter_track);

                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.TRACK_VOTER;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.voterReprint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.voter_reprint);

                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.VOTER_REPRINT;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.voterServices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.voter_services);

                    BlankFragment blankFragment = new BlankFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.VOTER_SERVICES;

                    transaction.replace(R.id.main_frame,blankFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction transaction = ((AppCompatActivity) context)
                            .getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.main_frame, homeFragment).commit();
                }
            }
        });
    }

}