package tbc.uncagedmist.allstateelection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.rvadapter.AdmobNativeAdAdapter;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import am.appwise.components.ni.NoInternetDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tbc.uncagedmist.allstateelection.Adapter.ElectionAdapter;
import tbc.uncagedmist.allstateelection.Common.Common;
import tbc.uncagedmist.allstateelection.Model.Election;
import tbc.uncagedmist.allstateelection.Remote.IMyAPI;

public class ElectionActivity extends AppCompatActivity {

    private FrameLayout adContainerView;
    private AdView adView;

    RecyclerView recyclerView;
    EditText edtSearch;

    IMyAPI iMyAPI;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    NoInternetDialog noInternetDialog;

    List<Election> localDataSource = new ArrayList<>();

    ElectionAdapter searchAdapter, adapter;

    ProgressDialog dialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);

        noInternetDialog = new NoInternetDialog.Builder(this).build();

        dialog = new ProgressDialog(this);

        iMyAPI = Common.getAPI();

        adContainerView = findViewById(R.id.bannerContainer);

        recyclerView = findViewById(R.id.recyclerView);
        edtSearch = findViewById(R.id.edtState);

        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_id));
        adContainerView.addView(adView);

        if (MyApplication.getInstance().isShowAds())   {
            loadBanner();
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.shareApp(ElectionActivity.this);
            }
        });

        loadStates();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                startSearch(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void startSearch(CharSequence text) {
        List<Election> result = new ArrayList<>();

        String name = text.toString();

        for (Election election : localDataSource)   {
            if (election.name.toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))    {
                result.add(election);
            }
        }
        searchAdapter = new ElectionAdapter(this, result);
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadStates() {
        dialog.setTitle("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        compositeDisposable.add(
                iMyAPI.getElection("1")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Election>>() {
                                       @Override
                                       public void accept(List<Election> elections) throws Exception {
                                           displayStates(elections);
                                           dialog.dismiss();
                                       }
                                   }
                        )
        );
    }


    private void displayStates(List<Election> electionList) {
        localDataSource = electionList;
        adapter = new ElectionAdapter(this,electionList);

        if (MyApplication.getInstance().isShowAds())    {
            AdmobNativeAdAdapter admobNativeAdAdapter = AdmobNativeAdAdapter.Builder.with(
                            getString(R.string.native_id),
                            adapter,
                            "small")
                    .adItemInterval(2)
                    .build();

            recyclerView.setAdapter(admobNativeAdAdapter);
        }
        else {
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadBanner() {
        AdRequest adRequest =
                new AdRequest.Builder()
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(ElectionActivity.this)
                .setTitle("Exit this App?")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("You're exiting this App!")
                .setNegativeBtnText("Exit")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Rate")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName()))))
                .OnNegativeClicked(() -> {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                })
                .build();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}