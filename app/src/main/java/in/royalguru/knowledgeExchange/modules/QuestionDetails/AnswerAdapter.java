package in.royalguru.knowledgeExchange.modules.QuestionDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.kalmesh.projectbase.AppActivity;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.HomeAdapter;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.QuestionDataModel;

/**
 * Created by Kalmeshwar on 06 Oct 2019 at 11:13.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    private ArrayList<QuestionDataModel.AnswerModel> answerList;
    private Context mContext;
    QuestionDataModel.AnswerModel ansModel;

    public AnswerAdapter(ArrayList<QuestionDataModel.AnswerModel> answerList, Context mContext) {
        this.answerList = answerList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_row, parent, false);
        return new AnswerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
        ansModel = answerList.get(position);
        if (ansModel != null) {
            holder.txt_answer.setText(ansModel.getAnswer());
            holder.txt_serial.setText(++position + " :-");
        }

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class AnswerHolder extends RecyclerView.ViewHolder {
        private TextView txt_serial, txt_answer;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            txt_serial = itemView.findViewById(R.id.txt_serial);
            txt_answer = itemView.findViewById(R.id.txt_answer);
        }
    }
}
