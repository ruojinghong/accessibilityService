package com.demacia.garen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.demacia.garen.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DialogMenu extends Dialog implements View.OnClickListener {
    private OnClickListener clickListener;
    private Holder holder;

    public static class Builder {
        /* access modifiers changed from: private */
        public List<OnClickMenuListener> clickListeners = new ArrayList();
        private Context context;
        private List<String> menus = new ArrayList();

        public Builder(Context context2) {
            this.context = context2;
        }

        private DialogMenu build() {
            if (this.menus.size() == 0) {
                return null;
            }
            DialogMenu dialogMenu = new DialogMenu(this.context);
            OnClickListener r2 = new OnClickListener() {
                public void onClick(int i) {
                    OnClickMenuListener onClickMenuListener = (OnClickMenuListener) Builder.this.clickListeners.get(i);
                    if (onClickMenuListener != null) {
                        onClickMenuListener.onClick();
                    }
                }
            };
            List<String> list = this.menus;
            dialogMenu.setMenus(r2, (String[]) list.toArray(new String[list.size()]));
            return dialogMenu;
        }

        public Builder setMenu(String str, OnClickMenuListener onClickMenuListener) {
            this.menus.add(str);
            this.clickListeners.add(onClickMenuListener);
            return this;
        }

        public DialogMenu show() {
            DialogMenu build = build();
            if (build != null) {
                build.show();
            }
            return build;
        }
    }

    private class Holder {
        /* access modifiers changed from: private */
        public ArrayList<View> lines;
        /* access modifiers changed from: private */
        public ArrayList<TextView> menus;

        private Holder() {
            this.menus = new ArrayList<>();
            this.lines = new ArrayList<>();
        }
    }

    public interface OnClickListener {
        void onClick(int i);
    }

    public interface OnClickMenuListener {
        void onClick();
    }

    private DialogMenu(Context context) {
        super(context, R.style.Dialog);
        this.clickListener = null;
        this.holder = new Holder();
        setContentView(R.layout.dialog_menu);
        initHolder();
        Iterator it = this.holder.menus.iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(this);
        }
    }

    private void initHolder() {
        this.holder.menus.add((TextView) findViewById(R.id.menu0));
        this.holder.menus.add((TextView) findViewById(R.id.menu1));
        this.holder.menus.add((TextView) findViewById(R.id.menu2));
        this.holder.menus.add((TextView) findViewById(R.id.menu3));
        this.holder.menus.add((TextView) findViewById(R.id.menu4));
        this.holder.menus.add((TextView) findViewById(R.id.menu5));
        this.holder.menus.add((TextView) findViewById(R.id.menu6));
        this.holder.menus.add((TextView) findViewById(R.id.menu7));
        this.holder.menus.add((TextView) findViewById(R.id.menu8));
        this.holder.menus.add((TextView) findViewById(R.id.menu9));
        this.holder.lines.add(findViewById(R.id.line0));
        this.holder.lines.add(findViewById(R.id.line1));
        this.holder.lines.add(findViewById(R.id.line2));
        this.holder.lines.add(findViewById(R.id.line3));
        this.holder.lines.add(findViewById(R.id.line4));
        this.holder.lines.add(findViewById(R.id.line5));
        this.holder.lines.add(findViewById(R.id.line6));
        this.holder.lines.add(findViewById(R.id.line7));
        this.holder.lines.add(findViewById(R.id.line8));
    }

    /* access modifiers changed from: private */
    public void setMenus(OnClickListener onClickListener, String... strArr) {
        this.clickListener = onClickListener;
        ((TextView) this.holder.menus.get(0)).setText(strArr[0]);
        int i = 1;
        while (true) {
            int i2 = i;
            if (i2 < this.holder.menus.size()) {
                if (i2 < strArr.length) {
                    ((TextView) this.holder.menus.get(i2)).setVisibility(View.VISIBLE);
                    ((View) this.holder.lines.get(i2 - 1)).setVisibility(View.VISIBLE);
                    ((TextView) this.holder.menus.get(i2)).setText(strArr[i2]);
                } else {
                    ((TextView) this.holder.menus.get(i2)).setVisibility(View.GONE);
                    ((View) this.holder.lines.get(i2 - 1)).setVisibility(View.GONE);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public static void show(Context context, OnClickListener onClickListener, String... strArr) {
        DialogMenu dialogMenu = new DialogMenu(context);
        dialogMenu.setMenus(onClickListener, strArr);
        dialogMenu.show();
    }

    public void dismiss() {
        super.dismiss();
    }

    public void onClick(View view) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.holder.menus.size()) {
                return;
            }
            if (view.getId() == ((TextView) this.holder.menus.get(i2)).getId()) {
                dismiss();
                OnClickListener onClickListener = this.clickListener;
                if (onClickListener != null) {
                    onClickListener.onClick(i2);
                    return;
                }
                return;
            }
            i = i2 + 1;
        }
    }
}
