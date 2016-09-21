package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.Database;
import es.esy.varto_novomyrgorod.varto.database.dao.interfaces.ScheduleDAOInterface;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.ScheduleSchema;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class ScheduleDAO implements ScheduleDAOInterface, ScheduleSchema {
    private static final String TAG = "ScheduleDAO";
    private Context context;

    public ScheduleDAO(Context context) {
        this.context = context;
    }

    @Override
    public void update(List<Schedule> schedules) {
        Cursor cursor = null;
        try {
            cursor = Database.getInstance(context).query(TAG_TABLE_SCHEDULE,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int rowsAffected = Database.getInstance(context).delete(TAG_TABLE_SCHEDULE,
                        null, null);
                Log.i(TAG, "[TABLE: schedule]SQL:  SUCCESS DELETE, deleted count rows: "
                        + rowsAffected);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        for (int i = 0; i < schedules.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Schedule item = schedules.get(i);
            contentValues.put(TAG_SHOP, item.getShop());
            contentValues.put(TAG_SUNDAY, item.getSunday());
            contentValues.put(TAG_MONDAY, item.getMonday());
            contentValues.put(TAG_TUESDAY, item.getTuesday());
            contentValues.put(TAG_WEDNESDAY, item.getWednesday());
            contentValues.put(TAG_THURSDAY, item.getThursday());
            contentValues.put(TAG_FRIDAY, item.getFriday());
            contentValues.put(TAG_SATURDAY, item.getSaturday());
            Log.i(TAG, "[TABLE: schedule]SQL: Result SQL insert: "
                    + String.valueOf(Database.getInstance(context)
                    .insert(TAG_TABLE_SCHEDULE, null, contentValues)));
        }
    }

    @Override
    public Schedule get(Shop shop) {
        Schedule schedule = new Schedule();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        Cursor cursor = null;
        try {
            cursor = Database.getInstance(context)
                    .query(TAG_TABLE_SCHEDULE, null, whereArgs, whereValues, null, null, null);
            if (cursor.moveToFirst()) {
                int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
                int sundayColIndex = cursor.getColumnIndex(TAG_SUNDAY);
                int mondayColIndex = cursor.getColumnIndex(TAG_MONDAY);
                int tuesdayColIndex = cursor.getColumnIndex(TAG_TUESDAY);
                int wednesdayColIndex = cursor.getColumnIndex(TAG_WEDNESDAY);
                int thursdayColIndex = cursor.getColumnIndex(TAG_THURSDAY);
                int fridayColIndex = cursor.getColumnIndex(TAG_FRIDAY);
                int saturdayColIndex = cursor.getColumnIndex(TAG_SATURDAY);

                schedule.setShop(cursor.getString(shopColIndex));
                schedule.setSunday(cursor.getString(sundayColIndex));
                schedule.setMonday(cursor.getString(mondayColIndex));
                schedule.setTuesday(cursor.getString(tuesdayColIndex));
                schedule.setWednesday(cursor.getString(wednesdayColIndex));
                schedule.setThursday(cursor.getString(thursdayColIndex));
                schedule.setFriday(cursor.getString(fridayColIndex));
                schedule.setSaturday(cursor.getString(saturdayColIndex));
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return schedule;
    }
}
