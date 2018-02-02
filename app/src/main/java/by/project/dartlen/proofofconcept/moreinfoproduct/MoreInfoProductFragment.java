package by.project.dartlen.proofofconcept.moreinfoproduct;

import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.R;
import by.project.dartlen.proofofconcept.data.model.Product;

public class MoreInfoProductFragment extends Fragment implements MoreInfoProductContract.View {

    private MoreInfoProductContract.Presenter mPresenter;

    @BindView(R.id.image_big)
    ImageView image;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.description)
    TextView description;

    public static MoreInfoProductFragment newInstance() {
        return new MoreInfoProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_moreinfo, container, false);
        ButterKnife.bind(this, root);

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        mPresenter.start();
        return root;
    }

    @Override
    public void setPresenter(MoreInfoProductContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProduct(Product product) {
        App.INSTANCE.getPicasso()
                .load(product.getUrl())
                .resize(350,350)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("picasso", "image loaded!");
                    }

                    @Override
                    public void onError() {
                        Log.d("picasso", "image error!");
                    }
                });
        name.setText(product.getName());
        price.setText(product.getPrice().toString());
        description.setText(product.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.onClickedBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
