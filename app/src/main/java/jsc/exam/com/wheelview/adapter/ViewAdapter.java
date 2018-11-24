package jsc.exam.com.wheelview.adapter;

import java.util.List;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public interface ViewAdapter<T> {

    public List<T> getItems();

    public T getItemAt(int position);

    public void setItems(List<T> items);

    public void addItems(List<T> items);

    public void addItems(int position, List<T> items);

    public void addItem(T item);

    public void addItem(int position, T item);

    public void removeItem(int position);

    public void removeItem(T item);
}
