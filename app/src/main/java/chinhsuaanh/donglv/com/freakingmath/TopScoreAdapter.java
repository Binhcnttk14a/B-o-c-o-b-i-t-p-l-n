package chinhsuaanh.donglv.com.freakingmath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pnt on 8/14/15.
 */
public class TopScoreAdapter extends BaseAdapter {
    private ArrayList<Player> list;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public TopScoreAdapter(Context context, ArrayList<Player> list){
        super();
        this.list = list;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item,null);
            holder.tv_id = (TextView) convertView.findViewById(R.id.textView_itemID);
            holder.tv_name = (TextView) convertView.findViewById(R.id.textView_itemName);
            holder.tv_score = (TextView) convertView.findViewById(R.id.textView_itemScore);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        Player player = list.get(position);
        holder.tv_id.setText(""+(position+1));
        holder.tv_name.setText(player.getName());
        holder.tv_score.setText(""+player.getScore());
        return convertView;
    }
    protected class ViewHolder{
        TextView tv_id;
        TextView tv_name;
        TextView tv_score;
    }
}
