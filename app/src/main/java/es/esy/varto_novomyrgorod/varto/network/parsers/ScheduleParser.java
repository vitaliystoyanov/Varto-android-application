package es.esy.varto_novomyrgorod.varto.network.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Schedule;

public class ScheduleParser extends AbstractParser<Schedule> {

    @Override
    protected List<Schedule> parseToList(JSONObject object) throws JSONException {
        JSONArray timetablesArray = object.getJSONArray(JSON_TIMETABLES);
        ArrayList<Schedule> parsedSchedule = new ArrayList<>();
        for (int i = 0; i < timetablesArray.length(); i++) {
            Schedule schedule = new Schedule();
            JSONObject jsonObject = timetablesArray.getJSONObject(i);

            schedule.setShop(jsonObject.getString(JSON_SHOP));
            schedule.setSunday(jsonObject.getString(JSON_SUNDAY));
            schedule.setMonday(jsonObject.getString(JSON_MONDAY));
            schedule.setTuesday(jsonObject.getString(JSON_TUESDAY));
            schedule.setWednesday(jsonObject.getString(JSON_WEDNESDAY));
            schedule.setThursday(jsonObject.getString(JSON_THURSDAY));
            schedule.setFriday(jsonObject.getString(JSON_FRIDAY));
            schedule.setSaturday(jsonObject.getString(JSON_SATURDAY));

            parsedSchedule.add(schedule);
        }
        return parsedSchedule;
    }
}
