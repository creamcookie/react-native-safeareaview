
package cc.creamcookie.safeareaview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.*;
import com.facebook.react.bridge.*;

public class RNCSafeAreaViewModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Activity mActivity;
    private boolean isLight;

    public RNCSafeAreaViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public void initialize() {
        super.initialize();
        mActivity = reactContext.getCurrentActivity();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Window w = mActivity.getWindow();
                    w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                    w.setStatusBarColor(Color.TRANSPARENT);
                }
            }
        });
    }

    public float pxToDp(float px){
        return px / ((float) this.reactContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @ReactMethod
    public void setTintLight(final boolean isLight) {
        this.isLight = isLight;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Window w = mActivity.getWindow();
                    if (isLight) {
                        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                    else {
                        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                    }
                }
            }
        });
    }

    @ReactMethod
    public void getTintLight(final Promise promise) {
        promise.resolve(this.isLight);
    }

    @ReactMethod
    public void getPadding(final Promise promise) {

        final Window w = mActivity.getWindow();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    boolean forceTop = false;

                    boolean isPhone = (reactContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
                    int _o = reactContext.getResources().getConfiguration().orientation;
                    if (_o == Configuration.ORIENTATION_LANDSCAPE && isPhone) {
                        forceTop = true;
                        w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        w.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    } else {
                        forceTop = false;
                        w.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                        w.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }

                    WritableMap map = Arguments.createMap();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        WindowInsets insets = w.getDecorView().getRootWindowInsets();
                        map.putDouble("top", forceTop ? 0 : pxToDp(insets.getStableInsetTop()));
                        map.putDouble("left", pxToDp(insets.getStableInsetLeft()));
                        map.putDouble("right", pxToDp(insets.getStableInsetRight()));
                        map.putDouble("bottom", pxToDp(insets.getStableInsetBottom()));
                    }
                    else {
                        map.putDouble("top", 0.0d);
                        map.putDouble("left", 0.0d);
                        map.putDouble("right", 0.0d);
                        map.putDouble("bottom", 0.0d);
                    }
                    promise.resolve(map);

                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    promise.reject(ex);
                }
            }
        });


    }

    @Override
    public String getName() {
        return "RNCSafeAreaView";
    }
}
