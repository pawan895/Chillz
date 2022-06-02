package com.example.chillz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Results> results;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    public String movieTitle;
    Context mContext;

    public void filterList(ArrayList<Results> filteredList) {
        results = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(int position, String movieTitle);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public Adapter(Context ctx,List<Results> results){
        mContext = ctx;
        this.results = results;
        this.inflater = LayoutInflater.from(ctx) ;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String poster = results.get(position).getPosterPath();
        String URL = "https://image.tmdb.org/t/p/w780/"+poster;
        Picasso.with(mContext).load(URL).into(holder.moviePreviewImage1);
        holder.titleTV.setText(results.get(position).getMovieTitle());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView moviePreviewImage1;
        TextView titleTV;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            setUpUI();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getBindingAdapterPosition();
                        movieTitle= results.get(position).getMovieTitle();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position, movieTitle);
                        }
                    }
                }
            });

        }
        //Linking the Xml ids to the java variables.
        private void setUpUI(){
            moviePreviewImage1 = itemView.findViewById(R.id.imageView);
            titleTV = itemView.findViewById(R.id.titleTV);
        }

    }
}
