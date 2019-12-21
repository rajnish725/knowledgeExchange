package in.royalguru.knowledgeExchange.listeners;

import android.view.View;

import in.royalguru.knowledgeExchange.enums.EnumClicks;

/**
 * Created by Kalmeshwar on 03 Jul 2019 at 16:39.
 */
public interface OnItemClickListener {
    void onItemClickListener(EnumClicks where, View view, int position, Object obj1, Object obj2, Object obj3, boolean isChecked);
}