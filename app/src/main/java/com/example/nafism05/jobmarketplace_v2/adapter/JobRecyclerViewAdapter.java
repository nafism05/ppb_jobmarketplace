package com.example.nafism05.jobmarketplace_v2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nafism05.jobmarketplace_v2.JobDetailActivity;
import com.example.nafism05.jobmarketplace_v2.PostJobActivity;
import com.example.nafism05.jobmarketplace_v2.R;
import com.example.nafism05.jobmarketplace_v2.SignInActivity;
import com.example.nafism05.jobmarketplace_v2.model.Job;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class JobRecyclerViewAdapter extends RecyclerView.Adapter<JobRecyclerViewAdapter.ViewHolder> {



    private List<Job> jobList;
    private Context context;
    private FirebaseFirestore firestoreDB;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    public JobRecyclerViewAdapter(List<Job> jobList, Context context, FirebaseFirestore firestoreDB) {
        this.jobList = jobList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    public JobRecyclerViewAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    @Override
    public JobRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_job, viewGroup, false);

        return new JobRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobRecyclerViewAdapter.ViewHolder holder, int i) {
        final int itemPosition = i;
        final Job job = jobList.get(itemPosition);

        holder.title.setText(job.getTitle());
        holder.language.setText(job.getLanguage());

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String user_id = job.getUser_id();
            String uid = user.getUid();
            Log.e("usernda", user_id);
            if (user_id.equals(uid)){
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
            }

        }


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "hahaha", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra("title", job.getTitle());
                intent.putExtra("language", job.getLanguage());
                intent.putExtra("detail", job.getDetail());
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJob(job);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJob(job.getId(), itemPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    private void updateJob(Job job){
        Intent intent = new Intent(context, PostJobActivity.class);
        intent.putExtra("id", job.getId());
        intent.putExtra("title", job.getTitle());
        intent.putExtra("language", job.getLanguage());
        intent.putExtra("detail", job.getDetail());
        intent.putExtra("user_id", job.getUser_id());
        context.startActivity(intent);
    }

    private void deleteJob(String id, final int position){
        firestoreDB.collection("joblist")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        jobList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, jobList.size());
                        Toast.makeText(context, "Job has been deleted!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, language;
        ImageView edit;
        ImageView delete;
        LinearLayout parentLayout;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            language = view.findViewById(R.id.language);

            edit = view.findViewById(R.id.ivEdit);
            delete = view.findViewById(R.id.ivDelete);

            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }
}
