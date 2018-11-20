package jsc.exam.com.wheelview.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassItem {
    public static final int TYPE_ACTIVITY = 0;
    public static final int TYPE_FRAGMENT = 1;
    @IntDef({TYPE_ACTIVITY, TYPE_FRAGMENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private String label;
    private Class<?> clazz;
    private int type;
    private boolean updated;
    private boolean isLandscape;

    public ClassItem() {
    }

    public ClassItem(@Type int type, String label, Class<?> clazz, boolean updated) {
        this(type, label, clazz, updated, false);
    }
    public ClassItem(@Type int type, String label, Class<?> clazz, boolean updated, boolean isLandscape) {
        this.type = type;
        this.label = label;
        this.clazz = clazz;
        this.updated = updated;
        this.isLandscape = isLandscape;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public void setType(@Type int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isLandscape() {
        return isLandscape;
    }

    public void setLandscape(boolean landscape) {
        isLandscape = landscape;
    }
}
