package me.dudu.cacheusersample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.dudu.cacheuser.CacheUserLoader;
import me.dudu.cacheuser.db.CacheUser;
import me.dudu.cacheuser.interf.OnResultCallback;
import me.dudu.cacheuser.task.CacheUserView;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description :
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> identifiers;

    public ContactAdapter(Context context, List<String> identifiers) {
        this.inflater = LayoutInflater.from(context);
        this.identifiers = identifiers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvPosition.setText(String.valueOf(position + ""));
        holder.tvContent.setText("准备获取数据");
        CacheUserLoader.getInstance().load(holder.viewCacheUser, identifiers.get(position), new OnResultCallback() {
            @Override
            public void onResult(CacheUser cacheUser) {
                holder.tvContent.setText(cacheUser.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return identifiers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosition;
        TextView tvContent;
        CacheUserView viewCacheUser;

        ViewHolder(View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvContent = itemView.findViewById(R.id.tv_content);
            viewCacheUser = itemView.findViewById(R.id.view_cache_user);
        }
    }

}
