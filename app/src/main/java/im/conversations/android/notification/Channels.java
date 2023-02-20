package im.conversations.android.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import im.conversations.android.R;

public final class Channels {

    private final Application application;

    private static final String CHANNEL_GROUP_STATUS = "status";
    static final String CHANNEL_FOREGROUND = "foreground";

    public Channels(final Application application) {
        this.application = application;
    }

    public void initialize() {
        final var notificationManager =
                ContextCompat.getSystemService(application, NotificationManager.class);
        if (notificationManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.initializeGroups(notificationManager);
            this.initializeForegroundChannel(notificationManager);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeGroups(NotificationManager notificationManager) {
        notificationManager.createNotificationChannelGroup(
                new NotificationChannelGroup(
                        CHANNEL_GROUP_STATUS,
                        application.getString(R.string.notification_group_status_information)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeForegroundChannel(final NotificationManager notificationManager) {
        final NotificationChannel foregroundServiceChannel =
                new NotificationChannel(
                        CHANNEL_FOREGROUND,
                        application.getString(R.string.foreground_service_channel_name),
                        NotificationManager.IMPORTANCE_MIN);
        foregroundServiceChannel.setDescription(
                application.getString(
                        R.string.foreground_service_channel_description,
                        application.getString(R.string.app_name)));
        foregroundServiceChannel.setShowBadge(false);
        foregroundServiceChannel.setGroup(CHANNEL_GROUP_STATUS);
        notificationManager.createNotificationChannel(foregroundServiceChannel);
    }
}