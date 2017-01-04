package com.dega.chama.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dega.chama.R;
import com.dega.chama.model.Repo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by davedega on 26/12/16.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposHolder> {

    private Context context;
    private ArrayList<Repo> repos;

    public ReposAdapter(Context context, ArrayList<Repo> repos) {
        this.context = context;
        this.repos = repos;
//        this.repos = new ArrayList<>();
    }

    @Override
    public ReposHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.item_repos, parent, false);
        return new ReposHolder(root);
    }

    @Override
    public void onBindViewHolder(ReposHolder holder, int position) {
        Repo repo = repos.get(position);
        String desc;
        if (repo.getDescription() != null) {
            desc = repo.getDescription().toString();
        } else {
            desc = "";
        }
        holder.setname(repo.getName());
        holder.setFullname(repo.getFullName());
        holder.setAvatar(repo.getOwner().getAvatarUrl());
        holder.setDescription(desc);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void addAll(ArrayList<Repo> repos) {
        if (repos == null)
            throw new NullPointerException("Empty list of repos not allowed in adapter");

        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    public void addRepo(Repo repo) {
        this.repos.add(repo);
        notifyItemInserted(getItemCount() - 1);
    }

    public void replace(ArrayList<Repo> repos) {
        this.repos = repos;
        notifyDataSetChanged();
    }
    public void clear(){
        this.repos.clear();
        notifyDataSetChanged();
    }

    class ReposHolder extends RecyclerView.ViewHolder {

        TextView name, fullName, description;
        ImageView avatar;


        public ReposHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_img);
            name = (TextView) itemView.findViewById(R.id.name_textview);
            fullName = (TextView) itemView.findViewById(R.id.fullname_textview);
            description = (TextView) itemView.findViewById(R.id.description_textview);
        }

        public void setname(String name) {
            this.name.setText(name);
        }

        public void setFullname(String fullname) {
            this.fullName.setText(fullname);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }

        public void setAvatar(String url) {
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.placeholder_120)
                    .resize(120, 120)
                    .into(this.avatar);

        }
    }
}
