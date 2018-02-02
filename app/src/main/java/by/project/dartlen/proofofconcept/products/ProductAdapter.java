package by.project.dartlen.proofofconcept.products;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.project.dartlen.proofofconcept.R;
import by.project.dartlen.proofofconcept.data.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> ProductList = new ArrayList<Product>(0);
    public ProductAdapter(){}

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductViewHolder vh = (ProductViewHolder) holder;
        vh.bind(ProductList.get(position).getUrl());
        vh.name.setText(ProductList.get(position).getName());
        if(ProductList.get(position).getDescription().length()<15)
            vh.description.setText(ProductList.get(position).getDescription());
        else
            vh.description.setText(ProductList.get(position).getDescription().substring(0,15)+"...");
        vh.price.setText(ProductList.get(position).getPrice().toString());

    }

    public void add(Product r) {
        ProductList.add(r);
        notifyItemInserted(ProductList.size() - 1);
    }

    public void addAll(List<Product> productslist) {
        for (Product result : productslist) {
            add(result);
        }
    }

    public void remove(Product r) {
        int position = ProductList.indexOf(r);
        if (position > -1) {
            ProductList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public Product getItem(int position) {
        return ProductList.get(position);
    }
}
