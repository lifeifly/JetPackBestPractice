package com.yele.jetpackbestpractice.data.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yele.architecture.domin.request.BaseRequest;
import com.yele.architecture.model.DataResult;
import com.yele.jetpackbestpractice.data.bean.TestAlbum;
import com.yele.jetpackbestpractice.data.repository.DataRepository;

/**
 * 音乐资源  Request
 * <p>
 * TODO tip 1：Request 通常按业务划分
 * 一个项目中通常存在多个 Request 类，
 * 每个页面配备的 state-ViewModel 实例可根据业务需要持有多个不同的 Request 实例。
 * <p>
 * request 的职责仅限于 对数据请求的转发，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 * <p>
 * 如果这样说还不理解的话，详见《如何让同事爱上架构模式、少写 bug 多注释》的解析
 * https://xiaozhuanlan.com/topic/8204519736
 * <p>
 * Create by KunMinX at 19/10/29
 */
public class MusicRequest extends BaseRequest {

    private final MutableLiveData<DataResult<TestAlbum>> mFreeMusicLiveData = new MutableLiveData<>();

    //TODO tip 2：向 ui 层提供的 request LiveData，使用父类 LiveData 而不是 MutableLiveData，
    //如此达成了 "唯一可信源" 的设计，也即通过访问控制权限实现 "读写分离"（国外称 "单向数据流"），
    //从而确保了消息分发的一致性和可靠性。
    public LiveData<DataResult<TestAlbum>> getFreeMusicLiveData() {
        //TODO tip 3：与此同时，为了方便语义上的理解，故而直接将 DataResult 作为 LiveData value 回推给 UI 层，
        //而不是将 DataResult 的泛型实体拆下来单独回推，如此
        //一方面使 UI 层有机会基于 DataResult 的 responseStatus 来分别处理 请求成功或失败的情况下的 UI 表现，
        //另一方面从语义上强调了 该数据是请求得来的结果，是只读的，与 "可变状态" 形成明确的区分，
        //从而方便团队开发人员自然而然遵循 "唯一可信源"/"单向数据流" 的开发理念，规避消息同步一致性等不可预期的错误。
        return mFreeMusicLiveData;
    }

    //TODO tip4:只能从内部修改而暴露给外部的只是父类LiveData达到唯一可信源的目的
    public void requestFreeMusic() {
        //TODO Tip：lambda 语句只有一行时可简写，具体可结合实际情况选择和使用

        /*DataRepository.getInstance().getFreeMusic(dataResult -> {
            mFreeMusicsLiveData.setValue(dataResult);
        });*/

        DataRepository.getInstance().getFreeMusic(mFreeMusicLiveData::setValue);
    }


}
