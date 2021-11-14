package tbc.uncagedmist.allstateelection;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

import tbc.uncagedmist.allstateelection.Utility.AppOpenManager;

public class MyApplication extends Application {

    private static MyApplication instance;

    private boolean showAds = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) { }
        });

        AudienceNetworkAds.initialize(this);

        if (showAds)    {
           AppOpenManager appOpenManager = new AppOpenManager(instance);
        }

    }

    public static MyApplication getInstance() {
        return instance;
    }

    public boolean isShowAds() {
        return showAds;
    }

    public void setShowAds(boolean showAds) {
        this.showAds = showAds;
    }
}
