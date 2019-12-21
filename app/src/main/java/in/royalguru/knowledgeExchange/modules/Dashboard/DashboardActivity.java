package in.royalguru.knowledgeExchange.modules.Dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import in.kalmesh.projectbase.AppActivity;
import in.kalmesh.projectbase.Debug;
import in.kalmesh.projectbase.Validator;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.application.ApplicationDetails;
import in.royalguru.knowledgeExchange.enums.EnumClicks;
import in.royalguru.knowledgeExchange.enums.ScreenTypes;
import in.royalguru.knowledgeExchange.listeners.AppOperationsListener;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;
import in.royalguru.knowledgeExchange.menu.MenuAdapter;
import in.royalguru.knowledgeExchange.menu.MenuModel;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.FragmentHome;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.QuestionDataModel;
import in.royalguru.knowledgeExchange.modules.QuestionDetails.QuestionDetailsActivity;
import in.royalguru.knowledgeExchange.modules.addNewQuestion.AddNewQuestionActivity;
import in.royalguru.knowledgeExchange.modules.addNewQuestion.addNewQuestionModel;
import in.royalguru.knowledgeExchange.modules.authentication.activities.LoginActivity;
import in.royalguru.knowledgeExchange.retrofit.APICallBack;
import in.royalguru.knowledgeExchange.retrofit.APIService;
import in.royalguru.knowledgeExchange.retrofit.ServerConstants;
import in.royalguru.knowledgeExchange.sessiondata.AppSessionData;
import in.royalguru.knowledgeExchange.sessiondata.GoogleLogin;
import in.royalguru.knowledgeExchange.sessiondata.SessionManager;
import in.royalguru.knowledgeExchange.sqlite.DatabaseHandler;
import in.royalguru.knowledgeExchange.utils.EventBusData;
import in.royalguru.knowledgeExchange.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.royalguru.knowledgeExchange.enums.EnumClicks.LOGIN;
import static in.royalguru.knowledgeExchange.enums.EnumClicks.LOGOUT;

public class DashboardActivity extends AppActivity implements View.OnClickListener, AppOperationsListener, OnItemClickListener {
    private final String TAG = DashboardActivity.class.getSimpleName();
    ArrayList<QuestionDataModel.QuestionModel> questionList = null;
    private TextView txt_screen_name, txt_user_name, txt_email;
    private ImageView img_menu, img_refress;
    private FrameLayout frame_container;
    private DrawerLayout drawer_layout;
    private ImageView img_share;
    private MenuModel menuModel = null;
    ArrayList<MenuModel> menuList = null;
    LinearLayout txt_menu_name_1;
    SessionManager manager;
    RecyclerView recycler_menu;
    private MenuAdapter adapter;
    boolean menuOpen = true;
    private Context mContext;
    GoogleSignInClient mGoogleSignInClient;
    FloatingActionButton fab_button;
    private ProgressDialog pDialog;

    @Override
    public int onCreateView() {
        return R.layout.app_dashboard_sceen;

    }

    @Override
    protected void preInitializeMethod() {
        mContext = this;
    }

    @Override
    protected void initUI() {

        manager = new SessionManager(mContext);

        txt_user_name = findViewById(R.id.txt_user_name);
        txt_email = findViewById(R.id.txt_email);
        img_menu = findViewById(R.id.img_menu);
        fab_button = findViewById(R.id.fab_button);
        img_share = findViewById(R.id.img_logout);
        txt_screen_name = findViewById(R.id.txt_screen_name);
        frame_container = findViewById(R.id.frame_container);

        txt_menu_name_1 = findViewById(R.id.txt_menu_name_1);
        drawer_layout = findViewById(R.id.drawer_layout);
        recycler_menu = findViewById(R.id.recycler_menu);

        //   addMenuData();
        img_menu.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_share.setVisibility(View.VISIBLE);
        recycler_menu.setLayoutManager(new LinearLayoutManager(mContext));


        if (Utility.getInstance().checkInternetConnection(mContext)) {

            getAllCategory();

        } else {
            addMenuListToRecyclerView(ApplicationDetails.getInstance().getGetCategoryList());


        }


    }


