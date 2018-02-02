package by.project.dartlen.proofofconcept.moreinfoproduct;

import com.google.firebase.auth.FirebaseAuth;

import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.data.ProductRepository;
import by.project.dartlen.proofofconcept.data.model.Product;

import static com.google.android.gms.common.internal.zzbq.checkNotNull;

public class MoreInfoProductPresenter implements MoreInfoProductContract.Presenter {

    private ProductRepository mRepository;
    private MoreInfoProductContract.View mMoreInfoProductView;
    private Product mProduct;
    private FirebaseAuth mAuth;

    public MoreInfoProductPresenter(ProductRepository repository, MoreInfoProductContract.View view,FirebaseAuth auth, Object data){
        mRepository     = checkNotNull(repository, "repository cann't be null");
        mMoreInfoProductView = checkNotNull(view, "view cann't be null");
        mAuth = checkNotNull(auth, "view cann't be null");
        mProduct = (Product)data;
        mMoreInfoProductView.setPresenter(this);
    }

    @Override
    public void start() {
        mMoreInfoProductView.showProduct(mProduct);
    }

    @Override
    public void onClickedBack() {
        if(mAuth.getUid() != null)
            App.INSTANCE.getRouter().navigateTo("adminproduct");
        else
            App.INSTANCE.getRouter().navigateTo("product");
    }
}
