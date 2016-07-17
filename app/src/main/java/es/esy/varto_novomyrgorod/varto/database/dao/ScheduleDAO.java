package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;
import es.esy.varto_novomyrgorod.varto.database.dao.interfaces.ScheduleDAOInterface;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.ScheduleSchema;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class ScheduleDAO implements ScheduleDAOInterface, ScheduleSchema {
    private static final String TAG = "ScheduleDAO";
    private final Context context;

    public ScheduleDAO(Context context) {
        this.context = context;
    }

    @Override
    public void add(List<Schedule> listOfItems) {
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_SCHEDULE,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int clearCount = DatabaseProvider.getInstance(context).delete(TAG_TABLE_SCHEDULE,
                        null, null);
                Log.i(TAG, "[TABLE: schedule]SQL:  SUCCESS DELETE, deleted count rows: " + clearCount);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        for (int i = 0; i < listOfItems.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Schedule schedule = listOfItems.get(i);
            contentValues.put(TAG_SHOP, schedule.getShop());
            contentValues.put(TAG_SUNDAY, schedule.getSunday());
            contentValues.put(TAG_MONDAY, schedule.getMonday());
            contentValues.put(TAG_TUESDAY, schedule.getTuesday());
            contentValues.put(TAG_WEDNESDAY, schedule.getWednesday());
            contentValues.put(TAG_THURSDAY, schedule.getThursday());
            contentValues.put(TAG_FRIDAY, schedule.getFriday());
            contentValues.put(TAG_SATURDAY, schedule.getSaturday());
            Log.i(TAG, "[TABLE: schedule]SQL: Result SQL insert: "
                    + String.valueOf(DatabaseProvider.getInstance(context)
                    .insert(TAG_TABLE_SCHEDULE, null, contentValues)));
        }
    }

    @Override
    public Schedule get(Shop shop) {
        Schedule schedule = new Schedule();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()}; // FIXME: 6/29/16 repeat again

        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context)
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
