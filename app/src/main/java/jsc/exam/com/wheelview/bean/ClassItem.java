package jsc.exam.com.wheelview.bean;

public class ClassItem {
    private String label;
    private Class<?> cls;
    private boolean updated;

    public ClassItem() {
    }

    public ClassItem(String label, Class<?> cls) {
        this(label, cls, false);
    }

    public ClassItem(String label, Class<?> cls, boolean updated) {
        this.label = label;
        this.cls = cls;
        this.updated = updated;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
