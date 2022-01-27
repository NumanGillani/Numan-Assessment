package com.ny.articles.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ny.articles.Models.ArticlesModel;
import com.ny.articles.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private List<ArticlesModel> articlesList;
    private AdapterView.OnItemClickListener onItemClickListener;

    public ArticlesAdapter(List<ArticlesModel> articlesList, AdapterView.OnItemClickListener onItemClickListener) {
        this.articlesList = articlesList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articles_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArticlesModel article = articlesList.get(position);
        holder.title.setText(article.getTitle());
        holder.byLine.setText(article.getByLine());
        holder.articleSection.setText(article.getArticleSection());
        holder.publishedDate.setText(article.getPublishedDate());
        try {
            if (article.getMediaThumbnails().size() > 0)
                Picasso.get()
                        .load(article.getMediaThumbnails().get(0).getMediaMetadata().get(0).getThumbnailURL())
                        .into(holder.thumbnail);
        } catch (Exception e) {
            Log.e("Thumbnail Exception", e + "");
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, byLine, publishedDate, articleSection;
        public CircleImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            byLine = (TextView) view.findViewById(R.id.byLine);
            articleSection = (TextView) view.findViewById(R.id.articleSection);
            publishedDate = (TextView) view.findViewById(R.id.publishedDate);
            thumbnail = (CircleImageView) view.findViewById(R.id.thumbnail);

            title.setOnClickListener(this);
            byLine.setOnClickListener(this);
            articleSection.setOnClickListener(this);
            publishedDate.setOnClickListener(this);
            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }
}