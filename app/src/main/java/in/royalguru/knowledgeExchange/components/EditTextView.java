package in.royalguru.knowledgeExchange.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import in.royalguru.knowledgeExchange.R;

/**
 * Created by Kalmeshwar on 24 May 2019 at 13:23.
 */
public class EditTextView extends RelativeLayout {
    private TextView txt_widget_title;
    private ImageView img_clear_value;
    private EditText edt_content;
    private View selection_view;
    private int maxLength = 0;

    public EditTextView(Context context) {
        super(context);
        initComponents(context, null);
    }

    public EditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponents(context, attrs);
    }

    public EditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponents(context, attrs);
    }

    private void initComponents(Context context, AttributeSet attrs) {
        View itemView = inflate(getContext(), R.layout.comp_edit_text_view, null);

        TextView txt_country_code;
        CardView card_parent_view;

        txt_widget_title = itemView.findViewById(R.id.txt_widget_title);
        txt_country_code = itemView.findViewById(R.id.txt_country_code);

        img_clear_value = itemView.findViewById(R.id.img_clear_value);
        edt_content = itemView.findViewById(R.id.edt_content);

        selection_view = itemView.findViewById(R.id.selection_view);

        card_parent_view = itemView.findViewById(R.id.card_parent_view);

        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.EditTextView, 0, 0);

        String hintText = "", title_text = "";
        int text_type, isVisibleCountryCode;

        try {
            hintText = styledAttributes.getString(R.styleable.EditTextView_hint);
            title_text = styledAttributes.getString(R.styleable.EditTextView_title);
            text_type = styledAttributes.getInteger(R.styleable.EditTextView_text_type, 0);

            ColorStateList cardBgClr = styledAttributes.getColorStateList(R.styleable.EditTextView_bg_clr);
            if (cardBgClr != null) {
                card_parent_view.setCardBackgroundColor(cardBgClr);
            } else {
                int bgClr = ContextCompat.getColor(context, R.color.grey_clr);
                card_parent_view.setCardBackgroundColor(bgClr);
            }

            edt_content.setHint(hintText);
            txt_widget_title.setText(title_text);

            isVisibleCountryCode = styledAttributes.getInteger(R.styleable.EditTextView_visible_country_code, 0);
            txt_country_code.setVisibility((isVisibleCountryCode == 1) ? View.VISIBLE : View.GONE);

            switch (text_type) {
                case 0://numeric
                    edt_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case 1://alphaNumeric
                    edt_content.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    break;
                case 2://text password
                    edt_content.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    break;
                case 3://number password
                    edt_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edt_content.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    break;
                case 4://email
                    edt_content.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
            }
        } finally {
            styledAttributes.recycle();
        }

        img_clear_value.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_content.setText("");
            }
        });

        edt_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                img_clear_value.setVisibility((charSequence.length() > 0) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_content.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                setViewVisibility(hasFocus);
            }
        });

        addView(itemView);
    }

    public void setViewColor(int viewColor) {
        selection_view.setBackgroundColor(viewColor);
        invalidate();
        requestLayout();
    }

    public void setViewVisibility(boolean isVisible) {
        selection_view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        invalidate();
        requestLayout();
    }

    public void setCloseViewVisibility(boolean isVisible) {
        img_clear_value.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        invalidate();
        requestLayout();
    }

    public void setText(String text) {
        edt_content.setText(text);
        invalidate();
        requestLayout();
    }

    public void setTitle(String title) {
        txt_widget_title.setText(title);
        invalidate();
        requestLayout();
    }

    public void setHint(String hint) {
        edt_content.setHint(hint);
        invalidate();
        requestLayout();
    }

    public void enableDisableView(boolean isEnable) {
        edt_content.setEnabled(isEnable);
        invalidate();
        requestLayout();
    }

    public void setFocus() {
        edt_content.requestFocus();
        invalidate();
        requestLayout();
    }

    public void openSoftKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edt_content, InputMethodManager.SHOW_IMPLICIT);
        invalidate();
        requestLayout();
    }

    public void hideSoftKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_content.getWindowToken(), 0);
        invalidate();
        requestLayout();
    }

    public String getText() {
        return edt_content.getText().toString().trim();
    }

    public void setMaxCharValue(int maxLength) {
        this.maxLength = maxLength;
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        edt_content.setFilters(FilterArray);
    }

    public void addOnTextChangeListener(final LengthChangeListener lengthListener) {
        edt_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (s.length() >= maxLength) {
                        lengthListener.onTextChange(true);
                    } else {
                        lengthListener.onTextChange(maxLength == 0);
                    }
                } else
                    lengthListener.onTextChange(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public interface LengthChangeListener {
        void onTextChange(boolean b);
    }
}