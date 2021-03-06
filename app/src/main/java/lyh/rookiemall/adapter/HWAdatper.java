package lyh.rookiemall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import lyh.rookiemall.R;
import lyh.rookiemall.bean.ShoppingCart;
import lyh.rookiemall.bean.Wares;
import lyh.rookiemall.utils.CartProvider;
import lyh.rookiemall.utils.ToastUtils;


/**
 * Created by 刘营海 on 2017/8/6.
 */
public class HWAdatper extends SimpleAdapter<Wares> {
    CartProvider provider ;
    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);
        provider = new CartProvider(context);
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, final Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
        Button button =viewHolder.getButton(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider.put(convertData(wares));
                ToastUtils.show(context,"已添加到购物车");
            }
        });
    }
    public ShoppingCart convertData(Wares item){
        ShoppingCart cart = new ShoppingCart();
        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }

    public void  resetLayout(int layoutId){


        this.layoutResId  = layoutId;

        notifyItemRangeChanged(0,getDatas().size());


    }
}