    @Override
    protected void postInitializeMethod() {

        openDashboardFragment(FragmentHome.newInstance(this, mContext, ""));
        img_menu.setOnClickListener(this);
        if (new SessionManager(mContext).isLoggedIn()) {
            txt_email.setText(AppSessionData.getInstance().getUseremail());

            txt_user_name.setText(AppSessionData.getInstance().getUserName());
        }

        replaceFragment("ALL");

//        openDashboardFragment(FragmentHome.newInstance(this, mContext, questionList));

        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new SessionManager(mContext).isLoggedIn()) {

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, AddNewQuestionActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    void replaceFragment(String id) {

        String str_id = "ALL";
        if (Validator.getInstance().isNotEmpty(id))
            str_id = id;
        openDashboardFragment(FragmentHome.newInstance(this, mContext, str_id));


    }


    void setMenuData(ArrayList<MenuModel> menuList1) {


        new DatabaseHandler(mContext).saveCategoryList(menuList1);
        ApplicationDetails.getInstance().setGetCategoryList(menuList1);

        menuList1.add(0, new MenuModel(0, "Home", EnumClicks.HOME));
        addMenuListToRecyclerView(menuList1);


    }

    private void setData(QuestionDataModel model) {

        questionList = model.getQuestionModelList();


    }


    // open fragment
    private void openDashboardFragment(Fragment fragment) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_menu:
                if (drawer_layout.isShown()) {
                    drawer_layout.closeDrawers();
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
                break;

            // logout
            case R.id.img_logout:

                if (manager.isLoggedIn()) {
                    dialogBox();
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }

                break;
        }
    }
    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure ?");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            GoogleLogin.getInstance(mContext).init(mContext, DashboardActivity.this);
                            boolean isLogout = GoogleLogin.getInstance(mContext).logout();

                            manager.clearUserSession();
                            txt_user_name.setText("USER");
                            txt_email.setText("user@gmial.com");


                            if (isLogout) {

                                new SessionManager(mContext).clearUserSession();
                            } else {
                                Toast.makeText(mContext, "something went woring", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Debug.printLogError(TAG, "error message on logout   --" + e.getMessage());
                        }


                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onListenOperation(ScreenTypes screenType, Object obj1, Object obj2, String stringValue, boolean isChecked) {

        switch (screenType) {

            case ON_SCROLL:
                if (isChecked) {
                    fab_button.hide();
                } else {
                    fab_button.show();
                }
                break;

            case HOME:

                EventBus.getDefault().postSticky(new EventBusData(obj2, Integer.parseInt(stringValue)));
                Debug.printLogError(TAG, "");
                Intent intent = new Intent(mContext, QuestionDetailsActivity.class);
                startActivity(intent);
                break;

            case SWIPE:
                break;
            case RATTING:
                rateQuestionApi(stringValue, obj1.toString());
                break;
        }


    }

    @Override
    public void onItemClickListener(EnumClicks where, View view, int position, Object obj1, Object obj2, Object obj3, boolean isChecked) {

        if (drawer_layout.isShown()) {
            drawer_layout.closeDrawers();
        }
        if (where.equals(LOGOUT) || where.equals(LOGIN))
            Debug.printLogError(TAG, "");
        else
            txt_screen_name.setText(obj1.toString());

        switch (where) {
            case CELL_CLICK:

                replaceFragment(obj2.toString());
                txt_screen_name.setText(obj1.toString());
                break;
        }
    }


    void rateQuestionApi(String rating, String que_id) {
        if (Utility.getInstance().checkInternetConnection(mContext)) {
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(true);
            pDialog.show();

            APIService.getInstance().init().rateQuestion(que_id,
                    rating,
                    "1").enqueue(new APICallBack<addNewQuestionModel>() {
                @Override
                protected void success(addNewQuestionModel model, Call<addNewQuestionModel> request) {
                    closeDialog();
                    if (model.getStatus().equalsIgnoreCase(ServerConstants.SUCCESS_RESPONSE)) {

                    } else {
                        Toast.makeText(mContext, "oops something wrong...!!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                protected void failure(String errorMsg, int responseCode) {
                    closeDialog();
                }

                @Override
                protected void onFailure(String failureReason) {
                    closeDialog();
                }

                @Override
                protected void closeProgressDialog() {
                    closeDialog();
                }

                @Override
                protected void sessionTimeout() {
                    closeDialog();
                }
            });

        }
    }

    void closeDialog() {

        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    void getAllCategory() {
        if (Utility.getInstance().checkInternetConnection(mContext)) {

            APIService.getInstance().init().getAllCategory("").enqueue(new Callback<ArrayList<MenuModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {


                    setMenuData(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {

                }
            });
        }
    }


    void addMenuListToRecyclerView(ArrayList<MenuModel> menuList) {

        //    Debug.printLogError(TAG, "status success0-----------" + menuList.get(1).getName());
        adapter = new MenuAdapter(mContext, menuList, this);
        recycler_menu.setAdapter(adapter);

    }


}
