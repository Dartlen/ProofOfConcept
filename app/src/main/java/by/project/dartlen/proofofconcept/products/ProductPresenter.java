package by.project.dartlen.proofofconcept.products;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.data.ProductRepository;
import by.project.dartlen.proofofconcept.data.model.Product;
import by.project.dartlen.proofofconcept.data.remote.GetProductsCallback;

import static com.google.android.gms.common.internal.zzbq.checkNotNull;

public class ProductPresenter implements ProductContract.Presenter {

    private ProductRepository mProductRepository;
    private ProductContract.View mProductView;
    private FirebaseAuth mAuth;
    private List<Product> dataList;

    public ProductPresenter(@NonNull ProductRepository productRepository, @NonNull ProductContract.View productView,
                            @NonNull FirebaseAuth firebaseAuth){
        mProductRepository = checkNotNull(productRepository, "repository cannot be null");
        mProductView       = checkNotNull(productView, "galleryView cannot be null!");
        mAuth              = checkNotNull(firebaseAuth, "firebase cannot be null!");
        mProductView.setPresenter(this);
    }

    @Override
    public void start() {
        loadingListProducts();
    }

    public void loadingListProducts(){
        mProductRepository.getProducts(new GetProductsCallback() {
            @Override
            public void onProductsDataLoaded(List<Product> dataProducts) {
                dataList = dataProducts;
                mProductView.showProducts(dataProducts);
            }

            @Override
            public void onDataNotAvailable(String error) {

            }
        });
    }

    @Override
    public void signInClicked() {
        App.INSTANCE.getRouter().navigateTo("login");
    }

    @Override
    public void onItemClicked(int position) {
        App.INSTANCE.getRouter().navigateTo("moreinfo", (Object)dataList.get(position));
    }
}
