package com.yele.architecture.domin.usecase;

public class UseCaseHandler {
    private static UseCaseHandler INSTANCE;

    private final UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        this.mUseCaseScheduler = useCaseScheduler;
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> void execute(final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> useCaseCallback) {
        useCase.setRequestValues(values);

        useCase.setUseCaseCallback(new UiCallbackWrapper<>(useCaseCallback, this));
        mUseCaseScheduler.execute(useCase::run);
    }

    private <V extends UseCase.ResponseValues> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    private <V extends UseCase.ResponseValues> void notifyError(UseCase.UseCaseCallback caseCallback) {
        mUseCaseScheduler.onError(caseCallback);
    }

    /**
     * 装饰器，内部代理实际工作的callback
     *
     * @param <V>
     */
    private static final class UiCallbackWrapper<V extends UseCase.ResponseValues> implements UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback, UseCaseHandler useCaseHandler) {
            this.mCallback = callback;
            this.mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }
}
