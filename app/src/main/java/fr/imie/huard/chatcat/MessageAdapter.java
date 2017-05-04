package fr.imie.huard.chatcat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

@Deprecated
public class MessageAdapter<Object> extends ArrayAdapter {

    private static LayoutInflater inflater = null;

    public MessageAdapter(@NonNull Context context) {
        super(context, R.layout.message);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.message, null);
        TextView pseudo = (TextView) vi.findViewById(R.id.pseudo);
        TextView msg = (TextView) vi.findViewById(R.id.message);
        TextView date = (TextView) vi.findViewById(R.id.date);
        Message monMessage = (Message) getItem(position);
        pseudo.setText(monMessage.getPseudo());
        msg.setText(monMessage.getMessage());
        DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        date.setText(dateFormater.format(monMessage.getDate()));
        return vi;
    }
}
