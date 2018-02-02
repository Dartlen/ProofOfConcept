package by.project.dartlen.proofofconcept.admin;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.data.ProductRepository;
import by.project.dartlen.proofofconcept.data.model.Product;
import by.project.dartlen.proofofconcept.data.remote.GetProductsCallback;

import static com.google.android.gms.common.internal.zzbq.checkNotNull;

public class AdminProductPresenter implements AdminProductContract.Presenter {

    private ProductRepository mProductRepository;
    private AdminProductContract.View mAdminProductView;
    private FirebaseAuth mAuth;

    public AdminProductPresenter(@NonNull ProductRepository productRepository, @NonNull AdminProductContract.View adminProductView,
                            @NonNull FirebaseAuth firebaseAuth){
        mProductRepository = checkNotNull(productRepository, "repository cannot be null");
        mAdminProductView       = checkNotNull(adminProductView, "galleryView cannot be null!");
        mAuth              = checkNotNull(firebaseAuth, "firebase cannot be null!");
        mAdminProductView.setPresenter(this);
    }

    @Override
    public void start() {
        loadingListProducts();
    }

    public void loadingListProducts(){
        mProductRepository.getProducts(new GetProductsCallback() {
            @Override
            public void onProductsDataLoaded(List<Product> dataProducts) {
                mAdminProductView.showProducts(dataProducts);
            }

            @Override
            public void onDataNotAvailable(String error) {

            }
        });
    }

    @Override
    public void fabOnClicked() {
        App.INSTANCE.getRouter().navigateTo("newproduct");
    }
}
