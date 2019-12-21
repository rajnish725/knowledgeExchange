package in.royalguru.knowledgeExchange.listeners;

import in.royalguru.knowledgeExchange.enums.ScreenTypes;

/**
 * Created by Kalmeshwar on 04 Jun 2019 at 13:03.
 */
public interface AppOperationsListener {
    void onListenOperation(ScreenTypes screenType, Object obj1, Object obj2, String stringValue, boolean isChecked);
}