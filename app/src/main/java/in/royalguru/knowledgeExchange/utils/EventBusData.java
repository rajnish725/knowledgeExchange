package in.royalguru.knowledgeExchange.utils;

import in.royalguru.knowledgeExchange.enums.ScreenTypes;

/**
 * Created by Kalmeshwar on 04 Oct 2019 at 19:17.
 */
public class EventBusData {


    private ScreenTypes screenTypes;
    private Object obj1;
    private boolean isChecked = false;
    private String ID;
    private int count;

    public EventBusData(ScreenTypes screenTypes, Object obj1, boolean isChecked, String ID, int count) {
        this.screenTypes = screenTypes;
        this.obj1 = obj1;
        this.isChecked = isChecked;
        this.ID = ID;
        this.count = count;

    }

    public EventBusData(Object obj1, int count) {
        this.obj1 = obj1;
        this.count = count;
    }

    public void setQuestionData(Object object) {
        this.obj1 = object;


    }

    Object getQuestionData() {
        return obj1;

    }

    public ScreenTypes getScreenTypes() {
        return screenTypes;
    }

    public Object getObj1() {
        return obj1;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getID() {
        return ID;
    }

    public int getCount() {
        return count;
    }
}
