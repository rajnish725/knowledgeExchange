package in.royalguru.knowledgeExchange.retrofit;

import java.util.ArrayList;

import in.royalguru.knowledgeExchange.menu.MenuModel;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.QuestionDataModel;
import in.royalguru.knowledgeExchange.modules.addNewQuestion.addNewQuestionModel;
import in.royalguru.knowledgeExchange.modules.authentication.activities.LoginUserModel;
import in.royalguru.knowledgeExchange.modules.model.CommonModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Kalmeshwar on 3/14/2018.
 */

public interface APIInterface {
    @FormUrlEncoded
    @POST("get-questions-list")
    Call<QuestionDataModel> getAllQuestion(
            @Field("trans_from") String trans_from,
            @Field("cat_id") String cat_id);


    @POST("vendor/verify-mobile")
    Call<QuestionDataModel> addQuestion(@Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("login-user")
    Call<LoginUserModel> loginUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("login_type") String login_type);

    @FormUrlEncoded
    @POST("add-question-with-ans")
    Call<addNewQuestionModel> addQuestionWithAnswer(
            @Field("question") String question,
            @Field("cat_id") String cat_id,
            @Field("ans_flag") String ans_flag,
            @Field("answer") String answer,
            @Field("posted_by") String posted_by,
            @Field("priority") String priority);


    @FormUrlEncoded
    @POST("rate-question")
    Call<addNewQuestionModel> rateQuestion(
            @Field("quest_id") String quest_id,
            @Field("rate") String rate,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("update-question")
    Call<CommonModel> updateQuestion(@Field("id") String id,
                                     @Field("question") String question,
                                     @Field("priority") String priority);


    @FormUrlEncoded
    @POST("post-answer")
    Call<MenuModel> UpdateAnswerForQuestion(@Field("question_id") String quest_id,
                                            @Field("answer") String answer,
                                            @Field("posted_by") String posted_by,
                                            @Field("priority") String priority);

    //http://kalmesh.in/knowledge_exchange/public/get-categories?


    @FormUrlEncoded
    @POST("get-categories")
    Call<ArrayList<MenuModel>> getAllCategory(@Field("trans_from") String trans_from);


}