package lyh.rookiemall.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import lyh.rookiemall.CreateOrderActivity;
import lyh.rookiemall.R;
import lyh.rookiemall.adapter.CartAdapter;
import lyh.rookiemall.bean.ShoppingCart;
import lyh.rookiemall.utils.CartProvider;
import lyh.rookiemall.weiget.CnToolbar;

/**
 * Created by 刘营海 on 2017/8/2.
 */
public class CartFragment extends Fragment implements View.OnClickListener {
    public static final int ACTION_EDIT=1;
    public static final int ACTION_CAMPLATE=2;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.checkbox_all)
    private CheckBox mCheckBox;
    @ViewInject(R.id.txt_total)
    private TextView mTextTotal;
    @ViewInject(R.id.btn_order)
    private Button mBtnOrder;
    @ViewInject(R.id.btn_del)
    private Button mBtnDel;
   @ViewInject(R.id.toolbar)
   private CnToolbar mToolbar;
    private CartAdapter mAdapter;
    private CartProvider cartProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart,container,false);
        x.view().inject(this,view);
        cartProvider = new CartProvider(getContext());
        changeToolbar();
        showData();
        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.delCart();
            }
        });
        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),CreateOrderActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
    private void showData(){
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter = new CartAdapter(getContext(),carts,mCheckBox,mTextTotal);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }
    public void refData(){
        mAdapter.clear();
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();
    }
    public void changeToolbar(){
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setOnClickListener(this);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }
    private void showDelControl(){
        mToolbar.getRightButton().setText("完成");
        mTextTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);
        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);
    }
    private void  hideDelControl(){
        mTextTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();
        mCheckBox.setChecked(true);
    }
    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();
        if(ACTION_EDIT == action){
            showDelControl();
        }
        else if(ACTION_CAMPLATE == action){
            hideDelControl();
        }
    }
}
