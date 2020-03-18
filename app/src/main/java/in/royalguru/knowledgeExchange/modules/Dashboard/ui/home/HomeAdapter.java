package in.royalguru.knowledgeExchange.modules.Dashboard.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.components.SelectorLayout;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;

import static in.royalguru.knowledgeExchange.enums.EnumClicks.CELL_CLICK;
import static in.royalguru.knowledgeExchange.enums.EnumClicks.RATTING;

/**
 * Created by Kalmeshwar on 30 Sep 2019 at 13:11.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
    private static final String TAG = HomeAdapter.class.getSimpleName();
    private OnItemClickListener itemClickListener;
    private Context mContext;
    private ArrayList<QuestionDataModel.QuestionModel> questionList;


    public HomeAdapter(Context mContext, ArrayList<QuestionDataModel.QuestionModel> questionList, OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.questionList = questionList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_row, parent, false);

        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {

        QuestionDataModel.QuestionModel questionModel = questionList.get(position);
        if (questionModel.isSelected()) {
            holder.selector_layout.setSelectorVisibility(true);
        } else {
            holder.selector_layout.setSelectorVisibility(false);
        }


        holder.txt_question.setText(questionModel.getQuestion());
        holder.txt_post_by.setText(questionModel.getPostedBy());

        try {
            if (questionModel.getRating() != null && !questionModel.getRating().isEmpty())
                holder.ratingBar.setRating(Integer.parseInt(questionModel.getRating()));
        } catch (Exception e) {
            Debug.printLogError(TAG, "somethiong--------" + e.getMessage());
        }


        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClickListener(CELL_CLICK, null, position, questionList.get(holder.getAdapterPosition()),
                    "", null, false);
//                itemClickListener.onItemClickListener(CELL_CLICK, null, position, holder.ratingBar.getNumStars(), null, null, false);

        });

        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Debug.printLogError(TAG, "onRatingChanged: " + rating);
                if (fromUser)
                    itemClickListener.onItemClickListener(RATTING, null, position, questionList.get(holder.getAdapterPosition()),
                            rating, null, false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        TextView txt_question, txt_post_by;
        SelectorLayout selector_layout;


        RatingBar ratingBar;

        HomeHolder(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            txt_question = itemView.findViewById(R.id.txt_question);
            txt_post_by = itemView.findViewById(R.id.txt_post_by);
            selector_layout = itemView.findViewById(R.id.selector_layout);
        }
    }
}
