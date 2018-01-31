package by.project.dartlen.proofofconcept.login;

import by.project.dartlen.proofofconcept.base.BasePresenter;
import by.project.dartlen.proofofconcept.base.BaseView;

public interface LoginContract {
    interface View extends BaseView<LoginContract.Presenter>{}
    interface Presenter extends BasePresenter{}
}
