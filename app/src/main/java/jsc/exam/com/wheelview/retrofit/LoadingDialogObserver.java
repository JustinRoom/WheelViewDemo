package jsc.exam.com.wheelview.retrofit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * create time: 6/7/2018 1:00 PM
 * @author jiangshicheng
 */
public abstract class LoadingDialogObserver<T> implements Observer<T>, DialogInterface.OnCancelListener {

    private final int SHOW_DIALOG = 0x6990;
    private final int HIDE_DIALOG = 0x6991;
    private Dialog loadingDialog;
    private boolean ifShowDialog;
    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SHOW_DIALOG:
                    if (loadingDialog != null && !loadingDialog.isShowing())
                        loadingDialog.show();
                    break;
                case HIDE_DIALOG:
                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();
                    break;
            }
            return true;
        }
    });
    private Disposable disposable;

    /**
     * Constructor.
     */
    public LoadingDialogObserver() {
        this(null);
    }

    /**
     * Constructor.
     * @param loadingDialog loading dialog
     */
    public LoadingDialogObserver(Dialog loadingDialog) {
        this(loadingDialog, true);
    }

    /**
     * Constructor.
     *
     * @param loadingDialog loading dialog
     * @param ifShowDialog  Show loadingDialog if true else not.
     */
    public LoadingDialogObserver(Dialog loadingDialog, boolean ifShowDialog) {
        this.loadingDialog = loadingDialog;
        this.ifShowDialog = ifShowDialog;
        if (loadingDialog != null)
            loadingDialog.setOnCancelListener(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        if (ifShowDialog)
            handler.sendEmptyMessage(SHOW_DIALOG);
        onStart(d);
    }

    @Override
    public void onNext(T t) {
        try {
            onResult(t);
        } catch (Exception e){
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (ifShowDialog)
            handler.sendEmptyMessage(HIDE_DIALOG);
        onException(e);
        onCompleteOrCancel(disposable);
    }

    @Override
    public void onComplete() {
        if (ifShowDialog)
            handler.sendEmptyMessage(HIDE_DIALOG);
        onCompleteOrCancel(disposable);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        disposable.dispose();
        onCompleteOrCancel(disposable);
    }

    /**
     * Show loading dialog here if necessary.
     * @param disposable disposable, the same as the return of {@link io.reactivex.Observable#subscribe(Observer)}.
     */
    public abstract void onStart(Disposable disposable);

    /**
     * Call back the response.
     * @param t response
     */
    public abstract void onResult(T t);

    /**
     * Call back when a exception appears.
     * @param e exception
     */
    public abstract void onException(Throwable e);

    /**
     * Call back when {@link Observer#onComplete()} or loading dialog is canceled.
     * @param disposable disposable, the same as the return of {@link io.reactivex.Observable#subscribe(Observer)}.
     */
    public abstract void onCompleteOrCancel(Disposable disposable);
}
