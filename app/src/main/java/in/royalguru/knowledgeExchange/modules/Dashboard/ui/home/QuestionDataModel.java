package in.royalguru.knowledgeExchange.modules.Dashboard.ui.home;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Kalmeshwar on 03 Oct 2019 at 15:22.
 */
public class QuestionDataModel {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    private String cat_id;
    @SerializedName("questions")
    private ArrayList<QuestionModel> questionModelList;


    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<QuestionModel> getQuestionModelList() {
        return questionModelList;
    }

    public void setQuestionModelList(ArrayList<QuestionModel> questionModelList) {
        this.questionModelList = questionModelList;
    }

    public static class QuestionModel {


        private String question;
        private String priority;
        private String id;
        @SerializedName("posted_by")
        private String postedBy;
        @SerializedName("user_type")
        private String userType;
        private String rating;
        ArrayList<AnswerModel> answers;

        private String cat_id;
        private String cat_name;
        private boolean isSelected = false;


        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPostedBy() {
            return postedBy;
        }

        public void setPostedBy(String postedBy) {
            this.postedBy = postedBy;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public ArrayList<AnswerModel> getAnswers() {
            return answers;
        }

        public void setAnswers(ArrayList<AnswerModel> answers) {
            this.answers = answers;
        }


    }


    public static class AnswerModel {

        private String id;
        private String answer;
        private String posted_by;
        private String user_type;
        private String priority;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getPosted_by() {
            return posted_by;
        }

        public void setPosted_by(String posted_by) {
            this.posted_by = posted_by;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }
    }


}

