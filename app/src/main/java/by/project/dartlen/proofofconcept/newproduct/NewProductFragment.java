package by.project.dartlen.proofofconcept.newproduct;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.R;
import by.project.dartlen.proofofconcept.data.model.Product;

public class NewProductFragment extends Fragment implements NewProductContract.View {

    private NewProductContract.Presenter mNewProductPresenter;
    private static ProgressDialog mDialog;

    @BindView(R.id.choose_image)
    Button btnChooseImage;

    @BindView(R.id.name_text)
    EditText editTextName;

    @BindView(R.id.price)
    EditText editTextPrice;

    @BindView(R.id.description)
    EditText editTextDesc;

    @BindView(R.id.upload)
    Button btnUpload;

    @BindView(R.id.path_to_image)
    TextView pathToImage;

    @BindView(R.id.image_to_load)
    ImageView imageToLoad;

    public static NewProductFragment newInstance() {
        return new NewProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newproduct, container, false);

        ButterKnife.bind(this, root);

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        btnChooseImage.setOnClickListener(v->mNewProductPresenter.onClickedChooseImage());
        btnUpload.setOnClickListener(v->mNewProductPresenter.onClickedUpload());
        return root;
    }

    @Override
    public void setPresenter(NewProductContract.Presenter presenter) {
        mNewProductPresenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNewProductPresenter.onClickedBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showChooser() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK,   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Choose image...");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mNewProductPresenter.returnedChoosenData(data);
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showPath(Uri data) {
        String path = "file://"+getRealPathFromURI(getContext(),data);

        pathToImage.setText(path);
    }

    @Override
    public void showImage(Uri data) {

        App.INSTANCE.getPicasso()
                .load(data)
                .resize(350,350)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(imageToLoad, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("picasso", "image loaded!");
                    }

                    @Override
                    public void onError() {
                        Log.d("picasso", "image error!");
                    }
                });
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void getProduct() {
        Product product = new Product();
        product.setUrl(pathToImage.getText().toString());
        product.setName(editTextName.getText().toString());
        product.setDescription(editTextDesc.getText().toString());
        product.setPrice(Float.parseFloat(editTextPrice.getText().toString()));
        mNewProductPresenter.productUpload(product);
    }

    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(getContext(),
                "",
                "Please wait uploading...");
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }
}
