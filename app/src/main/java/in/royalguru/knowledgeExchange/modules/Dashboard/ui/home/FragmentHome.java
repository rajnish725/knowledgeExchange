package in.royalguru.knowledgeExchange.modules.Dashboard.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import in.kalmesh.projectbase.AppFragment;
import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.enums.EnumClicks;
import in.royalguru.knowledgeExchange.enums.ScreenTypes;
import in.royalguru.knowledgeExchange.listeners.AppOperationsListener;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;
import in.royalguru.knowledgeExchange.retrofit.APICallBack;
import in.royalguru.knowledgeExchange.retrofit.APIService;
import in.royalguru.knowledgeExchange.retrofit.ServerConstants;
import in.royalguru.knowledgeExchange.sqlite.DatabaseHandler;
import in.royalguru.knowledgeExchange.utils.Utility;
import retrofit2.Call;

/**
 * Created by Kalmeshwar on 27 Sep 2019 at 15:22.
 */
public class FragmentHome extends AppFragment implements OnItemClickListener {

    final String TAG = FragmentHome.class.getSimpleName();

    private AppOperationsListener operationsListener;
    private Context mContext;
    private static final String SWIPE = "SWIPE";

    private HomeAdapter homeAdapter;
    SwipeRefreshLayout swipe_refress;
    private RecyclerView recycler_home;
    private ArrayList<QuestionDataModel.QuestionModel> questionList;
    QuestionDataModel.QuestionModel questionModel = null;
    private ProgressDialog pDialog;
    private DatabaseHandler dbHelper;
    private String str_id;

    public static FragmentHome newInstance(AppOperationsListener operationsListener, Context mContext, String id) {
        FragmentHome fragment = new FragmentHome();
        fragment.operationsListener = operationsListener;
        fragment.mContext = mContext;
        fragment.str_id = id;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int onCreateView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.fr_home_screen;
    }

    @Override
    protected void preInitializeMethod() {

        mContext = getActivity();
    }

    @Override
    protected void initUI(View view) {
        recycler_home = view.findViewById(R.id.recycler_home);

        swipe_refress = view.findViewById(R.id.swipe_refress);
        recycler_home = view.findViewById(R.id.recycler_home);
        dbHelper = new DatabaseHandler(mContext);
        Debug.printLogError(TAG, "open fragments");
        recycler_home.setLayoutManager(new LinearLayoutManager(mContext));
    }


    @Override
    protected void postInitializeMethod() {

        getDataFromDatabase();
        recycler_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    operationsListener.onListenOperation(ScreenTypes.ON_SCROLL, null, null, null, true);
                } else if (dy < 0) {
                    operationsListener.onListenOperation(ScreenTypes.ON_SCROLL, null, null, null, false);

                }
            }
        });

        // operationsListener.onListenOperation(ScreenTypes.SWIPE, null, null, SWIPE, false);

        swipe_refress.setOnRefreshListener(() -> {

            getDataFromDatabase();
            swipe_refress.setRefreshing(false);
        });

    }

    @Override
    public void onItemClickListener(EnumClicks where, View view, int position, Object obj1, Object obj2, Object obj3, boolean isChecked) {

        switch (where) {
            case CELL_CLICK:
                operationsListener.onListenOperation(ScreenTypes.HOME, position, obj1, "" + position, false);
                break;


            case RATTING:
                for (int i = 0; i < questionList.size(); i++) {
                    questionModel = questionList.get(i);
                    if (position == i) {

                        operationsListener.onListenOperation(ScreenTypes.RATTING, questionModel.getId(), obj1, obj2.toString(), false);

                    }
                }


                break;
        }


    }

    private void setData(QuestionDataModel model) {
        if (dbHelper.deleteAllTable()) {
            boolean row_affected = dbHelper.saveQuestionData(model, mContext);
            if (row_affected) {
                questionList = model.getQuestionModelList();
                recycler_home.setLayoutManager(new LinearLayoutManager(mContext));
//        recycler_home.setHasFixedSize(true);
                homeAdapter = new HomeAdapter(mContext, questionList, this);
                recycler_home.setAdapter(homeAdapter);
            } else {
                Debug.printLogError(TAG, " oops some thing went wrong");

            }
        } else {
            Debug.printLogError(TAG, " oops some thing went wrong");
        }


    }


    private void getAllQuestion(String cat_id) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(true);
        pDialog.show();


        APIService.getInstance().init().getAllQuestion("Android", cat_id).enqueue(new APICallBack<QuestionDataModel>() {
            @Override
            protected void success(QuestionDataModel model, Call<QuestionDataModel> request) {

                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }
                if (model.getStatus().equalsIgnoreCase(ServerConstants.SUCCESS_RESPONSE)) {
                    setData(model);
                } else {
                    Toast.makeText(mContext, "faild", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void failure(String errorMsg, int responseCode) {
                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }

            }

            @Override
            protected void onFailure(String failureReason) {
                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }


            }

            @Override
            protected void closeProgressDialog() {

                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }


            }

            @Override
            protected void sessionTimeout() {
                closeDialog();


            }
        });


    }

    private void
    getDataFromDatabase() {
        if (Utility.getInstance().checkInternetConnection(mContext)) {
            getAllQuestion(str_id);
        } else {
            homeAdapter = new HomeAdapter(mContext, dbHelper.fetchAllQuestionData(), this);
            recycler_home.setAdapter(homeAdapter);
        }
    }

    private void closeDialog() {

        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
