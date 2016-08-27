package br.com.mytasks.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import br.com.mytasks.entities.Category;
import br.com.mytasks.persistence.dataBase.DataBase;


public class CategoryBD implements IManagerBD<Category>{

    private SQLiteDatabase db;
    private String nameTable;
    private DataBase dtb;

    public CategoryBD(Context context){
        this.dtb = DataBase.getInstance(context);
        nameTable = DataBase.getNameTableCategory();
        this.db = dtb.getWritableDatabase();
    }

    public void insert(Category category){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("name",category.getName());
        contentValues.put("color",category.getColor());
        db.insert(this.nameTable, null, contentValues);
    }


    public void update(Category category, long id){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("name",category.getName());
        contentValues.put("color",category.getColor());
        db.update(this.nameTable, contentValues, "_id = ?", new String[]{"" + id + ""});
    }

    public void delete(long id){
        db.delete(this.nameTable, "_id =" + id, null);
    }

    public List<Category> findAll(){
        List<Category> listAll = new ArrayList<>();
        String[] column ={"_id", "name","color"};
        Cursor cursor = db.query(this.nameTable ,column, null,null,null,null, "_id ");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                listAll.add(new Category(cursor.getLong(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());

        }
        cursor.close();

        return listAll;
    }

    public DataBase getDtb() {
        return dtb;
    }

}
