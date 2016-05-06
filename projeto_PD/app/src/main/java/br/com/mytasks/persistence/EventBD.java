package br.com.mytasks.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mytasks.entities.Event;
import br.com.mytasks.persistence.dataBase.DataBase;


public class EventBD implements IManagerBD<Event> {


    private SQLiteDatabase db;
    private String tbName;
    private Context context;

    public EventBD(Context context) {
        DataBase dt = DataBase.getInstance(context);
        this.context = context;
        this.tbName = DataBase.getNameTableEvent();
        db = dt.getWritableDatabase();
    }


    @Override
    public void insert(Event ob) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", ob.getName());
        contentValues.put("initial_date", ob.getInitialDate().getTime());
        contentValues.put("final_date", ob.getFinalDate().getTime());

        db.insert(this.tbName, null, contentValues);

    }

    @Override
    public void update(Event ob, long id) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", ob.getName());
        contentValues.put("initial_date", ob.getInitialDate().getTime());
        contentValues.put("final_date", ob.getFinalDate().getTime());

        db.update(this.tbName, contentValues, "_id = ?", new String[]{"" +id+ ""});


    }

    @Override
    public void delete(long id) {

        db.delete(this.tbName, "_id =" +id, null);

    }

    @Override
    public List<Event> findAll() {

        List<Event> listAll = new ArrayList<>() ;
        String[] column = {"_id", "name", "initial_date", "final_date"};
        Cursor cursor = db.query(this.tbName, column, null, null, null, null,"_id");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Date date1 = new Date(cursor.getLong(2));
                Date date2 = new Date(cursor.getLong(3));
                listAll.add(new Event(cursor.getLong(0), cursor.getString(1), date1, date2));

            } while (cursor.moveToNext());
        }

        return listAll;
    }
}
