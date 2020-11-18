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

public class ActivityDyPingXing extends BaseActivity implements View.OnClickListener {
    private static final String[] SP_Key_msgs = {"DyShiXingmsg0", "DyShiXingmsg1", "DyShiXingmsg2", "DyShiXingmsg3", "DyShiXingmsg4"};
    private static final String SP_Key_root = "DyShiXing";
    private static final String SP_Key_runSpeed = "DyShiXingrunSpeed";
    private static final String SP_Key_shixing = "DyShiXingshixing";
    private Holder holder = new Holder();

    private class Holder {
        /* access modifiers changed from: private */
        public ArrayList<EditText> msgs;
        /* access modifiers changed from: private */
        public EditText runSpeed;
        /* access modifiers changed from: private */
        public EditText shixingCount;

        private Holder() {
            this.msgs = new ArrayList<>();
        }
    }

    private void initHolder() {
        EditText unused = this.holder.shixingCount = (EditText) findViewById(R.id.shixingCount);
        EditText unused2 = this.holder.runSpeed = (EditText) findViewById(R.id.runSpeed);
        this.holder.msgs.add((EditText) findViewById(R.id.msg0));
        this.holder.msgs.add((EditText) findViewById(R.id.msg1));
        this.holder.msgs.add((EditText) findViewById(R.id.msg2));
        this.holder.msgs.add((EditText) findViewById(R.id.msg3));
        this.holder.msgs.add((EditText) findViewById(R.id.msg4));
    }

    private void initView() {
        int i = 0;
        EditText access$100 = this.holder.shixingCount;
        access$100.setText("" + readShiXing(this.context));
        EditText access$200 = this.holder.runSpeed;
        access$200.setText("" + readRunSpeed(this.context));
        int i2 = 0;
        while (true) {
            String[] strArr = SP_Key_msgs;
            if (i2 >= strArr.length) {
                break;
            }
            ((EditText) this.holder.msgs.get(i2)).setText(readMsg(this.context, strArr[i2]));
            i2++;
        }
        this.holder.shixingCount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    SPHelper.putInt(ActivityDyPingXing.this.context, ActivityDyPingXing.SP_Key_shixing, Integer.valueOf(charSequence.toString()).intValue());
                } catch (Exception e) {
                }
            }
        });
        this.holder.runSpeed.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    SPHelper.putInt(ActivityDyPingXing.this.context, ActivityDyPingXing.SP_Key_runSpeed, Integer.valueOf(charSequence.toString()).intValue());
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
                        SPHelper.putString(ActivityDyPingXing.this.context, str, charSequence.toString());
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    public static String randomReadMsg(Context context) {
        ArrayList arrayList = new ArrayList();
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

    public static Integer readShiXing(Context context) {
        return Integer.valueOf(Math.max(0, SPHelper.getInt(context, SP_Key_shixing, 100)));
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStatusBarColor(-1, false);
        setContentView((int) R.layout.activity_dy_ping_xing);
        initHolder();
        initView();
    }
}
