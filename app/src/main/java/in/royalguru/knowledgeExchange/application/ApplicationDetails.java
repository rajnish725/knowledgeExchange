package in.royalguru.knowledgeExchange.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import in.royalguru.knowledgeExchange.menu.MenuModel;

/**
 * Created by Kalmeshwar on 12 May 2018 at 17:44.
 */
public class ApplicationDetails extends Application {
    public final String TAG = ApplicationDetails.class.getSimpleName();
    //    public UISettingModel.AppSettingModel appSettingModel = null;
    private static ApplicationDetails instance = null;

    private ArrayList<MenuModel> getCategoryList = null;

    public static ApplicationDetails getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        instance = this;

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Toast.makeText(getContext(), "crash", Toast.LENGTH_SHORT).show();

            }

        });
    }

    public Context getContext() {
        return getApplicationContext();
    }

  /*  public Class getLoginActivity() {
        return AuthenticationActivity.class;
    }*/

    /**
     * call when API token is changed/mismatched
     */
    public void sessionTimeout(Activity mActivity) {
//        FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
//        Fragment prev = mActivity.getFragmentManager().findFragmentByTag("sessionDialog");
//        if (prev != null)
//            ft.remove(prev);
//
//        ft.addToBackStack(null);
//        if (!mActivity.isFinishing())
//            new DialogSessionTimeoutDialog().newInstance(mActivity).show(ft, "sessionDialog");
    }

    public ArrayList<MenuModel> getGetCategoryList() {
        return getCategoryList.size() > 0 ? getCategoryList : null;
    }

    public void setGetCategoryList(ArrayList<MenuModel> getCategoryList) {
        this.getCategoryList = getCategoryList;
    }
}