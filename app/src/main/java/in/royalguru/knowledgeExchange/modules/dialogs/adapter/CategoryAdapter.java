package in.royalguru.knowledgeExchange.modules.dialogs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.enums.EnumClicks;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.QuestionDataModel;
import in.royalguru.knowledgeExchange.modules.dialogs.model.QuestionCategoryModel;

/**
 * Created by Kalmeshwar on 15 Oct 2019 at 16:27.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private ArrayList<QuestionCategoryModel> categoryList;
    private Context mContext;
    OnItemClickListener listener;

    public CategoryAdapter(Context mContext, ArrayList<QuestionCategoryModel> questionList, OnItemClickListener listener) {
        this.categoryList = questionList;
        this.mContext = mContext;
        this.listener = listener;

    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        QuestionCategoryModel model = categoryList.get(position);

        if (model != null) {
            holder.txt_category_name.setText(model.getCategory());
            if (model.isSelected()) {
                holder.img_icon.setVisibility(View.VISIBLE);
            } else {
                holder.img_icon.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClickListener(EnumClicks.CELL_CLICK, null, position, categoryList.get(holder.getAdapterPosition()), null, null, false);
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView txt_category_name;
        private ImageView img_icon;


        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            img_icon = itemView.findViewById(R.id.img_icon);
        }
    }

}
