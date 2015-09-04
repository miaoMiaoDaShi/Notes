package com.lguipeng.notes.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.WindowManager;

import com.lguipeng.notes.R;
import com.lguipeng.notes.injector.component.ActivityComponent;
import com.lguipeng.notes.utils.ThemeUtils;
import com.lguipeng.notes.utils.ToolbarUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by lgp on 2015/5/24.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ActivityComponent mActivityComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        initWindow();
        initializeDependencyInjector();
        setContentView(getLayoutView());
        ButterKnife.inject(this);
        initToolbar();
    }

    private void initTheme(){
        ThemeUtils.Theme theme = ThemeUtils.getCurrentTheme(this);
        ThemeUtils.changTheme(this, theme);
    }

    public int getColor(@ColorRes int res){
        if (res <= 0)
            throw new IllegalArgumentException("resource id can not be less 0");
        return getResources().getColor(res);
    }

    @TargetApi(19)
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getStatusBarColor());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    protected void initToolbar(Toolbar toolbar){
        ToolbarUtils.initToolbar(toolbar, this);
    }

    public int getStatusBarColor(){
        return getColorPrimary();
    }

    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public int getDarkColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 增加了默认的返回finish事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected void initToolbar(){

    }

    protected void initializeDependencyInjector(){

    }

    protected abstract @LayoutRes int getLayoutView();

}
