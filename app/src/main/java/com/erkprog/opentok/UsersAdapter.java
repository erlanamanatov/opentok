package com.erkprog.opentok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> usersList;

    public UsersAdapter(Context context, ArrayList<User> users ){
        this.context = context;
        this.usersList = users;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_item, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView userEmail = (TextView) convertView.findViewById(R.id.usersEmail);

        final User user = usersList.get(position);
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());

        convertView.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Room)context).onCallButtonClicked(user.getuId());
            }
        });

        return convertView;
    }
}
