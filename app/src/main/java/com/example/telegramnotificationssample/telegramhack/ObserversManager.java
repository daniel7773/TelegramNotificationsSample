package com.example.telegramnotificationssample.telegramhack;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for binding observations of NotificationCenter to some lifecycle
 */
public class ObserversManager {

    private List<Pair<Object, Integer>> observingIds;  //TODO change Pair on some detailed class

    public void observe(@NonNull NotificationCenter.NotificationCenterDelegate observer, int[] ids) {
        if (observingIds == null) {
            observingIds = new ArrayList<>(ids.length);
        }

        NotificationCenter nc = NotificationCenter.getInstance();
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            nc.addObserver(observer, id);
            observingIds.add(new Pair<>(observer, id));
        }
    }

    public void removeObserver(NotificationCenter.NotificationCenterDelegate observer, int id) {
        NotificationCenter.getInstance().removeObserver(observer, id);
        if (observingIds != null) {
            //noinspection SuspiciousMethodCalls
            observingIds.remove(new Pair<>(observer, id));
        }
    }


    public void removeObservers() {
        if (observingIds != null && observingIds.size() > 0) {
            NotificationCenter nc = NotificationCenter.getInstance();
            for (int i = 0; i < observingIds.size(); i++) {
                Pair<Object, Integer> pair = observingIds.get(i);
                nc.removeObserver(pair.first, pair.second);
            }
        }
        observingIds = null;
    }
}