package in.royalguru.knowledgeExchange.modules.addNewQuestion;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;

import in.kalmesh.projectbase.AppActivity;
import in.kalmesh.projectbase.Debug;
import in.kalmesh.projectbase.Validator;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.enums.EnumClicks;
import in.royalguru.knowledgeExchange.enums.ScreenTypes;
import in.royalguru.knowledgeExchange.listeners.AppOperationsListener;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;
import in.royalguru.knowledgeExchange.modules.dialogs.adapter.CategoryAdapter;
import in.royalguru.knowledgeExchange.modules.dialogs.model.QuestionCategoryModel;
import in.royalguru.knowledgeExchange.retrofit.APICallBack;
import in.royalguru.knowledgeExchange.retrofit.APIService;
import in.royalguru.knowledgeExchange.retrofit.ServerConstants;
import in.royalguru.knowledgeExchange.sessiondata.SessionManager;
import in.royalguru.knowledgeExchange.utils.Utility;
import retrofit2.Call;

/**
 * Created by Kalmeshwar on 09 Oct 2019 at 15:50.
 */
public class AddNewQuestionActivity extends AppActivity implements View.OnClickListener, AppOperationsListener, OnItemClickListener {
    private static String TAG = AddNewQuestionActivity.class.getSimpleName();
    private String CATEGORY = "-select category-";

    private TextView txt_screen_name, txt_category;
    private EditText edt_question, edt_answer;
    QuestionCategoryModel categoryModel;
    private LinearLayout lin_category;
    private ProgressDialog pDialog;
    private CategoryAdapter adapter;
    private CheckBox checkbox_yes;
    private ImageView img_menu;
    private String question;
    private Button btn_save;
    private String answer;
    private Context mContext;
    ArrayList<QuestionCategoryModel> quesrionList;
    Dialog dialog = null;

    @Override
    public int onCreateView() {
        return R.layout.app_add_new_question;
    }

    @Override
    protected void preInitializeMethod() {
        mContext = this;
        overridePendingTransition(R.anim.slide_in_normal, R.anim.slide_out_normal);

    }

    @Override
    protected void initUI() {
        txt_screen_name = findViewById(R.id.txt_screen_name);
        lin_category = findViewById(R.id.lin_category);
        txt_category = findViewById(R.id.txt_category);
        edt_answer = findViewById(R.id.edt_answer);
        edt_question = findViewById(R.id.edt_question);
        txt_screen_name.setText("Add new Question");
        btn_save = findViewById(R.id.btn_save);
        checkbox_yes = findViewById(R.id.checkbox_yes);
        img_menu = findViewById(R.id.img_menu);
        img_menu.setImageResource(R.drawable.icon_back_arrow_vector);
    }

