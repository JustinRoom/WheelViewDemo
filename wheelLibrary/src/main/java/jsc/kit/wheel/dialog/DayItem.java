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
public class DayItem implements IWheel {
    
    private int day;

    public DayItem(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String getShowText() {
        return String.format(Locale.CHINA, "%s日", (day < 10 ? "0" + day : "" + day));
    }
}
