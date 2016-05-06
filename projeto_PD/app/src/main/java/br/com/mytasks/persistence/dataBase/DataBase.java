package br.com.mytasks.persistence.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase extends SQLiteOpenHelper {

    private static final String NAME_BD = "activityManager.db";
    private static DataBase instace ;
    private static final int VERSION = 1;
    private static final String NAME_TABLE_CATEGORY = "Category";
    private static final String NAME_TABLE_ACTIVITY = "Activity";
    private static final String NAME_TABLE_EVENT = "Event";
    private static final String NAME_TABLE_ACTIVITY_EVENT = "Activity_has_Event";
    private Context context;

    private DataBase(Context context) {
        super(context, NAME_BD, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  " + NAME_TABLE_CATEGORY + " (" +
                "_id integer primary key autoincrement," +
                " name Text," +
                "color Text);");

        db.execSQL("CREATE TABLE  " + NAME_TABLE_ACTIVITY + " (" +
                "_id integer primary key autoincrement," +
                "name Text ,"+
                " deadline DATETIME," +
                " planned_hour DATETIME, " +
                "worked_hours DATETIME DEFAULT 0, " +
                "repetitions integer, " +
                "finished integer DEFAULT 0, " +
                "category_id integer DEFAULT 0);");

        db.execSQL("CREATE TABLE  " + NAME_TABLE_EVENT + " (" +
                "_id integer primary key autoincrement," +
                "name Text ," +
                "initial_date DATETIME," +
                "final_date DATETIME);");

        db.execSQL("CREATE TABLE "+ NAME_TABLE_ACTIVITY_EVENT+ " ("+
                "_id integer primary key autoincrement," +
                "_id_activity integer, " +
                "_id_event integer," +
                "FOREIGN KEY(_id_activity) REFERENCES " + DataBase.NAME_TABLE_ACTIVITY +"(_id), "+
                "FOREIGN KEY(_id_event) REFERENCES "+DataBase.NAME_TABLE_EVENT+
                " (_id) );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String[] tables = {NAME_TABLE_CATEGORY, NAME_TABLE_ACTIVITY, NAME_TABLE_EVENT};

            for (String table : tables) {
                db.execSQL("DROP TABLE IF EXISTS  " + table);
                onCreate(db);
            }

    }

    public static synchronized DataBase getInstance(Context context){

        if (instace == null){
            instace = new DataBase(context);

        }
        return instace;
    }


    public static String getNameTableCategory() {
        return NAME_TABLE_CATEGORY;}

    public static String getNameTableActivity() {
        return NAME_TABLE_ACTIVITY;
    }

    public static String getNameTableEvent(){
        return NAME_TABLE_EVENT;}

    public static String getNameTableActivityEvent() {
        return NAME_TABLE_ACTIVITY_EVENT;
    }
}
