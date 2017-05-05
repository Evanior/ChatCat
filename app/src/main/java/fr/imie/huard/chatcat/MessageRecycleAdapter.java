package fr.imie.huard.chatcat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

public class MessageRecycleAdapter extends RecyclerView.Adapter<MessageRecycleAdapter.MessageHolder> {

    private ArrayList<Message> mesMessages;

    public MessageRecycleAdapter() {
        mesMessages = new ArrayList<>();
    }

    public void addMessages(Message m){
        mesMessages.add(m);
    }

    public ArrayList<Message> getMesMessages() {
        return mesMessages;
    }

    public void setMesMessages(ArrayList<Message> mesMessages) {
        this.mesMessages = mesMessages;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.message;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParent);
        MessageHolder viewHolder = new MessageHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mesMessages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        private TextView pseudo;
        private TextView date;
        private TextView message;
        private ImageView imgprofil;

        public MessageHolder(View itemView){
            super(itemView);
            pseudo = (TextView) itemView.findViewById(R.id.pseudo);
            date = (TextView) itemView.findViewById(R.id.date);
            message = (TextView) itemView.findViewById(R.id.message);
            imgprofil = (ImageView) itemView.findViewById(R.id.imgprofile);
        }

        void bind(int listIndex){
            Message leMessage = mesMessages.get(listIndex);
            DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            //Log.i("myTag",(new Date().getTime() - leMessage.getDate().getTime())+"");
            if((new Date().getTime() - leMessage.getDate().getTime()) < 24*60*60*1000){
                dateFormater = new SimpleDateFormat("HH:mm");
            }/*else if((new Date().getTime() - leMessage.getDate().getTime()) < 24*60*60*7*1000){
                dateFormater = new SimpleDateFormat("dd/MM/yyyy");
            }*/

            pseudo.setText(leMessage.getPseudo());
            date.setText(dateFormater.format(leMessage.getDate()));
            message.setText(leMessage.getMessage());
            imgprofil.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
