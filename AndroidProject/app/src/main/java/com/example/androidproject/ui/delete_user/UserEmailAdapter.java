package com.example.androidproject.ui.delete_user;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.R;

import java.util.List;

public class UserEmailAdapter extends RecyclerView.Adapter<UserEmailAdapter.ViewHolder> {
    private List<String> userEmails;
    private final Context context;

    public UserEmailAdapter(Context context, List<String> userEmails) {
        this.context = context;
        this.userEmails = userEmails;
    }

    public void setFilteredList(List<String> filteredList) {

        this.userEmails = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_email_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String userEmail = userEmails.get(position);
        holder.userEmailAdmin.setText(userEmail);
        holder.deleteUserAdmin.setImageResource(R.drawable.delete);

        holder.deleteUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
                holder.deleteUserAdmin.startAnimation(animation);

                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.car_dialog);

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.delete_assurance);
                dialog.getWindow().setBackgroundDrawable(drawable);

                dialog.setCancelable(false);
                dialog.show();

                Button deleteBtn = (Button) dialog.findViewById(R.id.deleteBtn);
                Button cancelBtn = (Button) dialog.findViewById(R.id.cancelBtn);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "db", null, 1);
                        dataBaseHelper.deleteUser(userEmail);

                        Toast.makeText(context, "User has been deleted successfully", Toast.LENGTH_SHORT).show();

                        userEmails.remove(userEmail);
                        notifyDataSetChanged();

                        dialog.dismiss();

                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();


                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return userEmails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userEmailAdmin;
        private final ImageView deleteUserAdmin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmailAdmin = itemView.findViewById(R.id.userEmailAdmin);
            deleteUserAdmin = itemView.findViewById(R.id.grabageBtn);

        }

    }
}
