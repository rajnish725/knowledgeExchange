package in.royalguru.knowledgeExchange.modules.dialogs.Activitites;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.listeners.AppOperationsListener;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;
import in.royalguru.knowledgeExchange.modules.dialogs.adapter.CategoryAdapter;
import in.royalguru.knowledgeExchange.modules.dialogs.model.QuestionCategoryModel;

/**
 * Created by Kalmeshwar on 15 Oct 2019 at 15:55.
 */
public class DialogSelectCategory extends DialogFragment {
    private Context mContext;
    private ArrayList<QuestionCategoryModel> categoryList;
    private AppOperationsListener operationsListener;

    public DialogSelectCategory newInstance(Context mContext, ArrayList<QuestionCategoryModel> categoryList
            , AppOperationsListener operationsListener) {
        DialogSelectCategory category = new DialogSelectCategory();
        category.categoryList = categoryList;
        category.mContext = mContext;
        category.operationsListener = operationsListener;

        return category;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_category, container, false);
        mContext = getActivity();
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View itemView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(itemView, savedInstanceState);


        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getDialog().getWindow().setDimAmount(0.7f);
            getDialog().setCanceledOnTouchOutside(false);
            this.setCancelable(true);
            RecyclerView recycler_category;
            Button btn_cancle, btn_continue;

            recycler_category = itemView.findViewById(R.id.recycler_category);
            btn_continue = itemView.findViewById(R.id.btn_continue);
            btn_cancle = itemView.findViewById(R.id.btn_cancel);

            recycler_category.setLayoutManager(new LinearLayoutManager(mContext));
            recycler_category.setAdapter(new CategoryAdapter(mContext, categoryList, (OnItemClickListener) this));
        }
    }


}
