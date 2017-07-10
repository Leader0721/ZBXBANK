package com.pub.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pub.R;
import com.pub.utils.StringUtils;
/**
 * Created by Leader on 2017/5/22.
 *
 */
public class InputInfoDialog extends Dialog implements OnClickListener {
    private Context m_Context;

    private TextView m_textInfo;
    private EditText m_editContent;
    private TextView m_dialogCancel;
    private TextView m_dialogOk;
    private TextView m_textTitle;

    private InputInfoListener m_listener;
    private Object param;

    private String title = "";
    private String info = "";
    private boolean isShow = false;
    String showValue = "";
    private boolean isEditShow = true;

    public InputInfoDialog(Context context, InputInfoListener listener, String title, String info) {
        super(context, R.style.dialog_alert_bg);
        this.m_Context = context;
        this.m_listener = listener;
        this.title = title;
        this.info = info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_pwd);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        m_textInfo = (TextView) findViewById(R.id.tv_Info);
        m_textTitle = (TextView) findViewById(R.id.tv_title);
        if (StringUtils.isEmpty(title)) {
            m_textTitle.setVisibility(View.GONE);
        } else {
            m_textTitle.setText(title);
        }

        if (StringUtils.isEmpty(info)) {
            m_textInfo.setVisibility(View.GONE);
        } else {
            m_textInfo.setText(info);
        }
        m_editContent = (EditText) findViewById(R.id.edit_content);
        m_dialogCancel = (TextView) findViewById(R.id.dialog_cancel);
        m_dialogOk = (TextView) findViewById(R.id.dialog_ok);
        m_dialogCancel.setOnClickListener(this);
        m_dialogOk.setOnClickListener(this);
        if (isShow) {
            m_editContent.setText(showValue);
            m_editContent.setSelection(showValue.length());
        }

    }

    @Override
    public void onClick(View v) {
        if (v == m_dialogOk)
            onBtnOk();
        else if (v == m_dialogCancel)
            onBtnCancel();
    }

    private void onBtnOk() {
        if (isEditShow) {
            if (StringUtils.isEmpty(m_editContent)) {
                Toast.makeText(m_Context, "请输入内容", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (m_listener != null) {
            m_listener.onDialogOk(this, StringUtils.getEditText(m_editContent));
        }
    }

    private void onBtnCancel() {
        cancel();
        if (m_listener != null) {
            m_listener.onDialogCancel(this, param);
        }
    }

    public void setInputValue(String value) {
        isShow = true;
        showValue = value;
    }

    public void setEditTextHint() {
        isEditShow = false;
        m_editContent.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBtnCancel();
        }
        return super.onKeyDown(keyCode, event);
    }
}
