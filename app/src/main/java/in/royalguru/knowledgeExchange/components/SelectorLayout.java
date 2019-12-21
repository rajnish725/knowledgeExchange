package in.royalguru.knowledgeExchange.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import in.royalguru.knowledgeExchange.R;

/**
 * Created by Kalmeshwar on 03 Jul 2019 at 11:33.
 */
public class SelectorLayout extends RelativeLayout {
    public SelectorLayout(Context mContext) {
        super(mContext);
        initComponents(mContext, null);
    }

    public SelectorLayout(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        initComponents(mContext, attrs);
    }

    public SelectorLayout(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        initComponents(mContext, attrs);
    }

    private void initComponents(Context mContext, AttributeSet attrs) {
        View itemView = inflate(getContext(), R.layout.comp_selector_layout, null);

        View selection_view = itemView.findViewById(R.id.selection_view);

        TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
                attrs, R.styleable.SelectorLayout, 0, 0);

        int isVisibleSelector;

        try {
            isVisibleSelector = styledAttributes.getInteger(R.styleable.SelectorLayout_selector_visibility, 0);

            selection_view.setVisibility((isVisibleSelector == 1) ? View.VISIBLE : View.GONE);

//            edt_content.setHint(hintText);
//            txt_widget_title.setText(title_text);
//
//
//            switch (text_type) {
//                case 0://numeric
//                    edt_content.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    break;
//                case 1://alphaNumeric
//                    edt_content.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    break;
//                case 2://text password
//                    edt_content.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    break;
//                case 3://number password
//                    edt_content.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    edt_content.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    break;
//                case 4://email
//                    edt_content.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//                    break;
//            }
        } finally {
            styledAttributes.recycle();
        }

        addView(itemView);
    }
}
