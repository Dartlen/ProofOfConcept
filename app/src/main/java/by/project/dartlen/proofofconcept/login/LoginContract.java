package by.project.dartlen.proofofconcept.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import by.project.dartlen.proofofconcept.base.BasePresenter;
import by.project.dartlen.proofofconcept.base.BaseView;

public interface LoginContract {
    interface View extends BaseView<LoginContract.Presenter>{
        void setAuth(FirebaseAuth firebaseAuth);
        void showProgress();
        void hideProgress();
        void signin(String login, String password);
        void showDialog(String text);
    }
    interface Presenter extends BasePresenter{
        void onClickedSignIn(String login, String password);
        void signInCompleted(@NonNull Task<AuthResult> task);
        void onClickedBack();
    }
}
