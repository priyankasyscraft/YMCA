package com.ymca.CustomFont;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ymca.R;

/**
 * Created by Soni on 29-Jul-16.
 */
public class SecondaryFontText extends TextView {
    public SecondaryFontText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
            init(context,attrs);
    }

    public SecondaryFontText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
            init(context,attrs);
    }

    public SecondaryFontText(Context context) {
        super(context);
        if(!isInEditMode())
            init(context,null);
    }

    private void init(Context context,AttributeSet attrs) {
//        if (attrs!=null) {
//            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SecondaryFontText);
//            String fontName = typedArray.getString(R.styleable.SecondaryFontText_fontNames);
//            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/cachet-std-book.ttf");

//                Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/helveticaneueltcom45lt.ttf" + fontName);
                setTypeface(myTypeface);
//            }
//            typedArray.recycle();
//        }
    }



}
