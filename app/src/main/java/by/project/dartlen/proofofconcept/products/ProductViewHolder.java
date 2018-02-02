package by.project.dartlen.proofofconcept.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.proofofconcept.App;
import by.project.dartlen.proofofconcept.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.thumbnail)
    ImageView imageView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;

    @NonNull
    public ProductViewHolder create(@NonNull Context context) {
        View view = View.inflate(context, R.layout.item_product, null);

        return new ProductViewHolder(view);
    }

    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull String url) {
        App.INSTANCE.getPicasso()
                .load(url)
                //.resize(350,350)
                //.centerCrop()
                .error(R.drawable.ic_no_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
