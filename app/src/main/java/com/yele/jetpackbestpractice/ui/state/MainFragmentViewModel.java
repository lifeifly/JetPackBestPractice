package com.yele.jetpackbestpractice.ui.state;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yele.jetpackbestpractice.data.bean.TestAlbum;

import java.util.List;

/**
 * TODO tip：每个页面都要单独准备一个 state-ViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 * <p>
 * 此外，state-ViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 * <p>
 * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350
 * <p>
 * Create by KunMinX at 19/10/29
 */
public class MainFragmentViewModel extends ViewModel {
    public final ObservableBoolean initTabAndPage = new ObservableBoolean(true);
    public final ObservableField<String> pageAssetPath = new ObservableField<>();

    //TODO 此处用于绑定的状态，使用 LiveData 而不是 ObservableField，
    // 主要是考虑到 ObservableField 具有防抖的特性，不适合该场景。
    private MutableLiveData<List<TestAlbum.TestMusic>> list=new MutableLiveData<>();

    //TODO tip 2：将 request 作为 ViewModel 的成员暴露给 Activity/Fragment，
    // 如此便于语义的明确，以及实现多个 request 在 ViewModel 中的组合和复用。

    public final MusicRequest musicRequest = new MusicRequest();
}
