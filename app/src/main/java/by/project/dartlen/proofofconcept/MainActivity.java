package by.project.dartlen.proofofconcept;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import by.project.dartlen.proofofconcept.admin.AdminProductFragment;
import by.project.dartlen.proofofconcept.admin.AdminProductPresenter;
import by.project.dartlen.proofofconcept.data.ProductRepository;
import by.project.dartlen.proofofconcept.data.remote.ProductRemoteData;
import by.project.dartlen.proofofconcept.login.LoginFragment;
import by.project.dartlen.proofofconcept.login.LoginPresenter;
import by.project.dartlen.proofofconcept.newproduct.NewProductFragment;
import by.project.dartlen.proofofconcept.newproduct.NewProductPresenter;
import by.project.dartlen.proofofconcept.products.ProductFragment;
import by.project.dartlen.proofofconcept.products.ProductPresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainActivity extends AppCompatActivity {

    private ProductFragment mProductFragment;
    private ProductPresenter mProductPresenter;
    private LoginFragment mLoginFragment;
    private LoginPresenter mLoginPresenter;
    private ProductRepository mProductRepository;
    private FirebaseAuth mAuth;
    private AdminProductFragment mAdminProductFragment;
    private AdminProductPresenter mAdminProductPresenter;
    private NewProductFragment mNewProductFragment;
    private NewProductPresenter mNewProductPresenter;

    @Override
    protected void onResume() {
        super.onResume();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mProductRepository = ProductRepository.getINSTANCE(ProductRemoteData.getINSTANCE());
        App.INSTANCE.getRouter().navigateTo("product");
    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.fragment) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case "product":
                    if(mProductFragment == null) {
                        mProductFragment = ProductFragment.newInstance();
                        mProductPresenter = new ProductPresenter(mProductRepository, mProductFragment, mAuth);
                    }
                    return mProductFragment;
                case "login":
                    if(mLoginFragment == null) {
                        mLoginFragment = LoginFragment.newInstance();
                        mLoginPresenter = new LoginPresenter(mLoginFragment, mAuth);
                    }
                    return mLoginFragment;
                case "admin":
                    if(mAdminProductFragment == null) {
                        mAdminProductFragment = AdminProductFragment.newInstance();
                        mAdminProductPresenter = new AdminProductPresenter(mProductRepository, mAdminProductFragment, mAuth);
                    }
                    return mAdminProductFragment;
                case "newproduct":
                    if(mNewProductFragment == null) {
                        mNewProductFragment = NewProductFragment.newInstance();
                        mNewProductPresenter = new NewProductPresenter(mProductRepository, mNewProductFragment);
                    }
                    return mNewProductFragment;

                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };


}
