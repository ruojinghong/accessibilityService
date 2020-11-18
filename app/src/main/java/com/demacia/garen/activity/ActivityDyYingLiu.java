package com.demacia.garen.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.demacia.garen.R;
import com.demacia.garen.utils.SPHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityDyYingLiu extends BaseActivity implements View.OnClickListener {
    private static final String SP_Key_at = "DyYingLiuat";
    private static final String[] SP_Key_msgs = {"DyYingLiumsg0", "DyYingLiumsg1", "DyYingLiumsg2", "DyYingLiumsg3", "DyYingLiumsg4"};
    private static final String SP_Key_root = "DyYingLiu";
    private static final String SP_Key_runSpeed = "DyYingLiurunSpeed";
    private Holder holder = new Holder();

    private class Holder {
        /* access modifiers changed from: private */
        public EditText atTarget;
        /* access modifiers changed from: private */
        public ArrayList<EditText> msgs;
        /* access modifiers changed from: private */
        public EditText runSpeed;

        private Holder() {
            this.msgs = new ArrayList<>();
        }
    }

    private void initHolder() {
        EditText unused = this.holder.atTarget = (EditText) findViewById(R.id.atTarget);
        EditText unused2 = this.holder.runSpeed = (EditText) findViewById(R.id.runSpeed);
        this.holder.msgs.add((EditText) findViewById(R.id.msg0));
        this.holder.msgs.add((EditText) findViewById(R.id.msg1));
        this.holder.msgs.add((EditText) findViewById(R.id.msg2));
        this.holder.msgs.add((EditText) findViewById(R.id.msg3));
        this.holder.msgs.add((EditText) findViewById(R.id.msg4));
    }

    private void initView() {
        int i = 0;
        EditText access1 = this.holder.atTarget;
        access1.setText("" + readAt(this.context));
        EditText access2 = this.holder.runSpeed;
        access2.setText("" + readRunSpeed(this.context));
        int i2 = 0;
        while (true) {
            String[] strArr = SP_Key_msgs;
            if (i2 >= strArr.length) {
                break;
            }
            ((EditText) this.holder.msgs.get(i2)).setText(readMsg(this.context, strArr[i2]));
            i2++;
        }
        this.holder.atTarget.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SPHelper.putString(ActivityDyYingLiu.this.context, ActivityDyYingLiu.SP_Key_at, charSequence.toString());
            }
        });
        this.holder.runSpeed.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    SPHelper.putInt(ActivityDyYingLiu.this.context, ActivityDyYingLiu.SP_Key_runSpeed, Integer.parseInt(charSequence.toString()));
                } catch (Exception e) {
                }
            }
        });
        while (true) {
            String[] strArr2 = SP_Key_msgs;
            if (i < strArr2.length) {
                final String str = strArr2[i];
                ((EditText) this.holder.msgs.get(i)).addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable editable) {
                    }

                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        SPHelper.putString(ActivityDyYingLiu.this.context, str, charSequence.toString());
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    public static String randomReadMsg(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String readMsg : SP_Key_msgs) {
            String readMsg2 = readMsg(context, readMsg);
            if (!TextUtils.isEmpty(readMsg2)) {
                arrayList.add(readMsg2);
            }
        }
        if (arrayList.size() <= 0) {
            return "";
        }
        Collections.shuffle(arrayList);
        return (String) arrayList.get(0);
    }

    public static String readAt(Context context) {
        return SPHelper.getString(context, SP_Key_at, "").trim();
    }

    private static String readMsg(Context context, String str) {
        String string = SPHelper.getString(context, str, (String) null);
        if (!TextUtils.isEmpty(string)) {
            String trim = string.trim();
            if (!TextUtils.isEmpty(trim)) {
                return trim;
            }
        }
        return "";
    }

    public static Integer readRunSpeed(Context context) {
        return Integer.valueOf(Math.max(1, SPHelper.getInt(context, SP_Key_runSpeed, 1)));
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStatusBarColor(-1, false);
        setContentView((int) R.layout.activity_dy_ying_liu);
        initHolder();
        initView();
    }
}
