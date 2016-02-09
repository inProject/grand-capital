package com.grand.capital.app.ui.news.adapter.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.grand.capital.app.components.parser.ParserHandler;
import com.grand.capital.app.components.parser.comparators.helper.DateParserHelper;
import com.grand.capital.app.components.parser.models.news.NewsModel;
import com.grand.capital.app.ui.news.adapter.BaseNewsAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lfqdt on 15.11.2015.
 */
public class EndlessNewsListView extends ListView implements AbsListView.OnScrollListener {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_SEPARATOR = 1;

    private View footer;
    private boolean isLoading;
    private EndlessListener listener;
    private BaseNewsAdapter adapter;

    public EndlessNewsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
    }

    public EndlessNewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public EndlessNewsListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public void setListener(EndlessListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getAdapter() == null)
            return ;
        if (getAdapter().getCount() == 0)
            return ;

        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !isLoading) {
//            this.addFooterView(footer);
            isLoading = true;
            listener.loadData();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isLoading = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = (View) inflater.inflate(resId, null);
        this.addFooterView(footer);

    }

    public void setAdapter(BaseNewsAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
//        this.removeFooterView(footer);
    }


    public void addNewData(ArrayList<NewsModel> model) {
//        this.removeFooterView(footer);
        for (int i = 0; i < 10; i++) {
            NewsModel newsModel = model.get(i);
            if (dateComparator(newsModel.getEventDate()) == TYPE_SEPARATOR) {
                adapter.addSeparatorItem(newsModel);
            }
            adapter.addItem(newsModel);
        }
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    public interface EndlessListener {
        void loadData() ;
    }

    Date oldDate = null;
    public int dateComparator(String jsonDate) {
        Date newDate = DateParserHelper.parseStringToDate(ParserHandler.cutDate(jsonDate));
        int type = newDate.equals(oldDate) ? TYPE_ITEM : TYPE_SEPARATOR;
        oldDate = newDate;
        return type;
    }
}