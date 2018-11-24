package jsc.kit.wheel.base;

import android.support.annotation.ColorInt;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public interface IWheelViewSetting {

    void setTextSize(float textSize);

    void setTextColor(@ColorInt int textColor);

    void setShowCount(int showCount);

    void setTotalOffsetX(int totalOffsetX);

    void setItemVerticalSpace(int itemVerticalSpace);

    void setItems(IWheel[] items);

    int getSelectedIndex();

    void setSelectedIndex(int targetIndexPosition);

    void setSelectedIndex(int targetIndexPosition, boolean withAnimation);

    void setOnSelectedListener(WheelView.OnSelectedListener onSelectedListener);

    boolean isScrolling();
}
