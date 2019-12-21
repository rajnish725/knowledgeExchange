package in.royalguru.knowledgeExchange.googleLogin;

/**
 * Created by Kalmeshwar on 10 Oct 2019 at 15:21.
 */
public class UserDetailsModel {
    private String user;
    private String email;
    private String message;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
