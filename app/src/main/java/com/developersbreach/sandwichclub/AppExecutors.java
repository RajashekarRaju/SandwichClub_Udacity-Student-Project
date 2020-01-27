package com.developersbreach.sandwichclub;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutors {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sINSTANCE;
    private final Executor mMainThread;
    private final Executor mNetworkIO;

    private AppExecutors(Executor mainThread, Executor networkIO) {
        this.mMainThread = mainThread;
        this.mNetworkIO = networkIO;
    }

    public static AppExecutors getInstance() {
        if (sINSTANCE == null) {
            synchronized (LOCK) {
                sINSTANCE = new AppExecutors(new MainThreadExecutor(), Executors.newFixedThreadPool(3));
            }
        }
        return sINSTANCE;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    public Executor mainThread() {
        return mMainThread;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }
}
