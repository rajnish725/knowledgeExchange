package in.royalguru.knowledgeExchange.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.menu.MenuModel;
import in.royalguru.knowledgeExchange.modules.Dashboard.ui.home.QuestionDataModel;

/**
 * Created by Rajnish yadav on 01 Nov 2019 at 16:44.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private String TAG = DatabaseHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "question";
    private static final String TABLE_QUESTION = "tbl_question_list";
    private static final String TABLE_ANSWER = "table_answer";
    private static final String QUEST_CATEGORY = "quest_category";
    private static final String KEY_ID = "id";
    private SQLiteDatabase database;


    /* todo table field name
     * */
    private String QUESTION = "question";
    private String PRIORITY = "priority";
    private String ID = "id";
    private String POSTEDBY = "postedBy";
    private String USERTYPE = "userType";
    private String RATING = "rating";
    private String CAT_ID = "cat_id";
    private String CAT_NAME = "cat_name";

// TODO: 1/11/19 answer field

    private String ANS_ID = "ans_id";
    private String ANSWER = "answer";
    private String POSTED_BY = "posted_by";
    private String USER_TYPE = "user_type";
    private String ANS_PRIORITY = "ans_priority";
    private String QUEST_ID = "quest_id";


    // TODO: 16/11/19 question category name

    private String CATEGOY_ID = "cat_id";
    private String QUEST_CATEGORY_NAME = "quest_category_name";


    //todo question table
    String CREATE_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + "("
            + ID + " TEXT,"  //1
            + QUESTION + " TEXT DEFAULT NULL,"  //2
            + PRIORITY + " TEXT DEFAULT NULL,"//3
            + POSTEDBY + " TEXT DEFAULT NULL,"//4
            + USERTYPE + " TEXT DEFAULT NULL,"//5
            + RATING + " TEXT DEFAULT NULL,"//6
            + CAT_ID + " TEXT DEFAULT NULL,"//7
            + CAT_NAME + " TEXT DEFAULT NULL" + ")";  //8


    // TODO: 16/11/19 answer table
    String CREATE_ANSWER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWER + "("
            + ANS_ID + " TEXT DEFAULT NULL,"  //1
            + ANSWER + " TEXT DEFAULT NULL,"  //2
            + POSTED_BY + " TEXT DEFAULT NULL,"//3
            + USER_TYPE + " TEXT DEFAULT NULL,"//4
            + ANS_PRIORITY + " TEXT DEFAULT NULL,"
            + QUEST_ID + " TEXT DEFAULT NULL"//5
            + ")";


    // TODO: 16/11/19 question type category tab

    String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS  " + QUEST_CATEGORY + "("
            + CATEGOY_ID + " TEXT DEFAULT NULL,"
            + QUEST_CATEGORY_NAME + " TEXT DEFAULT NULL"
            + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_ANSWER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);

        Debug.printLogError(TAG, "db_create_table: " + CREATE_QUESTION_TABLE);
        Debug.printLogError(TAG, "db_create_table: " + CREATE_ANSWER_TABLE);

        Debug.printLogError(TAG, "db_create_table: " + CREATE_CATEGORY_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_QUESTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ANSWER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_CATEGORY_TABLE);

        Debug.printLogError(TAG, "db_drop_table: " + CREATE_QUESTION_TABLE);
        Debug.printLogError(TAG, "db_drop_table: " + CREATE_ANSWER_TABLE);

        //create new table(s)
        onCreate(db);

    }

    public boolean saveCategoryList(ArrayList<MenuModel> menuList) {
        database = this.getWritableDatabase();

        if (!menuList.isEmpty()) {
            for (int i = 0; i < menuList.size(); i++) {
                MenuModel model = menuList.get(i);
                ContentValues categoryValues = new ContentValues();
                categoryValues.put(CAT_ID, model.getId());
                categoryValues.put(QUEST_CATEGORY_NAME, model.getName());

                database.insert(CREATE_CATEGORY_TABLE, null, categoryValues);


            }
            database.close();
            return true;
        }


        return false;
    }

    public boolean saveQuestionData(QuestionDataModel dataModel, Context mContext) {
        database = this.getWritableDatabase();


        if (!dataModel.getQuestionModelList().isEmpty()) {
            for (int i = 0; i < dataModel.getQuestionModelList().size(); i++) {


                ContentValues questionValues = new ContentValues();

                QuestionDataModel.QuestionModel questionModel = dataModel.getQuestionModelList().get(i);
                questionValues.put(ID, questionModel.getId());
                questionValues.put(QUESTION, questionModel.getQuestion());
                questionValues.put(PRIORITY, questionModel.getPriority());
                questionValues.put(POSTEDBY, questionModel.getPostedBy());
                questionValues.put(RATING, questionModel.getRating());
                questionValues.put(CAT_ID, questionModel.getCat_id());
                questionValues.put(CAT_NAME, questionModel.getCat_name());

                if (!questionModel.getAnswers().isEmpty()) {
                    for (int j = 0; j < questionModel.getAnswers().size(); j++) {
                        ContentValues answerValue = new ContentValues();
                        QuestionDataModel.AnswerModel answerModel = questionModel.getAnswers().get(j);

                        answerValue.put(ANS_ID, answerModel.getId());
                        answerValue.put(ANSWER, answerModel.getAnswer());
                        answerValue.put(POSTED_BY, answerModel.getPosted_by());
                        answerValue.put(USER_TYPE, answerModel.getUser_type());
                        answerValue.put(ANS_PRIORITY, answerModel.getPriority());
                        answerValue.put(QUEST_ID, questionModel.getId());

                        database.insert(TABLE_ANSWER, null, answerValue);


                    }
                }

                database.insert(TABLE_QUESTION, null, questionValues);
            }
        }
        database.close();
        return true;
    }

    public boolean deleteAllTable() {
        database = this.getWritableDatabase();
        database.execSQL("delete from " + TABLE_QUESTION);
        database.execSQL("delete from " + TABLE_ANSWER);
        database.close();
        return true;
    }


    public ArrayList<QuestionDataModel.QuestionModel> fetchAllQuestionData() {

        ArrayList<QuestionDataModel.QuestionModel> questionList = new ArrayList<>();
        Debug.printLogError(TAG, "--------up-----------------------------");

        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] data = null;

        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                QuestionDataModel.QuestionModel questionModel = new QuestionDataModel.QuestionModel();
                questionModel.setCat_id(cursor.getString(cursor.getColumnIndex(CAT_ID)));
                questionModel.setCat_name(cursor.getString(cursor.getColumnIndex(CAT_NAME)));
                questionModel.setId(cursor.getString(cursor.getColumnIndex(ID)));
                questionModel.setQuestion(cursor.getString(cursor.getColumnIndex(QUESTION)));
                questionModel.setPostedBy(cursor.getString(cursor.getColumnIndex(POSTEDBY)));
                questionModel.setRating(cursor.getString(cursor.getColumnIndex(RATING)));
                questionModel.setUserType(cursor.getString(cursor.getColumnIndex(USERTYPE)));
                questionModel.setPriority(cursor.getString(cursor.getColumnIndex(PRIORITY)));
                Debug.printLogError(TAG, "temp_" + cursor.getString(cursor.getColumnIndex(QUESTION)) + "-----------------------------");

                questionList.add(questionModel);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return questionList;
    }

    public ArrayList<QuestionDataModel.AnswerModel> fetchAnswer(String quest_id) {

        ArrayList<QuestionDataModel.AnswerModel> answerList = new ArrayList<>();
        String answerQuery = "SELECT * FROM " + CREATE_ANSWER_TABLE + " WHERE " + QUEST_ID + "=" + quest_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(answerQuery, null);


        if (cursor.moveToFirst()) {
            do {
                QuestionDataModel.AnswerModel answerModel = new QuestionDataModel.AnswerModel();
                answerModel.setId(cursor.getString(cursor.getColumnIndex(ANS_ID)));
                answerModel.setAnswer(cursor.getString(cursor.getColumnIndex(ANSWER)));
                answerModel.setPosted_by(cursor.getString(cursor.getColumnIndex(POSTED_BY)));
                answerModel.setPriority(cursor.getString(cursor.getColumnIndex(PRIORITY)));
                answerModel.setUser_type(cursor.getString(cursor.getColumnIndex(USER_TYPE)));

                answerList.add(answerModel);

            } while (cursor.moveToNext());

        }
        return answerList;
    }


}
