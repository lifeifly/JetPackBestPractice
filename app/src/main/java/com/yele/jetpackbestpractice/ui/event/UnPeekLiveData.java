package com.yele.jetpackbestpractice.ui.event;

public class UnPeekLiveData<T> extends ProtectedUnPeekLiveData<T> {
    public UnPeekLiveData() {
    }

    public void setValue(T value) {
        super.setValue(value);
    }

    public void postValue(T value) {
        super.postValue(value);
    }

    public static class Builder<T> {
        private boolean isAllowNullValue;

        public Builder() {
        }

        public UnPeekLiveData.Builder<T> setAllowNullValue(boolean allowNullValue) {
            this.isAllowNullValue = allowNullValue;
            return this;
        }

        public UnPeekLiveData<T> create() {
            UnPeekLiveData<T> liveData = new UnPeekLiveData();
            liveData.isAllowNullValue = this.isAllowNullValue;
            return liveData;
        }
    }
}
