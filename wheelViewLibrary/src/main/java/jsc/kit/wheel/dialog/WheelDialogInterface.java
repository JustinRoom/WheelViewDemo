package jsc.kit.wheel.dialog;

import jsc.kit.wheel.base.IWheel;

public interface WheelDialogInterface<T extends IWheel> {

    boolean onClick(int witch, int selectedIndex, T item);
}