package by.project.dartlen.proofofconcept.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.project.dartlen.proofofconcept.App;

import static com.google.android.gms.common.internal.zzbq.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView,
                          @NonNull FirebaseAuth firebaseAuth){
        mLoginView          = checkNotNull(loginView, "loginView cannot be null!");
        mLoginView.setAuth(firebaseAuth);
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onClickedSignIn(String login, String password) {
        if(isValidEmail(login) && password.length()>=6) {
            mLoginView.showProgress();
            mLoginView.signin(login, password);
        }
        else {
            mLoginView.showDialog("Incorrect username or password.");
        }
    }

    public static boolean isValidEmail(String email)
    {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void signInCompleted(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            mLoginView.hideProgress();
            App.INSTANCE.getRouter().navigateTo("admin");
        } else {
            mLoginView.hideProgress();
            mLoginView.showDialog("Authentication failed.");
        }
    }
}
