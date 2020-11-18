package com.demacia.garen.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.demacia.garen.R;
import com.demacia.garen.utils.LOG;

public class DialogAlert {

    public interface OnClickListener {
        void onClick(int i);
    }

    public interface OnInputListener {
        void onInput(String str);
    }

    public static void show(Context context, String str, String str2, final OnClickListener onClickListener1, String... strArr) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(str)) {
            builder.setTitle(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            builder.setMessage(str2);
        }
        if (strArr.length == 1) {
            builder.setPositiveButton(strArr[0], new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // TODO: 2020/11/17
                    OnClickListener onClickListener = onClickListener1;
                    if (onClickListener != null) {
                        onClickListener.onClick(0);
                    }
                }
            });
        } else if (strArr.length == 2) {
            builder.setNeutralButton(strArr[0], new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // TODO: 2020/11/17
                    OnClickListener onClickListener = onClickListener1;
                    if (onClickListener != null) {
                        onClickListener.onClick(0);
                    }
                }
            });
            builder.setPositiveButton(strArr[1], new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // TODO: 2020/11/17
                    OnClickListener onClickListener = onClickListener1;
                    if (onClickListener != null) {
                        onClickListener.onClick(1);
                    }
                }
            });
        } else if (strArr.length == 3) {
            builder.setNeutralButton(strArr[0], new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    OnClickListener onClickListener = onClickListener1;
                    if (onClickListener != null) {
                        onClickListener.onClick(0);
                    }
                }
            });
            builder.setNegativeButton(strArr[1], new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    OnClickListener onClickListener = onClickListener1;
                    if (onClickListener != null) {
                        onClickListener.onClick(1);
                    }
                }
            });
            builder.setPositiveButton(strArr[2], new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (onClickListener1 != null) {
                        onClickListener1.onClick(2);
                    }
                }
            });
        }
        try {
            builder.show();
        } catch (Exception e) {
            LOG.m11v("activity 可能已关闭");
        }
    }

    public static void showInput(Context context, String str, String str2, final OnInputListener onInputListener1) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_input, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.input);
        editText.setText(str2);
        editText.selectAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setView(inflate);
        builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                OnInputListener onInputListener = onInputListener1;
                if (onInputListener != null) {
                    onInputListener.onInput(editText.getText().toString());
                }
            }
        });
        builder.show();
    }

    public static void showInputNumber(final Context context, String str, int i, final OnInputListener onInputListener1) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_input_number, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.input);
        editText.setText(String.valueOf(i));
        editText.selectAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setView(inflate);
        builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int i2;
                try {
                    i2 = Integer.valueOf(editText.getText().toString()).intValue();
                } catch (Exception e) {
                    LOG.toast(context, "输入的数字不合法");
                    e.printStackTrace();
                    i2 = -1;
                }
                if (i2 >= 0) {
                    OnInputListener onInputListener = onInputListener1;
                    if (onInputListener != null) {
                        onInputListener.onInput(String.valueOf(i2));
                        return;
                    }
                    return;
                }
                LOG.toast(context, "输入的数字不正确");
            }
        });
        builder.show();
    }

    public static void showMultiInput(Context context, String str, String str2, final OnInputListener onInputListener1) {
        if (str2 == null) {
            str2 = "";
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_multi_input, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.input);
        editText.setText(str2);
        editText.setSelection(str2.length());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setView(inflate);
        builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                OnInputListener onInputListener = onInputListener1;
                if (onInputListener != null) {
                    onInputListener.onInput(editText.getText().toString());
                }
            }
        });
        builder.show();
    }
}
