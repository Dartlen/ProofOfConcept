package by.project.dartlen.proofofconcept.data;

import android.support.annotation.NonNull;

import com.google.firebase.storage.UploadTask;

import java.util.List;

import by.project.dartlen.proofofconcept.data.model.Product;
import by.project.dartlen.proofofconcept.data.remote.GetProductsCallback;
import by.project.dartlen.proofofconcept.data.remote.PostProductCallback;
import by.project.dartlen.proofofconcept.data.remote.ProductRemoteData;

public class ProductRepository {
    private static ProductRepository INSTANCE = null;
    private ProductRemoteData productRemoteData;
    public static ProductRepository getINSTANCE(ProductRemoteData productRemoteData) {
        if(INSTANCE == null){
            INSTANCE = new ProductRepository(productRemoteData);
        }
        return INSTANCE;
    }

    public ProductRepository(@NonNull ProductRemoteData productRemoteData){
        this.productRemoteData = productRemoteData;
    }

    public void getProducts(GetProductsCallback callback){
        productRemoteData.getProducts(new GetProductsCallback() {
            @Override
            public void onProductsDataLoaded(List<Product> dataProducts) {
                callback.onProductsDataLoaded(dataProducts);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    public void postProduct(PostProductCallback callback, Product product){
        productRemoteData.postProduct(new PostProductCallback() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onSuccess(taskSnapshot);
            }

            @Override
            public void onFailure(Exception exception) {
                callback.onFailure(exception);
            }

            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onProgress(taskSnapshot);
            }

            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onPaused(taskSnapshot);
            }
        }, product);
    }
}
