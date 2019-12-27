package in.royalguru.knowledgeExchange.modules.QuestionDetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import in.kalmesh.projectbase.AppActivity;
import in.kalmesh.projectbase.Debug;
import in.kalmesh.projectbase.Validator;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.menu.MenuModel;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.QuestionDataModel;
import in.royalguru.knowledgeExchange.retrofit.APICallBack;
import in.royalguru.knowledgeExchange.retrofit.APIService;
import in.royalguru.knowledgeExchange.retrofit.ServerConstants;
import in.royalguru.knowledgeExchange.sessiondata.SessionManager;
import in.royalguru.knowledgeExchange.sqlite.DatabaseHandler;
import in.royalguru.knowledgeExchange.utils.EventBusData;
import in.royalguru.knowledgeExchange.utils.Utility;
import retrofit2.Call;

/**
 * Created by Kalmeshwar on 03 Oct 2019 at 18:10.
 */
public class QuestionDetailsActivity extends AppActivity implements View.OnClickListener {

    private static final String TAG = QuestionDetailsActivity.class.getSimpleName();
    ArrayList<QuestionDataModel.AnswerModel> ansList;
    private ImageView img_menu;
    private TextView txt_screen_name, txt_question, txt_qustion_no, edt_answer;
    private RelativeLayout rel_checkbox, rel_question_details;
    private RecyclerView recycler_answer;
    private CheckBox checkbox_yes;
    private ImageView img_share;
    private String qus_id = null;
    private ProgressDialog pDialog;
    private Button btn_save;
    private DatabaseHandler databaseHandler;


    Context mContext;

    @Override
    public int onCreateView() {
        return R.layout.app_question_details_screen;
    }

    @Override
    protected void preInitializeMethod() {
        mContext = this;


    }

    @Override
    protected void initUI() {
        rel_question_details = findViewById(R.id.rel_question_details);
        rel_checkbox = findViewById(R.id.rel_checkbox);
        checkbox_yes = findViewById(R.id.checkbox_yes);
        recycler_answer = findViewById(R.id.recycler_answer);
        img_menu = findViewById(R.id.img_menu);
        txt_screen_name = findViewById(R.id.txt_screen_name);
        img_share = findViewById(R.id.img_logout);
        img_share.setVisibility(View.VISIBLE);
        txt_question = findViewById(R.id.txt_question);

        btn_save = findViewById(R.id.btn_save);
        edt_answer = findViewById(R.id.edt_answer);
        txt_screen_name.setText("Question Details");
        txt_qustion_no = findViewById(R.id.txt_qustion_no);
        img_menu.setImageResource(R.drawable.icon_back_arrow_vector);


    }

    @Override
    protected void postInitializeMethod() {

        databaseHandler = new DatabaseHandler(mContext);

        recycler_answer.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_answer.setHasFixedSize(true);

        img_share.setOnClickListener(this);


        checkbox_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_answer.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.VISIBLE);

                } else {
                    edt_answer.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);

                }
            }
        });


        btn_save.setOnClickListener(v -> {
            if (SessionManager.getInstance(mContext).isLoggedIn())
                if (Validator.getInstance().isNotEmpty(edt_answer.getText().toString().trim())) {
                    addQuestionApi(qus_id, edt_answer.getText().toString().trim());
                }
        });


        img_menu.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    public void addQuestionApi(String id, String ans) {
        if (Utility.getInstance().checkInternetConnection(mContext)) {

            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(true);
            pDialog.show();
            APIService.getInstance().init().UpdateAnswerForQuestion(id, ans, SessionManager.getInstance(mContext).getUserId(), "4")
                    .enqueue(new APICallBack<MenuModel>() {
                        @Override
                        protected void success(MenuModel model, Call<MenuModel> request) {
                            closeProgressDialog();
                            if (model.getStatus().equals(ServerConstants.SUCCESS_RESPONSE)) {
                                Toast.makeText(mContext, "updated answer", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "something wents wrongs", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        protected void failure(String errorMsg, int responseCode) {
                            closeProgressDialog();

                        }

                        @Override
                        protected void onFailure(String failureReason) {
                            closeProgressDialog();

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

                            closeProgressDialog();
                        }
                    });
        }
    }


    // TODO: 5/10/19 event from home fragment
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData event) {
        QuestionDataModel.QuestionModel model = (QuestionDataModel.QuestionModel) event.getObj1();
        ansList = model.getAnswers();
        qus_id = model.getId();
        txt_question.setText(model.getQuestion());
        txt_qustion_no.setText(model.getId());
        Debug.printLogError("", model.getQuestion());

        if (ansList.size() > 0) {
            Debug.printLogError(TAG, "-----------anser not" + model.getAnswers());

            rel_checkbox.setVisibility(View.GONE);
            recycler_answer.setVisibility(View.VISIBLE);
            AnswerAdapter adapter = new AnswerAdapter(ansList, mContext);
            recycler_answer.setAdapter(adapter);
        } else {
            rel_checkbox.setVisibility(View.VISIBLE);
            recycler_answer.setVisibility(View.GONE);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (EventBus.getDefault() != null) {
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_logout:
                ShareCompat.IntentBuilder.from(QuestionDetailsActivity.this)
                        .setType("text/plain")
                        .setChooserTitle(txt_question.getText().toString())
                        .setText(ansList.get(0).getAnswer())
                        .startChooser();

                break;
        }
    }
}
