package by.project.dartlen.proofofconcept.newproduct;

import android.content.Intent;
import android.net.Uri;

import com.google.firebase.storage.UploadTask;

import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.data.ProductRepository;
import by.project.dartlen.proofofconcept.data.model.Product;
import by.project.dartlen.proofofconcept.data.remote.PostProductCallback;

import static com.google.android.gms.common.internal.zzbq.checkNotNull;

public class NewProductPresenter implements NewProductContract.Presenter {

    private NewProductContract.View mNewProductView;
    private ProductRepository mRepository;

    public NewProductPresenter(){}

    public NewProductPresenter(ProductRepository repository, NewProductContract.View view){
        mRepository     = checkNotNull(repository, "repository cann't be null");
        mNewProductView = checkNotNull(view, "view cann't be null");
        mNewProductView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onClickedBack() {
        App.INSTANCE.getRouter().navigateTo("admin");
    }

    @Override
    public void onClickedChooseImage() {
        mNewProductView.showChooser();
    }

    @Override
    public void returnedChoosenData(Intent data) {
        Uri dataImage = data.getData();
        mNewProductView.showPath(dataImage);
        mNewProductView.showImage(dataImage);
    }

    @Override
    public void onClickedUpload() {
        mNewProductView.getProduct();
    }

    @Override
    public void productUpload(Product product) {
        mNewProductView.showProgress();
        mRepository.postProduct(new PostProductCallback() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mNewProductView.hideProgress();
            }

            @Override
            public void onFailure(Exception exception) {
                mNewProductView.hideProgress();
            }

            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }

            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }, product);
    }
}
