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
public class YearItem implements IWheel {

    private int year;

    public YearItem(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String getShowText() {
        return String.format(Locale.CHINA, "%d年", year);
    }
}
