package in.royalguru.knowledgeExchange.modules.authentication.activities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Kalmeshwar on 09 Oct 2019 at 14:26.
 */
public class LoginUserModel {
    private String status;
    private String message;
    @SerializedName("data")
    private LoginDataModel loginDataModel;


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

    public LoginDataModel getLoginDataModel() {
        return loginDataModel;
    }

    public void setLoginDataModel(LoginDataModel loginDataModel) {
        this.loginDataModel = loginDataModel;
    }

    class LoginDataModel {
        private String user_id;
        @SerializedName("user_name")
        private String UserName;
        private String email;

        public String getUser_id() {

            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
