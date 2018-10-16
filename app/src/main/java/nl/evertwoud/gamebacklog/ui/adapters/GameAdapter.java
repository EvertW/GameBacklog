package nl.evertwoud.gamebacklog.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nl.evertwoud.gamebacklog.R;
import nl.evertwoud.gamebacklog.data.models.Game;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private final Context mContext;
    private List<Game> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public GameAdapter(Context context, List<Game> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    public void remove(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.view_game, parent, false);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = mData.get(position);
        holder.title.setText(game.getTitle());
        holder.platform.setText(game.getPlatform());
        holder.status.setText(game.getStatusString(mContext));
        holder.date.setText(game.getDateFormatted());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView platform;
        TextView status;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.game_title);
            platform = itemView.findViewById(R.id.game_platform);
            status = itemView.findViewById(R.id.game_status);
            date = itemView.findViewById(R.id.game_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Game getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}