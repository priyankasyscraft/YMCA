package com.example.ymca.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Soni on 29-Jul-16.
 */
public class SecondaryFontText extends TextView {

    public SecondaryFontText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    public SecondaryFontText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);

    }

    public SecondaryFontText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("assets/ufonts.com_cachetbook.ttf", context);
        setTypeface(customFont);
    }

}
