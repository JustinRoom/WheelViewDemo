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
public class MinuteItem implements IWheel {
    
    private int minute;

    public MinuteItem(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String getShowText() {
        return String.format(Locale.CHINA, "%s分", (minute < 10 ? "0" + minute : "" + minute));
    }
}
