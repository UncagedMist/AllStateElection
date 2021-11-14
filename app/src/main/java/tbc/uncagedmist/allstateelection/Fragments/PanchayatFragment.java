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


public class PanchayatFragment extends Fragment {

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

        View itemView = inflater.inflate(R.layout.fragment_panchayat, container, false);

        txtName = itemView.findViewById(R.id.txtName);

        txtName.setText(R.string.app_name);


        if (MyApplication.getInstance().isShowAds()) {
            GoogleAds.loadNativeAds((Activity) context, (View) null, (ViewGroup) itemView.findViewById(R.id.admob_native_container), (NativeAdView) itemView.findViewById(R.id.native_ad_view));
        } else {
            itemView.findViewById(R.id.admob_native_container).setVisibility(View.GONE);
        }

        clickListener(itemView);

        return itemView;
    }

    private void clickListener(View itemView) {
        itemView.findViewById(R.id.panchHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.home_page);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.HOME_PAGE;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchNomination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CurrentName = context.getString(R.string.nomination);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.NOMINATION;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panch2021).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CurrentName = context.getString(R.string.candidates);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.CONTESTING_21;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchBooth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CurrentName = context.getString(R.string.know_your_booth);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.KNOW_BOOTH;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchPDF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CurrentName = context.getString(R.string.electoral_roll_pdf);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.ELECTORAL_ROLL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }

                else {

                    Common.CurrentName = context.getString(R.string.search_electoral_roll);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.SEARCH_ROLL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.panchayat_voter_list);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.PANCHAYAT_LIST;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchCandidates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    Common.CurrentName = context.getString(R.string.contesting_candidates);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.CONTESTING_CANDIDATES;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchTrack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.photo);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.PHOTO_GALLERY;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        itemView.findViewById(R.id.panchCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null) {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    Common.CurrentName = context.getString(R.string.vote_count);

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.ELECTION_RESULT;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
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
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.main_frame,homeFragment).commit();
                }
            }
        });
    }
}