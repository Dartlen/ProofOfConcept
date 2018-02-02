package by.project.dartlen.proofofconcept.products;

import java.util.List;

import by.project.dartlen.proofofconcept.base.BasePresenter;
import by.project.dartlen.proofofconcept.base.BaseView;
import by.project.dartlen.proofofconcept.data.model.Product;

public interface ProductContract {
    interface Presenter extends BasePresenter{
        void signInClicked();
        void onItemClicked(int position);
    }
    interface View extends BaseView<ProductContract.Presenter>{
        void showProducts(List<Product> data);
    }
}
