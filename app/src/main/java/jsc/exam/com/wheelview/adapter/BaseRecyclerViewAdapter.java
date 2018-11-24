package jsc.exam.com.wheelview.adapter;


import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements ViewAdapter<T> {

    private List<T> items = null;
    @LayoutRes
    protected int layoutId = -1;
    private boolean itemClickEnable;
    private boolean itemLongClickEnable;
    private OnItemClickListener<T> onViewClickListener = null;
    private OnItemClickListener<T> onItemClickListener = null;
    private OnItemLongClickListener<T> onItemLongClickListener = null;
    private View.OnClickListener listener = null;
    private View.OnClickListener onClickListener = null;
    private View.OnLongClickListener onLongClickListener = null;

    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int position, T item, int viewType);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View itemView, int position, T item, int viewType);
    }

    public BaseRecyclerViewAdapter() {
        this(-1, true, true);
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutId) {
        this(layoutId, true, true);
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutId, boolean itemClickEnable, boolean itemLongClickEnable) {
        this.layoutId = layoutId;
        this.itemClickEnable = itemClickEnable;
        this.itemLongClickEnable = itemLongClickEnable;
        items = new ArrayList<>();
    }

    public List<T> getItems() {
        return items;
    }

    public T getItemAt(int position) {
        return position < getItemCount() ? items.get(position) : null;
    }

    public void setItems(List<T> items) {
        this.items = items;
        ensureListNotNull();
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        addItems(getItemCount(), items);
    }

    @Override
    public void addItems(@IntRange(from = 0) int position, List<T> items) {
        if (items == null || items.isEmpty())
            return;
        ensureListNotNull();
        this.items.addAll(items);
        notifyItemRangeInserted(position, items.size());
    }

    @Override
    public void addItem(T item) {
        addItem(getItemCount(), item);
    }

    @Override
    public void addItem(int position, T item) {
        if (item == null)
            return;
        ensureListNotNull();
        this.items.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public void removeItem(T item) {
        if (item == null)
            return;
        int position = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if (item == getItemAt(i)){
                position = i;
                break;
            }
        }
        removeItem(position);
    }

    @Override
    public void removeItem(int position) {
        if (position < 0)
            return;
        items.remove(position);
        notifyItemRemoved(position);
    }

    private void ensureListNotNull(){
        if (this.items == null)
            this.items = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnViewClickListener(OnItemClickListener<T> onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public View createView(@NonNull ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    @Override
    public final void onBindViewHolder(@NonNull final VH holder, int position) {
        holder.itemView.setTag(position);
        addOnItemClickListener(holder);
        addOnItemLongClickListener(holder);
        bindViewHolder(holder, position, getItemAt(position), getItemViewType(position));
    }

    public abstract void bindViewHolder(@NonNull final VH holder, int position, T item, int viewType);

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void addOnItemClickListener(@NonNull final VH holder){
        if (!itemClickEnable) return;
        if (onClickListener == null){
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, position, getItemAt(position), getItemViewType(position));
                }
            };
        }
        holder.itemView.setOnClickListener(onClickListener);
    }

    private void addOnItemLongClickListener(@NonNull final VH holder){
        if (!itemLongClickEnable) return;
        if (onLongClickListener == null){
            onLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = (int) v.getTag();
                    if (onItemLongClickListener != null) {
                        return onItemLongClickListener.onItemLongClick(v, position, getItemAt(position), getItemViewType(position));
                    }
                    return false;
                }
            };
        }
        holder.itemView.setOnLongClickListener(onLongClickListener);
    }

    public final void addOnViewClickListener(@NonNull final VH holder, @IdRes int id){
        //listener使用全局模式，避免每次都创建而造成内存d抖动
        if (listener == null)
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (onViewClickListener != null)
                        onViewClickListener.onItemClick(v, position, getItemAt(position), getItemViewType(position));
                }
            };
        View view = holder.itemView.findViewById(id);
        if (view != null)
            view.setOnClickListener(listener);
    }
}
