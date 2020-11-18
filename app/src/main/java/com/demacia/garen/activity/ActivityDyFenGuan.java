package com.demacia.garen.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.alibaba.fastjson.asm.Opcodes;
import com.demacia.garen.R;
import com.demacia.garen.utils.SPHelper;

public class ActivityDyFenGuan extends BaseActivity implements View.OnClickListener {
    private static final String SP_Key_fen = "DyFenGuanfen";
    private static final String SP_Key_root = "DyFenGuan";
    private static final String SP_Key_runSpeed = "DyFenGuanrunSpeed";
    private Holder holder = new Holder();

    private class Holder {
        /* access modifiers changed from: private */
        public EditText fenCount;
        /* access modifiers changed from: private */
        public EditText runSpeed;

        private Holder() {
        }
    }

    private void initHolder() {
        EditText unused = this.holder.fenCount = (EditText) findViewById(R.id.fenCount);
        EditText unused2 = this.holder.runSpeed = (EditText) findViewById(R.id.runSpeed);
    }

    private void initView() {
        EditText access$100 = this.holder.fenCount;
        access$100.setText("" + readFen(this.context));
        EditText access$200 = this.holder.runSpeed;
        access$200.setText("" + readRunSpeed(this.context));
        this.holder.fenCount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    SPHelper.putInt(ActivityDyFenGuan.this.context, ActivityDyFenGuan.SP_Key_fen, Integer.valueOf(charSequence.toString()).intValue());
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
                    SPHelper.putInt(ActivityDyFenGuan.this.context, ActivityDyFenGuan.SP_Key_runSpeed, Integer.valueOf(charSequence.toString()).intValue());
                } catch (Exception e) {
                }
            }
        });
    }

    public static Integer readFen(Context context) {
        return Integer.valueOf(SPHelper.getInt(context, SP_Key_fen, Opcodes.GOTO_W));
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
        setContentView((int) R.layout.activity_dy_fen_guan);
        initHolder();
        initView();
    }
}
