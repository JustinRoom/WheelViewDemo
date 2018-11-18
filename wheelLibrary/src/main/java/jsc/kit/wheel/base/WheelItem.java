package jsc.kit.wheel.base;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class WheelItem implements IWheel {

    String label;

    public WheelItem(String label) {
        this.label = label;
    }

    @Override
    public String getShowText() {
        return label;
    }
}
