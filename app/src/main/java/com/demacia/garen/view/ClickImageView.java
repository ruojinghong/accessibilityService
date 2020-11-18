package com.demacia.garen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ClickImageView extends androidx.appcompat.widget.AppCompatImageView {
    public ClickImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
        if (r0 != 3) goto L_0x001d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r4) {
        return super.onTouchEvent(r4);
        /*
            r3 = this;
            r2 = 1065353216(0x3f800000, float:1.0)
            boolean r0 = r3.isClickable()
            if (r0 != 0) goto L_0x000e
            boolean r0 = r3.isLongClickable()
            if (r0 == 0) goto L_0x0022
        L_0x000e:
            int r0 = r4.getAction()
            if (r0 == 0) goto L_0x002b
            r1 = 1
            if (r0 == r1) goto L_0x0027
            r1 = 2
            if (r0 == r1) goto L_0x002b
            r1 = 3
            if (r0 == r1) goto L_0x0027
        L_0x001d:
            boolean r0 = super.onTouchEvent(r4)
        L_0x0021:
            return r0
        L_0x0022:
            r3.setAlpha(r2)
            r0 = 0
            goto L_0x0021
        L_0x0027:
            r3.setAlpha(r2)
            goto L_0x001d
        L_0x002b:
            r0 = 1063675494(0x3f666666, float:0.9)
            r3.setAlpha(r0)
            goto L_0x001d
        */
        // throw new UnsupportedOperationException("Method not decompiled: com.lisyx.tap.view.ClickImageView.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
