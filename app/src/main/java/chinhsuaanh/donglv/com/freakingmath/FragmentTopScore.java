package chinhsuaanh.donglv.com.freakingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by pnt on 8/14/15.
 */
public class FragmentTopScore extends Fragment {
    private ArrayList<Player> list = new ArrayList<Player>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_score,null);
        ListView lv = (ListView) view.findViewById(R.id.listView);
        HightScoreData db = new HightScoreData(getActivity());
        db.open();
        list = db.getData();
//        Collections.sort(list, new Comparator<Player>() {
//            @Override
//            public int compare(Player lhs, Player rhs) {
//                if(lhs.getScore()>rhs.getScore()){
//                    return -1;
//                }
//                else
//                    if (lhs.getScore()==rhs.getScore()){
//                        return  0;
//                    }
//                    else return 1;
//            }
//        });
        db.close();
        TopScoreAdapter adapter = new TopScoreAdapter(getActivity(),list);
        lv.setAdapter(adapter);
        return view;

    }
}
