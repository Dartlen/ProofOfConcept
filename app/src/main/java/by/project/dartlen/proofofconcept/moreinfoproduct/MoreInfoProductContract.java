package by.project.dartlen.proofofconcept.moreinfoproduct;

import by.project.dartlen.proofofconcept.base.BasePresenter;
import by.project.dartlen.proofofconcept.base.BaseView;
import by.project.dartlen.proofofconcept.data.model.Product;

public interface MoreInfoProductContract {
    interface View extends BaseView<MoreInfoProductContract.Presenter>{
        void showProduct(Product product);
    }
    interface Presenter extends BasePresenter{
        void onClickedBack();
    }
}
