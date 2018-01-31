package by.project.dartlen.proofofconcept.data.remote;

import java.util.List;

import by.project.dartlen.proofofconcept.data.model.Product;

public interface GetProductsCallback {
    void onProductsDataLoaded(List<Product> dataProducts);
    void onDataNotAvailable(String error);
}
