package tbc.uncagedmist.allstateelection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

import tbc.uncagedmist.allstateelection.Utility.AppOpenManager;
import tbc.uncagedmist.allstateelection.Utility.MyNetworkReceiver;

public class MyApplication extends Application {

    private static MyApplication instance;

    private boolean showAds = true;

    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;
    MyNetworkReceiver mNetworkReceiver;



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


        AudienceNetworkAds.initialize(instance);


        if (showAds)    {
            AppOpenManager appOpenManager = new AppOpenManager(this);
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                mNetworkReceiver = new MyNetworkReceiver();
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = activity;
                registerNetworkBroadcastForLollipop();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                mActivity = null;
                unregisterReceiver(mNetworkReceiver);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void registerNetworkBroadcastForLollipop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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
