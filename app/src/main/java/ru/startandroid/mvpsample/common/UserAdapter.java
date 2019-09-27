package ru.startandroid.mvpsample.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.mvpsample.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<User> data = new ArrayList<>();

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<User> users) {
        data.clear();
        data.addAll(users);
        notifyDataSetChanged();
        Log.d("qweee", "size  = " + getItemCount());
    }

    static class UserHolder extends RecyclerView.ViewHolder {

        TextView text;

        UserHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }

        void bind(User user) {
            text.setText(String.format("id: %s, name: %s, email: %s", user.getId(), user.getName(), user.getEmail()));
        }
    }
}