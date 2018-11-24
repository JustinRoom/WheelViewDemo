package jsc.kit.wheel.dialog;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import jsc.kit.wheel.base.IWheel;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class DateItem implements IWheel {

    public static final int TYPE_YEAR = 0;
    public static final int TYPE_MONTH = 1;
    public static final int TYPE_DAY = 2;
    public static final int TYPE_HOUR = 3;
    public static final int TYPE_MINUTE = 4;

    @IntDef({TYPE_YEAR, TYPE_MONTH, TYPE_DAY, TYPE_HOUR, TYPE_MINUTE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateType {
    }

    private int type;
    private int value;

    public DateItem() {
    }

    public DateItem(int value) {
        this(TYPE_YEAR, value);
    }

    public DateItem(@DateType int type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getShowText() {
        return String.format(Locale.CHINA, getFormatStringByType(), (value < 10 ? "0" + value : "" + value));
    }

    private String getFormatStringByType() {
        String result = "";
        switch (type) {
            case TYPE_YEAR:
                result = "%s年";
                break;
            case TYPE_MONTH:
                result = "%s月";
                break;
            case TYPE_DAY:
                result = "%s日";
                break;
            case TYPE_HOUR:
                result = "%s时";
                break;
            case TYPE_MINUTE:
                result = "%s分";
                break;
        }
        return result;
    }
}
