package com.gerus.themovie.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gerus.themovie.R;

import org.apmem.tools.layouts.FlowLayout;


public class DialogFilter extends Dialog implements View.OnClickListener {
    private Activity mActivity;
    private FlowLayout mLinerLayout;


    public DialogFilter(Activity poActivity) {
        super(poActivity, R.style.AppTheme);
        mActivity = poActivity;
        //mInterface = poInterface;
        setContentView(R.layout.dialog_filter);

        findByIds();
        show();
    }

    public void findByIds() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLinerLayout = (FlowLayout) findViewById(R.id.radioGroup);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        int numero = (int) (Math.random() * 10) + 1;
        for (int row = 0; row < numero; row++) {

            for (int i = 1; i <= numero; i++) {
                final CheckBox rdbtn = new CheckBox(new ContextThemeWrapper(mActivity, R.style.Chip), null, R.style.Chip);
                rdbtn.setLayoutParams(new ViewGroup.LayoutParams((int) getContext().getResources().getDimension(R.dimen.chip_checkbox), ViewGroup.LayoutParams.WRAP_CONTENT));
                rdbtn.setText("i"+i);
                rdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "setOnClickListener "+rdbtn.isChecked(),Toast.LENGTH_SHORT).show();
                    }
                });
                rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Toast.makeText(mActivity, "setOnCheckedChangeListener "+rdbtn.isChecked(),Toast.LENGTH_SHORT).show();
                    }
                });
                mLinerLayout.addView(rdbtn);
            }
            //((ViewGroup) findViewById(R.id.radiogroup)).addView(mRadio);
        }


    }


    @Override
    public void onClick(View v) {

    }



}