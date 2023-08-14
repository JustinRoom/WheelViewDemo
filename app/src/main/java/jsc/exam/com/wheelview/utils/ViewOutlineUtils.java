package jsc.exam.com.wheelview.utils;

import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

public class ViewOutlineUtils {

    /**
     * 圆
     *
     * @param view
     */
    public static void applyCircleOutline(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        int min = Math.min(view.getWidth(), view.getHeight());
                        int l = (view.getWidth() - min) / 2;
                        int t = (view.getHeight() - min) / 2;
                        outline.setOval(l, t, l + min, t + min);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    /**
     * 椭圆
     *
     * @param view
     */
    public static void applyEllipticOutline(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        float radius = Math.min(view.getWidth(), view.getHeight()) / 2.0f;
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    /**
     * 椭圆
     *
     * @param view
     */
    public static void applyHorizontalEllipticOutline(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        float radius = view.getHeight() / 2.0f;
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    /**
     * 圆角
     *
     * @param view
     * @param radius
     */
    public static void applyRoundOutline(View view, final float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    /**
     * 左下、右下圆角
     *
     * @param view
     * @param radius
     */
    public static void applyBottomRoundOutline(View view, final float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        int bottomExtra = (int) (view.getHeight() + radius + 0.5f);
                        outline.setRoundRect(0, 0 - bottomExtra, view.getWidth(), view.getHeight(), radius);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    /**
     * 左上、右上圆角
     *
     * @param view
     * @param radius
     */
    public static void applyTopRoundOutline(View view, final float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        int bottomExtra = (int) (view.getHeight() + radius + 0.5f);
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight() + bottomExtra, radius);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    /**
     * 左上、右上圆角
     *
     * @param view
     */
    public static void applyTopRoundOutlineLR(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        float radius = view.getHeight() / 2.0f;
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }


    public static void applyConvexPathOutline(View view, final float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() > 0 && view.getHeight() > 0) {
                        Path path = new Path();
                        path.moveTo(0, view.getHeight());
                        path.lineTo(0, radius);
                        path.quadTo(0, 0, radius, 0);
                        path.lineTo(view.getWidth() - radius, 0);
                        path.quadTo(view.getWidth(), 0, view.getWidth(), radius);
                        path.lineTo(view.getWidth(), view.getHeight());
                        path.close();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q || path.isConvex()) {
                            outline.setConvexPath(path);
                        }
                    }
                }
            });
            view.setClipToOutline(true);
        }
    }

    public static void clearOutline(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(null);
            view.setClipToOutline(false);
        }
    }
}
