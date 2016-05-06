package br.com.mytasks.persistence;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mytasks.entities.Activity;
import br.com.mytasks.entities.Event;
import br.com.mytasks.persistence.dataBase.DataBase;


public class AuxActivity implements IManagerBD<Activity> {

    SQLiteDatabase db;
    private String tbName;


    AuxActivity(SQLiteDatabase dataBase) {
        this.db = dataBase;
        tbName = DataBase.getNameTableActivityEvent();
    }


    @Override
    public void insert(Activity ob) {
        ContentValues contentValues = new ContentValues();

        for (Event event : ob.getEvents()) {
            contentValues.put("_id_activity", ob.getId());
            contentValues.put("_id_event", event.getId());
            db.insert(tbName, null, contentValues);
        }

    }

    @Override
    public void update(Activity ob, long id) {
        ContentValues contentValues = new ContentValues();

        for (Event event : ob.getEvents()) {
            contentValues.put("_id_activity", id);
            contentValues.put("_id_event", event.getId());
            db.update(tbName, contentValues, "_id = ?", new String[]{"" + id + ""});
        }

      //  List<String> idsEvent = getIdsEvents(id);

    }

    @Override
    public void delete(long id) {
        List<Long> list = getIdsEvents(id);
        for (int i =0 ; i< list.size(); i++){
            db.delete(this.tbName, "_id_event = " + list.get(i), null);
        }
    }

    @Override
    public List<Activity> findAll() {

        return null;
    }

    public List<Long> getIdsEvents(long idActivity) {
        List<Long> list = new ArrayList<>();
        String[] column = {"_id", "_id_activity", "_id_event"};

        Cursor cursor = db.query(this.tbName, column, null, null, null, null, "_id_activity");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                if (cursor.getLong(1) == idActivity){
                    list.add(cursor.getLong(2));
                }

            } while (cursor.moveToNext());

        }
        return list;
    }
}
