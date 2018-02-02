package by.project.dartlen.proofofconcept.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.proofofconcept.R;
import by.project.dartlen.proofofconcept.data.model.Product;
import by.project.dartlen.proofofconcept.products.ProductAdapter;

public class AdminProductFragment extends Fragment implements AdminProductContract.View {

    private AdminProductContract.Presenter mAdminProductPresenter;
    private GridLayoutManager gridLayoutManager;
    private ProductAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton addProduct;

    public static AdminProductFragment newInstance() {
        return new AdminProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin, container, false);

        ButterKnife.bind(this, root);

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setDisplayShowHomeEnabled(false);
        }

        addProduct.setOnClickListener(v -> mAdminProductPresenter.fabOnClicked());

        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new ProductAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdminProductPresenter.start();
        return root;
    }

    @Override
    public void setPresenter(AdminProductContract.Presenter presenter) {
        mAdminProductPresenter = presenter;
    }

    @Override
    public void showProducts(List<Product> data) {
        mAdapter.addAll(data);

        mAdapter.notifyDataSetChanged();
    }
}
