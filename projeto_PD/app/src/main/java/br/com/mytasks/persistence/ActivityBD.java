package br.com.mytasks.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mytasks.entities.Activity;
import br.com.mytasks.entities.Category;
import br.com.mytasks.entities.Event;
import br.com.mytasks.persistence.dataBase.DataBase;


public class ActivityBD implements IManagerBD<Activity> {

    private SQLiteDatabase db;
    private String tbName;
    private Context context;
    private AuxActivity auxActivity;

    public ActivityBD(Context context) {
        DataBase dt = DataBase.getInstance(context);
        this.context = context;
        this.tbName = DataBase.getNameTableActivity();
        db = dt.getWritableDatabase();
        auxActivity = new AuxActivity(db);
    }

    @Override
    public void insert(Activity ob) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", ob.getName());
        contentValues.put("deadLine", ob.getDeadLine().getTime());

        if (ob.getCategory() != null)
            contentValues.put("category_id", ob.getCategory().getId());

        contentValues.put("planned_hour", ob.getPlannedHours().getTime());
        contentValues.put("finished", ob.isFinished());
        contentValues.put("repetitions", ob.getRepetitions());

        db.insert(this.tbName, null, contentValues);

        if (ob.getEvents() != null) {

            String query = "SELECT ROWID from " + tbName + " order by ROWID DESC limit 1";
            Cursor c = db.rawQuery(query, null);
            long lastId = 0;
            if (c != null && c.moveToFirst()) {
                lastId = c.getLong(0);
            }
            ob.setId(lastId);
            assert c != null;
            c.close();
            auxActivity.insert(ob);

        }
    }

    @Override
    public void update(Activity ob, long id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", ob.getName());
        contentValues.put("deadLine", ob.getDeadLine().getTime());
        contentValues.put("category_id", ob.getCategory().getId());
        contentValues.put("planned_hour", ob.getPlannedHours().getTime());
        if (ob.getWorkedHours() != null) {
            contentValues.put("worked_hours", ob.getWorkedHours().getTime());
        }
        contentValues.put("finished", ob.isFinished());
        contentValues.put("repetitions", ob.getRepetitions());

        if (ob.getEvents() != null) {
            auxActivity.update(ob, id);
        }

        db.update(this.tbName, contentValues, "_id = ?", new String[]{"" + id + ""});
    }

    @Override
    public void delete(long id) {
        auxActivity.delete(id);
        db.delete(this.tbName, "_id =" + id, null);
    }

    @Override
    public List<Activity> findAll() {

        List<Activity> listAll = new ArrayList<>();

        String[] column = {"_id", "name", "deadLine", "category_id", "planned_hour",
                "worked_hours", "finished", "repetitions"};

        Cursor cursor = db.query(this.tbName, column, null, null, null, null, "_id ");

        Category category = null;
        List<Event> events = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            CategoryBD categoryBD = new CategoryBD(context);
            EventBD eventBD = new EventBD(context);

            do {
                Date date = new Date(cursor.getLong(2));
                Date date1 = new Date(cursor.getLong(4));
                Date date2 = new Date(cursor.getLong(5));
                boolean value = cursor.getInt(6) > 0;
                for (Category c : categoryBD.findAll()) {

                    if (c.getId() != 0 && c.getId() == cursor.getLong(3)) {
                        category = c;
                        break;
                    }

                }

                List<Long> ids = auxActivity.getIdsEvents(cursor.getLong(0));
                for (int i = 0; i < ids.size(); i++) {
                    for (Event e : eventBD.findAll()) {
                        if (e.getId() == ids.get(i)) {
                            events.add(e);
                        }
                    }
                }

                listAll.add(new Activity(cursor.getLong(0), cursor.getString(1), date, date1, date2,
                        category, cursor.getInt(7), value, events));

            } while (cursor.moveToNext());
        }
        cursor.close();

        return listAll;
    }
}
