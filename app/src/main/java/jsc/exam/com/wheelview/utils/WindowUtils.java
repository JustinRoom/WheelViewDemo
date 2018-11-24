package jsc.exam.com.wheelview.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public final class WindowUtils {

    /**
     * Get status bar height.
     *
     * @param context context
     * @return the height of status bar
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = CompatResourceUtils.getDimensionPixelSize(context, resourceId);
        }
        return statusBarHeight;
    }

    /**
     * Get action bar height.
     *
     * @param context context
     * @return the height of action bar
     */
    public static int getActionBarSize(@NonNull Context context) {
        TypedValue typedValue = new TypedValue();
        int[] attribute = new int[]{android.R.attr.actionBarSize};
        TypedArray array = context.obtainStyledAttributes(typedValue.resourceId, attribute);
        int actionBarSize = array.getDimensionPixelSize(0, 0);
        array.recycle();
        return actionBarSize;
    }

    /**
     * Get system selectable item background borderless.
     * @param context context
     * @return selectable item background borderless
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getSelectableItemBackgroundBorderless(Context context){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);
        int[] attribute = new int[]{android.R.attr.selectableItemBackgroundBorderless};
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.resourceId, attribute);
        Drawable drawable = typedArray.getDrawable(0);
        typedArray.recycle();
        return drawable;
    }
}