    @Override
    protected void postInitializeMethod() {


        img_menu.setOnClickListener(this);
        lin_category.setOnClickListener(this);
        txt_category.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        quesrionList = addQuestionCategory();
        checkbox_yes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edt_answer.setVisibility(View.VISIBLE);

            } else {
                edt_answer.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu:
                onBackPressed();
                break;
            case R.id.txt_category:
               /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                DialogSelectCategory dialogSelectCategory = new DialogSelectCategory().newInstance(mContext, addQuestionCategory(), this);
                dialogSelectCategory.show(ft, "category");*/
                dialogPin();
                break;
            case R.id.btn_save:
                validateField();
                break;


        }
    }

    private void validateField() {
        if (!txt_category.getText().toString().equalsIgnoreCase(CATEGORY)) {
            if (Validator.getInstance().isNotEmpty(edt_question.getText().toString())) {
                CATEGORY = txt_category.getText().toString().trim();
                lin_category.setBackgroundResource(R.drawable.bg_black_sld_b_rounded);
                question = edt_question.getText().toString();
                if (checkbox_yes.isChecked()) {
                    if (Validator.getInstance().isNotEmpty(edt_answer.getText().toString().trim())) {
                        answer = edt_answer.getText().toString().trim();
                        addQuestion(question, answer, "1", getCategoryType(CATEGORY));
                        Debug.printLogError(TAG, "------------------------with ansner ");
                    } else {
                        Toast.makeText(mContext, "enter answer or uncheck", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Debug.printLogError(TAG, "------------------------ansers empty");

                    addQuestion(question, "", "", getCategoryType(CATEGORY));
                }

            } else {
                Toast.makeText(mContext, "add question first", Toast.LENGTH_SHORT).show();
            }

        } else {
            lin_category.setBackgroundResource(R.drawable.bg_red_stroke_trn_rounded);
            Debug.printLogError("dialog alert", "");
            Alerter.create((Activity) mContext)
                    .setIcon(R.drawable.icon_alerter_vector)
                    .setText("select question type first!!...")
                    .setBackgroundColorRes(R.color.greyDark)
                    .setTextAppearance(R.style.AlertTextAppearanceStyle)
                    .show();

        }
    }

    void addQuestion(String question, String answer, String ansFlag, String categoryType) {
        if (Utility.getInstance().checkInternetConnection(mContext)) {
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(true);
            pDialog.show();


            APIService.getInstance().init().
                    addQuestionWithAnswer(question, categoryType,
                            ansFlag, answer, SessionManager.getInstance(mContext).getUserId(), "3")
                    .enqueue(new APICallBack<addNewQuestionModel>() {
                        @Override
                        protected void success(addNewQuestionModel model, Call<addNewQuestionModel> request) {
                            closeDialog();
                            if (model.getStatus().equalsIgnoreCase(ServerConstants.SUCCESS_RESPONSE)) {
                                edt_answer.setText("");
                                txt_category.setText("-select category-");
                                edt_question.setText("");
                                Toast.makeText(mContext, "successs", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "faild response", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        protected void failure(String errorMsg, int responseCode) {
                            closeDialog();
                            Debug.printLogError(TAG, "------------------------onFailer");
                        }

                        @Override
                        protected void onFailure(String failureReason) {
                            closeDialog();
                            Debug.printLogError(TAG, "------------------------Failer");
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
    }


    void closeDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


  /*  1=> History
2=> Current Affairs
3=> Computer/IT
4=> Hindi
5=> Mathematics
6=> General Knowledge*/

    ArrayList<QuestionCategoryModel> addQuestionCategory() {
        quesrionList = new ArrayList<>();
        categoryModel = new QuestionCategoryModel();
        categoryModel.setCategory("History");
        categoryModel.setId("1");
        quesrionList.add(categoryModel);

        categoryModel = new QuestionCategoryModel();
        categoryModel.setCategory("Current Affairs");
        categoryModel.setId("2");
        quesrionList.add(categoryModel);

        categoryModel = new QuestionCategoryModel();
        categoryModel.setCategory("Computer/IT");
        categoryModel.setId("3");
        quesrionList.add(categoryModel);

        categoryModel = new QuestionCategoryModel();
        categoryModel.setCategory("Hindi");
        categoryModel.setId("4");
        quesrionList.add(categoryModel);

        categoryModel = new QuestionCategoryModel();
        categoryModel.setCategory("Mathematics");
        categoryModel.setId("5");
        quesrionList.add(categoryModel);

        categoryModel = new QuestionCategoryModel();
        categoryModel.setId("6");
        categoryModel.setCategory("General Knowledge");
        quesrionList.add(categoryModel);
        return quesrionList;
    }


    private String getCategoryType(String categoryType) {
        String type = "";

        for (int i = 0; i < quesrionList.size(); i++) {
            categoryModel = quesrionList.get(i);
            if (categoryModel.getCategory().equalsIgnoreCase(categoryType)) {
                type = categoryModel.getId();
            }

        }
        return type;
    }


    public void dialogPin() {
        Button btn_continue;
        dialog = new Dialog(mContext);
        // dialog.set(android.app.DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_select_category, null, false);
        //*HERE YOU CAN FIND YOU IDS AND SET TEXTS OR BUTTONS*//*
        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setDimAmount(0.7f);
        btn_continue = view.findViewById(R.id.btn_continue);
        Button btn_cancle = view.findViewById(R.id.btn_cancel);
        RecyclerView recycler_category;
        recycler_category = view.findViewById(R.id.recycler_category);
        dialog.show();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.7);
        int height = (int) (displaymetrics.heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);


        recycler_category.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CategoryAdapter(mContext, addQuestionCategory(), this);
        recycler_category.setAdapter(adapter);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < quesrionList.size(); i++) {
                    categoryModel = quesrionList.get(i);
                    if (categoryModel.isSelected()) {
                        txt_category.setText(categoryModel.getCategory());
//                        validateField();
                        dialog.dismiss();
                        break;
                    } else {
//                        Debug.printLogError("dialog alert", "");
//
//                        Alerter.create((Activity) mContext)
//                                .setIcon(R.drawable.icon_alerter_vector)
//                                .setText("select question type first!!...")
//                                .setBackgroundColorRes(R.color.greyDark)
//                                .setTextAppearance(R.style.AlertTextAppearanceStyle)
//                                .show();
                    }
                }

            }

        });

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onListenOperation(ScreenTypes screenType, Object obj1, Object obj2, String stringValue, boolean isChecked) {

    }

    @Override
    public void onItemClickListener(EnumClicks where, View view, int position, Object obj1, Object obj2, Object obj3, boolean isChecked) {

        switch (where) {
            case CELL_CLICK:
                for (int i = 0; i < quesrionList.size(); i++) {
                    categoryModel = quesrionList.get(i);
                    if (position == i) {
                        if (!categoryModel.isSelected())
                            categoryModel.setSelected(true);

                    } else {
                        categoryModel.setSelected(false);
                    }

                    quesrionList.set(i, categoryModel);
                }
                adapter.notifyDataSetChanged();

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_reverse, R.anim.slide_out_reverse);
    }
}
