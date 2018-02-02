package by.project.dartlen.proofofconcept.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.R;

import static com.google.android.gms.common.internal.zzbq.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;
    private FirebaseAuth mAuth;

    @BindView(R.id.login)
    EditText mLogin;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.button)
    Button mButton;

    private static ProgressDialog mDialog;

    public LoginFragment(){}

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this,root);
        mButton.setOnClickListener(this);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        return root;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                App.INSTANCE.getRouter().navigateTo("product");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        mPresenter.onClickedSignIn(mLogin.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(getContext(),
                "",
                "Please wait authorization...");
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

    @Override
    public void setAuth(FirebaseAuth firebaseAuth) {
        mAuth = firebaseAuth;
    }

    @Override
    public void showDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("OK",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void signin(String login, String password) {
        mAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(getActivity(), task -> mPresenter.signInCompleted(task));
    }
}
