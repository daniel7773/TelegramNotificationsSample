package com.example.telegramnotificationssample.telegramhack;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.HandlerThread;
import android.os.Looper;


import com.example.telegramnotificationssample.BaseApplication;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class MyHelper {
    public static final String LOG_TAG = "POS_LOG_TAG";
    public static final Observer EMPTY_SUBSCRIBER = new Observer() {


        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object o) {

        }
    };
    public static final long APP_TIMEOUT = 42;
    //private static OkHttpClient okHttpClient;
    static MyHandler mainHandler = new MyHandler(Looper.getMainLooper());
    static MyHandler eventsMainHandler;
    static MyHandler uiSupHandler;
    static MyHandler initialSyncAwaitHandler;

    static MyHandler eventsSupplementaryHandler;

    private static final ReentrantLock okHttpLock = new ReentrantLock();
    public static Runnable setupOkHttpRunnable;

    private static ReentrantLock supplementaryLock = new ReentrantLock();
    private static Scheduler printerRxScheduler;

    private static OkHttpClient sharedOkHttp;


    static { //TODO lazy initialization
        uiSupHandler = getNewThreadHandler("UI_sup");

        eventsMainHandler = getNewThreadHandler("Events");

        initialSyncAwaitHandler = getNewThreadHandler("InitialSyncAwait");
    }

    private static boolean debug;

    @SuppressLint("StaticFieldLeak")
    private static BaseApplication applicationInstance;
    private static Scheduler eventsSupplementaryScheduler;
    private static Scheduler uiSupplementaryScheduler;

  /*  @NonNull
    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpLock.lock();
            if (okHttpClient != null) {
                okHttpLock.unlock();
                return okHttpClient;
            }
            setupOkHttpRunnable.run();
            okHttpLock.unlock();
        }
        return okHttpClient;
    }*/

    public static void runOnMain(Runnable action) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else {
            mainHandler.post(action);
        }
    }

    public static void runOnUiSupThread(Runnable action) {
        if (Looper.myLooper() == uiSupHandler.getLooper()) {
            action.run();
        } else {
            uiSupHandler.post(action);
        }
    }

    public static void runOnInitialSyncAwaitThread(Runnable action) {
        if (Looper.myLooper() == initialSyncAwaitHandler.getLooper()) {
            action.run();
        } else {
            initialSyncAwaitHandler.post(action);
        }
    }

    public static MyHandler getMainHandler() {
        return mainHandler;
    }

    public static MyHandler getEventsMainHandler() {
        return eventsMainHandler;
    }

    public static MyHandler getUiSupHandler() {
        return uiSupHandler;
    }

    public static void postDebounceOnMain(Runnable runnable, int delay, Object token) {
        if (token != null) {
            mainHandler.removeCallbacksAndMessages(token);
            mainHandler.postDelayed(runnable, delay, token);
        } else {
            mainHandler.postDelayed(runnable, delay);
        }
    }

    public static void postOnMain(Runnable action) {
        mainHandler.post(action);
    }

    public static void postOnMainDelayed(Runnable runnable, int delay) {
        mainHandler.postDelayed(runnable, delay);
    }

   /* public static void setOkHttpClient(OkHttpClient okHttpClient) {
        PosHelper.okHttpClient = okHttpClient;
    }*/

    public static SSLSocketFactory getUnsafeSocketFactory() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MyHandler getEventsSupplementaryHandler() {   //TODO normal double check getInstance
        supplementaryLock.lock();
        if (eventsSupplementaryHandler == null) {
            eventsSupplementaryHandler = getNewThreadHandler("Events_sup");
        }
        supplementaryLock.unlock();
        return eventsSupplementaryHandler;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        MyHelper.debug = debug;
    }

    public static BaseApplication getApplicationInstance() {
        return applicationInstance;
    }

    public static void setApplicationInstance(BaseApplication applicationInstance) {
        MyHelper.applicationInstance = applicationInstance;
    }

    public static void pass() {
    }

    public static boolean isMainThread() {
        return mainHandler.getLooper() == Looper.myLooper();
    }

    public static String getDeviceUUID() {
        return "UUID"; // TODO add back return SharedPrefsHelper.getDeviceUUID();
    }

    public static String getDeviceName() {
        return Build.MODEL + ", Android " + Build.VERSION.RELEASE;
    }


    public static Scheduler getEventsSupplementaryScheduler() {
        if (eventsSupplementaryScheduler == null) {
            eventsSupplementaryScheduler = AndroidSchedulers.from(getEventsSupplementaryHandler().getLooper());
        }
        return eventsSupplementaryScheduler;
    }

    public static Scheduler getUiSupplementaryScheduler() {
        if (uiSupplementaryScheduler == null) {
            uiSupplementaryScheduler = AndroidSchedulers.from(MyHelper.getUiSupHandler().getLooper());
        }
        return uiSupplementaryScheduler;
    }

    public static Scheduler getBlockingPrinterScheduler() {
        if (printerRxScheduler == null) {
            HandlerThread thread = new HandlerThread("BLOCKING_PRINTER");
            thread.start();
            MyHandler printerHandler = new MyHandler(thread.getLooper());
            printerRxScheduler = AndroidSchedulers.from(printerHandler.getLooper());
        }
        return printerRxScheduler;
    }

    public static MyHandler getNewThreadHandler(String name) {
        HandlerThread thread = new HandlerThread(name);
        thread.start();
        return new MyHandler(thread.getLooper());
    }
}
