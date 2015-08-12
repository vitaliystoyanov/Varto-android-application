package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.ScheduleObject;

public class DBScheduleProvider {
    private static final String LOG_TAG = "DBG";
    private static final String TAG_TABLE = "timetable";
    private static final String TAG_SHOP = "shop";
    private static final String TAG_SUNDAY = "sunday";
    private static final String TAG_MONDAY = "monday";
    private static final String TAG_TUESDAY = "tuesday";
    private static final String TAG_WEDNESDAY = "wednesday";
    private static final String TAG_THURSDAY = "thursday";
    private static final String TAG_FRIDAY = "friday";
    private static final String TAG_SATURDAY = "saturday";
    private DBHelper localDBHelper;

    public DBScheduleProvider(DBHelper helper) {
        this.localDBHelper = helper;
    }

    public void setTimetablesObjectToDB(List<ScheduleObject> listTimetables) {
        if (!listTimetables.isEmpty()) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(LOG_TAG, "DB connect: " + DBConnect.toString());

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        int clearCount = DBConnect.delete(TAG_TABLE, null, null);
                        Log.i(LOG_TAG, "SQL:  SUCCESS DELETE, deleted count rows: " + clearCount);
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }

                for (int i = 0; i < listTimetables.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    ScheduleObject scheduleObject = listTimetables.get(i);

                    contentValues.put(TAG_SHOP, scheduleObject.getShop());
                    contentValues.put(TAG_SUNDAY,scheduleObject.getSunday());
                    contentValues.put(TAG_MONDAY, scheduleObject.getMonday());
                    contentValues.put(TAG_TUESDAY, scheduleObject.getTuesday());
                    contentValues.put(TAG_WEDNESDAY, scheduleObject.getWednesday());
                    contentValues.put(TAG_THURSDAY, scheduleObject.getThursday());
                    contentValues.put(TAG_FRIDAY, scheduleObject.getFriday());
                    contentValues.put(TAG_SATURDAY, scheduleObject.getSaturday());

                    Log.i(LOG_TAG, "Size content value to insert to DB: " + String.valueOf(contentValues.size()));
                    Log.i(LOG_TAG, "SQL: Result SQL insert: " + String.valueOf(DBConnect.insert(TAG_TABLE, null, contentValues)));
                }

            } finally {
                if (DBConnect != null) DBConnect.close();
            }
        } else {
            Log.i(LOG_TAG, "List<ScheduleObject> is empty!");
        }
    }

    public ScheduleObject getScheduleFromDB(String shop){
        if (shop != null) {
            ScheduleObject scheduleObject = new ScheduleObject();
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(LOG_TAG, "DB connect: " + DBConnect.toString());

                String whereArgs = "shop = ?";
                String[] whereValues = new String[]{shop};

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE, null, whereArgs, whereValues, null, null, null);
                    if (cursor.moveToFirst()) {
                        Log.i(LOG_TAG, "SQL: query success!Finded row");
                        int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
                        int sundayColIndex = cursor.getColumnIndex(TAG_SUNDAY);
                        int mondayColIndex = cursor.getColumnIndex(TAG_MONDAY);
                        int tuesdayColIndex = cursor.getColumnIndex(TAG_TUESDAY);
                        int wednesdayColIndex = cursor.getColumnIndex(TAG_WEDNESDAY);
                        int thursdayColIndex = cursor.getColumnIndex(TAG_THURSDAY);
                        int fridayColIndex = cursor.getColumnIndex(TAG_FRIDAY);
                        int saturdayColIndex = cursor.getColumnIndex(TAG_SATURDAY);

                        scheduleObject.setShop(cursor.getString(shopColIndex));
                        scheduleObject.setSunday(cursor.getString(sundayColIndex));
                        scheduleObject.setMonday(cursor.getString(mondayColIndex));
                        scheduleObject.setTuesday(cursor.getString(tuesdayColIndex));
                        scheduleObject.setWednesday(cursor.getString(wednesdayColIndex));
                        scheduleObject.setThursday(cursor.getString(thursdayColIndex));
                        scheduleObject.setFriday(cursor.getString(fridayColIndex));
                        scheduleObject.setSaturday(cursor.getString(saturdayColIndex));
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } finally {
                if (DBConnect != null) DBConnect.close();
            }
            return scheduleObject;
        } else {
            Log.i(LOG_TAG, "getScheduleFromDB(String shop): Argument a shop is NULL!");
            return null;
        }
    }
}
