package by.project.dartlen.proofofconcept.admin;

import java.util.List;

import by.project.dartlen.proofofconcept.base.BasePresenter;
import by.project.dartlen.proofofconcept.base.BaseView;
import by.project.dartlen.proofofconcept.data.model.Product;

public interface AdminProductContract {
    interface View extends BaseView<AdminProductContract.Presenter>{
        void showProducts(List<Product> data);
    }
    interface Presenter extends BasePresenter{
        void fabOnClicked();
    }
}
