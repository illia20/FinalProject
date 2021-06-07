package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UnivAdapter extends RecyclerView.Adapter<UnivAdapter.ViewHolder>{
    interface OnUnivClickListener {
        void onUnivClick(University university, int position);
    }

    private final OnUnivClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<University> universities;

    UnivAdapter(Context context, List<University> universities, OnUnivClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.universities = universities;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public UnivAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnivAdapter.ViewHolder holder, int position) {

        University university = universities.get(position);

        holder.nameView.setText(university.getName());
        holder.cityView.setText(university.getCity());
        holder.nstud.setText("Кількість студентів: " + university.getNumberOfStudents());
        holder.worldrank.setText("World Rank: " + university.getWorldrank());
        holder.impactrank.setText("Impact Rank: " + university.getImpactrank());
        holder.opennessrank.setText("Openness Rank: " + university.getOpennessrank());
        holder.excellencerank.setText("Excellence Rank: " + university.getExcellencerank());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onUnivClick(university, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, cityView, nstud, worldrank, impactrank, opennessrank, excellencerank;
        ViewHolder(View view){
            super(view);
            nameView = (TextView)view.findViewById(R.id.nameView);
            cityView = (TextView) view.findViewById(R.id.cityView);
            nstud = (TextView) view.findViewById(R.id.nstud);
            worldrank = (TextView) view.findViewById(R.id.wrView);
            impactrank = (TextView) view.findViewById(R.id.irView);
            opennessrank = (TextView) view.findViewById(R.id.orView);
            excellencerank = (TextView) view.findViewById(R.id.erView);
        }
    }
}
