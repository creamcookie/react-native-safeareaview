
package cc.creamcookie.safeareaview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.*;
import com.facebook.react.bridge.*;

public class RNCSafeAreaViewModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Activity mActivity;

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
                    w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                    w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                }
            }
        });
    }

    public float pxToDp(float px){
        return px / ((float) this.reactContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @ReactMethod
    public void getPadding(Promise promise) {

        try {
            final Window w = mActivity.getWindow();
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    boolean isPhone = (reactContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
                    int _o = reactContext.getResources().getConfiguration().orientation;
                    if (_o == Configuration.ORIENTATION_LANDSCAPE && isPhone) {
                        w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        w.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    } else {
                        w.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                        w.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                }
            });


            WritableMap map = Arguments.createMap();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                WindowInsets insets = w.getDecorView().getRootWindowInsets();
                map.putDouble("top", this.pxToDp(insets.getStableInsetTop()));
                map.putDouble("left", this.pxToDp(insets.getStableInsetLeft()));
                map.putDouble("right", this.pxToDp(insets.getStableInsetRight()));
                map.putDouble("bottom", this.pxToDp(insets.getStableInsetBottom()));
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

    @Override
    public String getName() {
        return "RNCSafeAreaView";
    }
}
