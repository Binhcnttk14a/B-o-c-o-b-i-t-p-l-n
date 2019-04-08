package chinhsuaanh.donglv.com.freakingmath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by pnt on 8/14/15.
 */
public class HightScoreData {
    private static final String DATABASE_NAME = "DB_HIGHT_SCORE";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "HIGHT_SCORE";
    private static final String COLUM_ID = "id";
    private static final String COLUM_NAME = "name";
    private static final String COLUM_SCORE = "score";
    private static final String COLUM_TIME = "time";

    private static Context context;
    private static SQLiteDatabase db;
    private static OpenHelper openHelper;

    public HightScoreData(Context context){
        HightScoreData.context = context;
    }

    public HightScoreData open() throws SQLiteException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        openHelper.close();
    }

    public long createData(Player player){
        ContentValues cv = new ContentValues();
        cv.put(COLUM_ID,player.getId());
        cv.put(COLUM_NAME,player.getName());
        cv.put(COLUM_SCORE,player.getScore());
        cv.put(COLUM_TIME, player.getTime());
        //Log.e("name1",player.getName());
        return db.insert(TABLE_NAME,null,cv);
    }

    public ArrayList<Player> getData(){
        ArrayList<Player> list = new ArrayList<Player>();
        String[] columns = new String[] {COLUM_ID,COLUM_NAME,COLUM_SCORE,COLUM_TIME};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        int iID = cursor.getColumnIndex(COLUM_ID);
        int iNAME = cursor.getColumnIndex(COLUM_NAME);
        int iSCORE = cursor.getColumnIndex(COLUM_SCORE);
        int iTIME = cursor.getColumnIndex(COLUM_TIME);
        for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
            Player player = new Player();
            player.setId(cursor.getInt(iID));
            player.setName(cursor.getString(iNAME));
            player.setScore(cursor.getInt(iSCORE));
            player.setTime(cursor.getString(iTIME));
           // Log.e("name2",player.getName());
            list.add(player);
            if(list.size()==10) break;
        }
        cursor.close();
        return list;
    }
    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "Create Table "
                    +TABLE_NAME+" ( "
                    +COLUM_ID+" integer,"
                    +COLUM_NAME+" text,"
                    +COLUM_SCORE+" integer primary key,"
                    +COLUM_TIME+" text);";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
            onCreate(db);
        }
    }
}
