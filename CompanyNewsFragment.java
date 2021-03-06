package com.grand.capital.app.ui.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.grand.capital.app.R;
import com.grand.capital.app.api.handler.interfaces.imp.OnSuccessCallback;
import com.grand.capital.app.components.Constants;
import com.grand.capital.app.ui.BaseFragment;
import com.grand.capital.app.ui.news.adapter.BaseNewsAdapter;
import com.grand.capital.app.ui.news.adapter.views.EndlessNewsListView;

import java.util.Locale;

/**
 * Created by lfqdt on 02.11.2015.
 */
public class CompanyNewsFragment extends BaseFragment implements EndlessNewsListView.EndlessListener {

    private EndlessNewsListView listView;
    private BaseNewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_company_news, container, false);

        ((AppCompatActivity) context).getSupportActionBar().setTitle(getString(R.string.company_news));

        adapter =  new BaseNewsAdapter(context);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (EndlessNewsListView) view.findViewById(R.id.listView7);
        listView.setLoadingView(R.layout.loading_view);
        listView.setListener(this);
        companyNameRequest(COUNT, OFFSET, Locale.getDefault().getLanguage()).setOnSuccessListener(new OnSuccessCallback() {
            @Override
            public void success(String result) {
                progressBar.setVisibility(View.GONE);
                listView.setAdapter(adapter.setOnClickListener(new BaseNewsAdapter.OnAdapterClickListener() {
                    @Override
                    public void onCLick(String title, String description) {
                        getFragmentManager().beginTransaction().replace(R.id.container, new FullNewsFragment().newInstance(title, description))
                                .addToBackStack(Constants.BACKTRACE_TAG)
                                .commit();
                    }
                }));
                listView.addNewData(parseNewsGson(result));
            }
        });
        return view;
    }

    @Override
    public void loadData() {
        OFFSET += 10;
        companyNameRequest(COUNT, OFFSET, Locale.getDefault().getLanguage()).setOnSuccessListener(new OnSuccessCallback() {
            @Override
            public void success(String result) {
                listView.addNewData(parseNewsGson(result));
            }
        });
    }
}
