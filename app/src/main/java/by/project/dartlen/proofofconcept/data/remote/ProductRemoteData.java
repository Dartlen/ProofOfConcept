package by.project.dartlen.proofofconcept.data.remote;

import android.net.Uri;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import by.project.dartlen.proofofconcept.data.model.Product;

public class ProductRemoteData {
    private static ProductRemoteData INSTANCE;
    private DatabaseReference mDataReference;
    private StorageReference mProductReference;
    private StorageReference fileRef;

    public ProductRemoteData(){};

    public static ProductRemoteData getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new ProductRemoteData();
        }
        return INSTANCE;
    }

    public void postProduct(final PostProductCallback callback, Product product){
        mDataReference = FirebaseDatabase.getInstance().getReference("product");
        mProductReference = FirebaseStorage.getInstance().getReference().child("product");
        fileRef = mProductReference.child(product.getName());
        fileRef.putFile(Uri.parse(product.getUrl()))
                .addOnSuccessListener(taskSnapshot -> {


                    product.setUrl(taskSnapshot.getDownloadUrl().toString());

                    writeNewProductInfoToDB(product);
                    callback.onSuccess(taskSnapshot);
                })
                .addOnFailureListener(exception -> callback.onFailure(exception))
                .addOnProgressListener(taskSnapshot -> callback.onProgress(taskSnapshot))
                .addOnPausedListener(taskSnapshot -> callback.onPaused(taskSnapshot));
    }

    private void writeNewProductInfoToDB(Product product) {
        String key = mDataReference.push().getKey();

        mDataReference.child(key).setValue(product);
    }

    public void getProducts(GetProductsCallback callback){
        mDataReference = FirebaseDatabase.getInstance().getReference("product");
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Product>> dataImages = new GenericTypeIndicator<HashMap<String, Product>>() {
                };
                HashMap<String, Product> listResult = dataSnapshot.getValue(dataImages);
                if (listResult != null)
                    callback.onProductsDataLoaded(new ArrayList<Product>(listResult.values()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable(databaseError.getMessage());
            }
        });
    }


}
