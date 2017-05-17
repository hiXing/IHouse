package com.sky.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义EditText
 * 
 * @author skypan
 */
public class SHEditTextNoClear extends EditText {

	public SHEditTextNoClear(Context context) {
		this(context, null);
	}

	public SHEditTextNoClear(Context context, AttributeSet attrs) {
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public SHEditTextNoClear(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setHintTextColor(Color.BLACK & 0x00ffffff | 0x44000000);
	}


}
