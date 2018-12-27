package com.example.sk.mtafare;

import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;

public class StyleableSpannableStringBuilder extends SpannableStringBuilder {

    public StyleableSpannableStringBuilder appendWithStyle(CharacterStyle c, CharSequence text) {
        super.append(text);
        int startPos = length() - text.length();
        setSpan(c, startPos, length(), 0);
        return this;
    }

    public StyleableSpannableStringBuilder appendWithStyle(CharacterStyle[] c, CharSequence text) {
        super.append(text);
        int startPos = length() - text.length();
        for (CharacterStyle c1 : c)
            setSpan(c1, startPos, length(), 0);
        return this;
    }
}

