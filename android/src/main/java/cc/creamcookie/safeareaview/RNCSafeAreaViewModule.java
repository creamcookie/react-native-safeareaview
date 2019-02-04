
package cc.creamcookie.safeareaview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.facebook.react.bridge.*;

public class RNCSafeAreaViewModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final Activity mActivity;
    private int sHeight = 0, nHeight = 0;

    public RNCSafeAreaViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.mActivity = reactContext.getCurrentActivity();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window w = reactContext.getCurrentActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            w.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(){
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    setWindowInsets();
                    return insets;
                }
            });
        }
    }

    public void setWindowInsets() {
        Resources resources = mActivity.getResources();
        int sHeight = 0, nHeight = 0;
        {
            sHeight = 0;
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                sHeight = (int) this.convertPixelsToDp(resources.getDimensionPixelSize(resourceId));
            }
            this.sHeight = sHeight;
        }
        {
            nHeight = 0;
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                nHeight = (int) this.convertPixelsToDp(resources.getDimensionPixelSize(resourceId));
            }
            this.nHeight = nHeight;
        }
    }

    public float convertPixelsToDp(float px){
        return px / ((float) this.reactContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @ReactMethod
    public void getPadding(Promise promise) {

        try {
            WritableMap map = Arguments.createMap();
            map.putDouble("top", 0.0d);
            map.putDouble("left", 0.0d);
            map.putDouble("right", 0.0d);
            map.putDouble("bottom", 0.0d);

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
