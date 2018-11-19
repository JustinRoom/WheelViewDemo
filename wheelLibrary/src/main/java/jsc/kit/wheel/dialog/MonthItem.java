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
public class MonthItem implements IWheel {
    
    private int month;

    public MonthItem(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String getShowText() {
        return String.format(Locale.CHINA, "%s月", (month < 10 ? "0" + month : "" + month));
    }
}
