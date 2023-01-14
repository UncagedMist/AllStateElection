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
import android.widget.TextView;

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
import tbc.uncagedmist.allstateelection.Adapter.ItemAdapter;
import tbc.uncagedmist.allstateelection.Common.Common;
import tbc.uncagedmist.allstateelection.Model.Election;
import tbc.uncagedmist.allstateelection.Model.Item;
import tbc.uncagedmist.allstateelection.Remote.IMyAPI;

public class ItemActivity extends AppCompatActivity {

    private FrameLayout adContainerView;
    private AdView adView;

    RecyclerView recyclerView;

    IMyAPI iMyAPI;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    NoInternetDialog noInternetDialog;

    ProgressDialog dialog;

    TextView txtName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        noInternetDialog = new NoInternetDialog.Builder(this).build();

        dialog = new ProgressDialog(this);

        iMyAPI = Common.getAPI();

        adContainerView = findViewById(R.id.bannerContainer);

        recyclerView = findViewById(R.id.recyclerView);

        txtName = findViewById(R.id.txtName);

        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_id));
        adContainerView.addView(adView);

        if (MyApplication.getInstance().isShowAds())   {
            loadBanner();
        }

        txtName.setText(Common.CURRENT_ELECTION.name);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.shareApp(ItemActivity.this);
            }
        });

        loadStates();



    }


    private void loadStates() {
        dialog.setTitle("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        compositeDisposable.add(
                iMyAPI.getItem(Common.CURRENT_ELECTION.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Item>>() {
                                       @Override
                                       public void accept(List<Item> items) throws Exception {
                                           displayStates(items);
                                           dialog.dismiss();
                                       }
                                   }
                        )
        );
    }


    private void displayStates(List<Item> itemList) {
        ItemAdapter adapter = new ItemAdapter(this,itemList);

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
    protected void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}