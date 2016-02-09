package com.grand.capital.app.ui.news.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grand.capital.app.R;
import com.grand.capital.app.components.parser.formatter.DateFormatter;
import com.grand.capital.app.components.parser.models.news.NewsModel;
import com.grand.capital.app.ui.BaseCustomAdapter;

/**
 * Created by lfqdt on 06.11.2015.
 */
public class BaseNewsAdapter extends BaseCustomAdapter {

    public OnAdapterClickListener listener;

    public BaseNewsAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    public void addItem(NewsModel item) {
        newsModels.add(item);
        notifyDataSetChanged();
    }

    public void addSeparatorItem(NewsModel item) {
        newsModels.add(item);
        mSeparatorsSet.add(newsModels.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    public int getCount() {
        return newsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return newsModels.get(position);
    }

    public long getItemId(int position) {
        return newsModels.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        int type = getItemViewType(position);
        if(type == TYPE_ITEM) {
            convertView = inflater.inflate(R.layout.item_analitic, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.description = (TextView) convertView.findViewById(R.id.description);

            final NewsModel model = newsModels.get(position);

            holder.title.setText(model.getTitle());
            holder.description.setText(String.valueOf(stripHtml(model.getBody())));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCLick(model.getTitle(), model.getBody());
                }
            });
        } else {
            convertView = mInflater.inflate(R.layout.item_header_layout, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
            holder.textView.setText(DateFormatter.changeDateFormat(newsModels.get(position).getEventDate()));
        }
        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView description;
        public TextView textView;
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    public BaseNewsAdapter setOnClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.listener = onAdapterClickListener;
        return this;
    }

    public interface OnAdapterClickListener {
        void onCLick(String title, String description);
    }
}
