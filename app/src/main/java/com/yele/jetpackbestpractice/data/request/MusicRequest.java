package com.yele.jetpackbestpractice.data.request;

import androidx.lifecycle.MutableLiveData;

import com.yele.architecture.domin.request.BaseRequest;
import com.yele.architecture.model.DataResult;
import com.yele.jetpackbestpractice.data.bean.TestAlbum;

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

    private final MutableLiveData<DataResult<TestAlbum>> mFreeMusicLiveData=new MutableLiveData<>();

    //TODO tip 2：向 ui 层提供的 request LiveData，使用父类 LiveData 而不是 MutableLiveData，
    //如此达成了 "唯一可信源" 的设计，也即通过访问控制权限实现 "读写分离"（国外称 "单向数据流"），
    //从而确保了消息分发的一致性和可靠性。




}
