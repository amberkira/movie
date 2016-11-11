package android.zhixin.com.jsoup.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.zhixin.com.jsoup.R;
import android.zhixin.com.jsoup.base.adapter.BaseAdapter;
import android.zhixin.com.jsoup.base.adapter.BaseViewHolder;
import android.zhixin.com.jsoup.data.Douban250SubjectsBean;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangwenxing on 2016/11/10.
 */

public class DoubanMoview250Adapter extends BaseAdapter<Douban250SubjectsBean> {


    public DoubanMoview250Adapter(Context context, List<Douban250SubjectsBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        this.mDatas = datas;
        this.mContext = context;
        this.mOpenLoadMore = isOpenLoadMore;
    }

    @Override
    protected void convert(BaseViewHolder holder, Douban250SubjectsBean data) {
        ImageView imageView = holder.getView(R.id.movie250_recycler_item_title_iv);
        TextView tv = holder.getView(R.id.movie250_recycler_item_title_tv);
        tv.setText(data.getTitle());
        Picasso.with(mContext).load(data.getImages().getLarge()).error(R.mipmap.test).into(imageView);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.movie250_recycler_item;
    }

}
