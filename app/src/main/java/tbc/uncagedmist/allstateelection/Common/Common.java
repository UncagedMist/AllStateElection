package tbc.uncagedmist.allstateelection.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import tbc.uncagedmist.allstateelection.MainActivity;
import tbc.uncagedmist.allstateelection.Model.Election;
import tbc.uncagedmist.allstateelection.Model.Item;
import tbc.uncagedmist.allstateelection.R;
import tbc.uncagedmist.allstateelection.Remote.IMyAPI;
import tbc.uncagedmist.allstateelection.Remote.RetrofitClient;

public class Common {

    public static final String BASE_URL = "https://app.ihuntech.com/allelection/";


    public static Election CURRENT_ELECTION;
    public static Item CURRENT_ITEM;

    public static IMyAPI getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(IMyAPI.class);
    }

    public static void checkAppUpdate(Context context) {
        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))    {

                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                result,AppUpdateType.IMMEDIATE,
                                (Activity) context,
                                12);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public static void shareApp(Context context)    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Never Miss A Thing About Election. Install "+context.getString(R.string.app_name) +"App and Stay Updated! \n https://play.google.com/store/apps/details?id="+context.getPackageName();
        intent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(intent, "Share "+context.getString(R.string.app_name) +" App Using"));
    }

    public static boolean isConnectedToInternet(Context context)    {

        ConnectivityManager connectivityManager = (
                ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)    {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null)   {

                for (int i = 0; i <info.length;i++)   {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)  {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
