package com.quirktastic.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.friendrequeststatus.FriendRequestStatusActivity;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Util;

import org.json.JSONObject;

import java.util.Calendar;

public class GetFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = GetFirebaseMessagingService.class.getName();

    private String noti_message = "";
    private String title = "";
    private String type = "";
    private String sender_id = "";
    private String notiTitle = "";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    PendingIntent resultPendingIntent;

    public GetFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

       // Log.e("remoteMessage", remoteMessage.getData().toString());

        // AIzaSyCLZ4Dyd05rPlfGAwIK3cbhzfOzOE2zlfU

        if (!Prefs.getString(this, PrefsKey.USER_ID, "").equals("")) {

            if (remoteMessage.getData().size() > 0) {

                if (remoteMessage.getData() != null && !remoteMessage.getData().toString().equalsIgnoreCase("")) {

                    JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                    setUpNotification(jsonObject);
                }
            }

        }

    }

    private void setUpNotification(JSONObject jsonObject) {

        type = jsonObject.optString("type");
        title = jsonObject.optString("title");
        if (title.equalsIgnoreCase("")) {
            notiTitle = this.getString(R.string.app_name);
        } else {
            notiTitle = title;
        }

        if (type.equals("1")) {
            noti_message = jsonObject.optString("message");
            Intent getRequestIntent = new Intent(this, FriendRequestStatusActivity.class);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntentWithParentStack(getRequestIntent);
            resultPendingIntent = taskStackBuilder
                    .getPendingIntent((int) System.currentTimeMillis(),  PendingIntent.FLAG_ONE_SHOT);
            showNotification();

        } else if (type.equals("2")) {
            noti_message = jsonObject.optString("message");
            Intent dashboardIntentAcceptReq = new Intent(this, DashboardActivity.class);
            dashboardIntentAcceptReq.putExtra("is_from_noti", "1");
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntentWithParentStack(dashboardIntentAcceptReq);
            resultPendingIntent = taskStackBuilder
                    .getPendingIntent((int) System.currentTimeMillis(),  PendingIntent.FLAG_ONE_SHOT);
            showNotification();

        } else if (type.equals("3")) {
            noti_message = jsonObject.optString("message");
            Intent dashboardIntentRejectReq = new Intent(this, DashboardActivity.class);
            dashboardIntentRejectReq.putExtra("is_from_noti", "1");
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntentWithParentStack(dashboardIntentRejectReq);
            resultPendingIntent = taskStackBuilder
                    .getPendingIntent((int) System.currentTimeMillis(),  PendingIntent.FLAG_ONE_SHOT);
            showNotification();

        } else if (type.equals("4")) {
            noti_message = jsonObject.optString("message");
            sender_id = jsonObject.optString("sender_id");
            Intent messageIntent = new Intent(this, ChatProfileActivity.class);
            messageIntent.putExtra("sender_id", sender_id);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntentWithParentStack(messageIntent);
            resultPendingIntent = taskStackBuilder
                    .getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_ONE_SHOT);

            if (!Util.isChatProfileActivityVisible) {
                showNotification();
            }

        }
    }


    private void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, this.getString(R.string.app_name), importance);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(mChannel);
            // Create a notification and set the notification channel.
            Notification notification = getNotificationBuilder(resultPendingIntent, noti_message, notiTitle);
            manager.notify((int) Calendar.getInstance().getTimeInMillis(), notification);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_small_noti)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(notiTitle)
                    .setContentText(noti_message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(noti_message))
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000})
                    .getNotification();
            notification.priority = Notification.PRIORITY_MAX;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify((int)Calendar.getInstance().getTimeInMillis(), notification);
        } else {
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_small_noti)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(notiTitle)
                    .setContentText(noti_message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(noti_message))
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000})
                    .getNotification();
            notification.priority = Notification.PRIORITY_MAX;

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify((int) Calendar.getInstance().getTimeInMillis(), notification);
        }
    }

    private Notification getNotificationBuilder(PendingIntent pIntent, String message, String title) {
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_noti)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText("" + message)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setStyle(new NotificationCompat.BigTextStyle().bigText("" + message))
                .setContentIntent(pIntent).setTicker(message)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000})
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .build();
    }


}
