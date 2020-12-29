package com.example.telegramnotificationssample.telegramhack;

import androidx.annotation.NonNull;

public interface NotificationCenterLifecycleAware {
    void observe(@NonNull NotificationCenter.NotificationCenterDelegate observer, int... ids);

    void removeObservation(NotificationCenter.NotificationCenterDelegate observer, int id);
}
