package by.project.dartlen.proofofconcept.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.proofofconcept.R;
import by.project.dartlen.proofofconcept.data.model.Product;

public class ProductFragment extends Fragment implements ProductContract.View {

    private ProductContract.Presenter mProductPresenter;
    private GridLayoutManager gridLayoutManager;
    private ProductAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public ProductFragment(){}

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        ButterKnife.bind(this, root);

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setDisplayShowHomeEnabled(false);
        }

        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new ProductAdapter();
        recyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> mProductPresenter.onItemClicked(position));

        mProductPresenter.start();
        return root;
    }

    @Override
    public void setPresenter(ProductContract.Presenter presenter) {
        mProductPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_login) {
            mProductPresenter.signInClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProducts(List<Product> data) {
        mAdapter.addAll(data);

        mAdapter.notifyDataSetChanged();
    }
}
