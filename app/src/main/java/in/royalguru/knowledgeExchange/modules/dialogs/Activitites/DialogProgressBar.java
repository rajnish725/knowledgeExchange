package in.royalguru.knowledgeExchange.modules.dialogs.Activitites;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import in.royalguru.knowledgeExchange.R;

/**
 * Created by Kalmeshwar on 05 Jul 2019 at 14:47.
 */
public class DialogProgressBar extends DialogFragment {
    private Activity mActivity = null;

    public DialogProgressBar newInstance(Activity mActivity) {
        DialogProgressBar dialog = new DialogProgressBar();
        dialog.mActivity = mActivity;

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_progress_bar,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getDialog().getWindow().setDimAmount(0.4f);
            getDialog().setCanceledOnTouchOutside(false);
            this.setCancelable(true);
        }
    }
}
