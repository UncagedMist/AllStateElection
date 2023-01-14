package tbc.uncagedmist.allstateelection.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tbc.uncagedmist.allstateelection.ElectionActivity;
import tbc.uncagedmist.allstateelection.R;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        createNotification(
                message.getData().get("title"),
                message.getData().get("message"),
                message.getData().get("image")
        );
    }

    private void createNotification(String notificationTitle, String notificationContent, String imageUrl){
        Intent intent = new Intent(this, ElectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Let's create a notification builder object
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                getResources().getString(R.string.notification_channel_id));
        // Create a notificationManager object
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        // If android version is greater than 8.0 then create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create a notification channel
            NotificationChannel notificationChannel = new NotificationChannel(
                    getResources().getString(R.string.notification_channel_id),
                    getResources().getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            // Set properties to notification channel
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            // Pass the notificationChannel object to notificationManager
            notificationManager.createNotificationChannel(notificationChannel);
        }
        builder.setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        if (imageUrl != null) {
            Bitmap bitmap = getBitmapFromUrl(imageUrl);
            builder.setStyle(
                    new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(null)
            ).setLargeIcon(bitmap);
        }

        notificationManager.notify(1, builder.build());
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.e("awesome", "Error in getting notification image: " + e.getLocalizedMessage());
            return null;
        }
    }
}
