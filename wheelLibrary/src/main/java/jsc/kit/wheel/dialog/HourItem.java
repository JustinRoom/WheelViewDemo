package jsc.kit.wheel.dialog;

import java.util.Locale;

import jsc.kit.wheel.base.IWheel;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class HourItem implements IWheel {
    
    private int hour;

    public HourItem(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String getShowText() {
        return String.format(Locale.CHINA, "%s时", (hour < 10 ? "0" + hour : "" + hour));
    }
}
