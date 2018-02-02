package by.project.dartlen.proofofconcept.newproduct;

import android.content.Intent;
import android.net.Uri;

import by.project.dartlen.proofofconcept.base.BasePresenter;
import by.project.dartlen.proofofconcept.base.BaseView;
import by.project.dartlen.proofofconcept.data.model.Product;

public interface NewProductContract {
    interface View extends BaseView<Presenter> {
        void showChooser();
        void showPath(Uri data);
        void showImage(Uri data);
        void getProduct();
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter {
        void onClickedBack();
        void onClickedChooseImage();
        void returnedChoosenData(Intent data);
        void onClickedUpload();
        void productUpload(Product product);
    }
}
