package com.yele.jetpackbestpractice.ui.event;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtectedUnPeekLiveData<T> extends LiveData<T> {
    private static final String TAG = "V6Test";
    protected boolean isAllowNullValue;
    private final ConcurrentHashMap<Observer<? super T>, Boolean> observerStateMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<Observer<? super T>, Observer<? super T>> observerProxyMap = new ConcurrentHashMap();

    public ProtectedUnPeekLiveData() {
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        Observer<? super T> observer1 = this.getObserverProxy(observer);
        if (observer1 != null) {
            super.observe(owner, observer1);
        }

    }

    public void observeForever(@NonNull Observer<? super T> observer) {
        Observer<? super T> observer1 = this.getObserverProxy(observer);
        if (observer1 != null) {
            super.observeForever(observer1);
        }

    }

    private Observer<? super T> getObserverProxy(Observer<? super T> observer) {
        if (this.observerStateMap.containsKey(observer)) {
            Log.d("V6Test", "observe repeatedly, observer has been attached to owner");
            return null;
        } else {
            this.observerStateMap.put(observer, false);
            ProtectedUnPeekLiveData<T>.ObserverProxy proxy = new ProtectedUnPeekLiveData.ObserverProxy(observer);
            this.observerProxyMap.put(observer, proxy);
            return proxy;
        }
    }

    public void observeSticky(LifecycleOwner owner, Observer<T> observer) {
        super.observe(owner, observer);
    }

    public void observeStickyForever(Observer<T> observer) {
        super.observeForever(observer);
    }

    protected void setValue(T value) {
        if (value != null || this.isAllowNullValue) {
            Iterator var2 = this.observerStateMap.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<Observer<? super T>, Boolean> entry = (Map.Entry)var2.next();
                entry.setValue(true);
            }

            super.setValue(value);
        }

    }

    public void removeObserver(@NonNull Observer<? super T> observer) {
        Observer proxy;
        Observer target;
        if (observer instanceof ProtectedUnPeekLiveData.ObserverProxy) {
            proxy = observer;
            target = ((ProtectedUnPeekLiveData.ObserverProxy)observer).getTarget();
        } else {
            proxy = (Observer)this.observerProxyMap.get(observer);
            target = proxy != null ? observer : null;
        }

        if (proxy != null && target != null) {
            this.observerProxyMap.remove(target);
            this.observerStateMap.remove(target);
            super.removeObserver(proxy);
        }

    }

    public void clear() {
        super.setValue(null);
    }

    private class ObserverProxy implements Observer<T> {
        private final Observer<? super T> target;

        public ObserverProxy(Observer<? super T> target) {
            this.target = target;
        }

        public Observer<? super T> getTarget() {
            return this.target;
        }

        public void onChanged(T t) {
            if (ProtectedUnPeekLiveData.this.observerStateMap.get(this.target) != null && (Boolean)ProtectedUnPeekLiveData.this.observerStateMap.get(this.target)) {
                ProtectedUnPeekLiveData.this.observerStateMap.put(this.target, false);
                if (t != null || ProtectedUnPeekLiveData.this.isAllowNullValue) {
                    this.target.onChanged(t);
                }
            }

        }
    }
}

