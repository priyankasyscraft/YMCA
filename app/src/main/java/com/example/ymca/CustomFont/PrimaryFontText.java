package com.example.ymca.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Soni on 29-Jul-16.
 */
public class PrimaryFontText extends TextView {

    public PrimaryFontText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    public PrimaryFontText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);

    }

    public PrimaryFontText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Cachet_Std_Bold.otf", context);
        setTypeface(customFont);
    }

}
