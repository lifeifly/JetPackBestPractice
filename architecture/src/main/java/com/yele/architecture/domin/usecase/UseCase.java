package com.yele.architecture.domin.usecase;

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <Q> the request type
 * @param <P> the response type
 */
public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValues> {
    private Q mRequestValues;
    private UseCaseCallback<P> mUseCaseCallback;

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(Q mRequestValues) {
        this.mRequestValues = mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> mUseCaseCallback) {
        this.mUseCaseCallback = mUseCaseCallback;
    }

    void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(RequestValues requestValues);

    public interface RequestValues {
    }

    public interface ResponseValues {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);

        default void onError() {
        }
    }
}
