package jsc.kit.wheel.base;

import android.support.annotation.ColorInt;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public interface IWheelViewSetting {

    /**
     *
     * @param textSize the text size
     */
    void setTextSize(float textSize);

    /**
     *
     * @param textColor the text color
     */
    void setTextColor(@ColorInt int textColor);

    /**
     *
     * @param showCount the show count
     */
    void setShowCount(int showCount);

    /**
     *
     * @param totalOffsetX the horizontal offset
     */
    void setTotalOffsetX(int totalOffsetX);

    /**
     *
     * @param itemVerticalSpace the vertical space of two items
     */
    void setItemVerticalSpace(int itemVerticalSpace);

}
