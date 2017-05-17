package com.sky.widget.material.edittext;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.sky.widget.R;

/**
 * 输入邮箱自动提示
 * Material 风格
 * 带清空
 * @author skypan
 */
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.DONUT)
public class MaterialMailAutoEditText extends AutoCompleteTextView implements  
OnFocusChangeListener, TextWatcher{
	private static final String TAG = "EmailAutoCompleteTextView";
	public static final int FLOATING_LABEL_NONE = 0;
	public static final int FLOATING_LABEL_NORMAL = 1;
	public static final int FLOATING_LABEL_HIGHLIGHT = 2;
	
	/**
	 * 删除按钮的引用
	 */
    private Drawable mClearDrawable; 
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

	/**
	 * the spacing between the main text and the inner top padding.
	 */
	private int extraPaddingTop;

	/**
	 * the spacing between the main text and the inner bottom padding.
	 */
	private int extraPaddingBottom;

	/**
	 * the floating label's text size.
	 */
	private final int floatingLabelTextSize;

	/**
	 * the spacing between the main text and the inner components (floating label, bottom ellipsis, characters counter).
	 */
	private final int innerComponentsSpacing;

	/**
	 * whether the floating label should be shown. default is false.
	 */
	private boolean floatingLabelEnabled;

	/**
	 * whether to highlight the floating label's text color when focused (with the main color). default is true.
	 */
	private boolean highlightFloatingLabel;

	/**
	 * the base color of the line and the texts. default is black.
	 */
	private int baseColor;

	/**
	 * inner top padding
	 */
	private int innerPaddingTop;

	/**
	 * inner bottom padding
	 */
	private int innerPaddingBottom;

	/**
	 * the underline's highlight color, and the highlight color of the floating label if app:highlightFloatingLabel is set true in the xml. default is black(when app:darkTheme is false) or white(when app:darkTheme is true)
	 */
	private int primaryColor;

	/**
	 * the color for when something is wrong.(e.g. exceeding max characters)
	 */
	private int errorColor;
	
	private int lineColor;

	/**
	 * characters count limit. 0 means no limit. default is 0. NOTE: the character counter will increase the View's height.
	 */
	private int maxCharacters;

	/**
	 * whether to show the bottom ellipsis in singleLine mode. default is false. NOTE: the bottom ellipsis will increase the View's height.
	 */
	private boolean singleLineEllipsis;

	/**
	 * bottom ellipsis's height
	 */
	private final int bottomEllipsisSize;

	/**
	 * animation fraction of the floating label (0 as totally hidden).
	 */
	private float floatingLabelFraction;

	/**
	 * whether the floating label is being shown.
	 */
	private boolean floatingLabelShown;

	/**
	 * the floating label's focusFraction
	 */
	private float focusFraction;

	private ArgbEvaluator focusEvaluator = new ArgbEvaluator();
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	ObjectAnimator labelAnimator;
	ObjectAnimator labelFocusAnimator;
	OnFocusChangeListener interFocusChangeListener;
	OnFocusChangeListener outerFocusChangeListener;
	private String[] emailSufixs = new String[] { "@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com", "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@yeah.net",
			"@vip.qq.com", "@vip.sina.com" };

	public MaterialMailAutoEditText(Context context) {
		this(context, null);
	}

	public MaterialMailAutoEditText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MaterialMailAutoEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);

		floatingLabelTextSize = getResources().getDimensionPixelSize(R.dimen.floating_label_text_size);
		innerComponentsSpacing = getResources().getDimensionPixelSize(R.dimen.inner_components_spacing);
		bottomEllipsisSize = getResources().getDimensionPixelSize(R.dimen.bottom_ellipsis_height);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
		baseColor = typedArray.getColor(R.styleable.MaterialEditText_baseColor, Color.BLACK);
		ColorStateList colorStateList = new ColorStateList(new int[][] {new int[] {android.R.attr.state_enabled}, EMPTY_STATE_SET}, new int[] {baseColor & 0x00ffffff | 0xdf000000, baseColor & 0x00ffffff | 0x44000000});
		setTextColor(colorStateList);
		lineColor = typedArray.getColor(R.styleable.MaterialEditText_lineColor, 0x00a5a5a5);
		primaryColor = typedArray.getColor(R.styleable.MaterialEditText_primaryColor, baseColor);
		setFloatingLabelInternal(typedArray.getInt(R.styleable.MaterialEditText_floatingLabel, 0));
		errorColor = typedArray.getColor(R.styleable.MaterialEditText_errorColor, Color.parseColor("#e7492E"));
		maxCharacters = typedArray.getInt(R.styleable.MaterialEditText_maxCharacters, 0);
		singleLineEllipsis = typedArray.getBoolean(R.styleable.MaterialEditText_singleLineEllipsis, false);
		typedArray.recycle();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			setBackground(null);
		} else {
			setBackgroundDrawable(null);
		}
		if (singleLineEllipsis) {
			TransformationMethod transformationMethod = getTransformationMethod();
			setSingleLine();
			setTransformationMethod(transformationMethod);
		}
		init(context);
		initPadding();
		initText();
		initFloatingLabel();
		initClear();
	}

	public void setAdapterString(String[] es) {
		if (es != null && es.length > 0)
			this.emailSufixs = es;
	}

	@Override
	protected void onDraw( Canvas canvas) {
		// set the textSize
		paint.setTextSize(floatingLabelTextSize);

		// draw the background
		float lineStartY = getScrollY() + getHeight() - getPaddingBottom() + innerComponentsSpacing;
		if (!isEnabled()) { // disabled
			paint.setColor(baseColor & 0x00ffffff | 0x44000000);
			float interval = getPixel(1);
			for (float startX = 0; startX < getWidth(); startX += interval * 3) {
				canvas.drawRect(getScrollX() + startX, lineStartY, getScrollX() + startX + interval, lineStartY + 1, paint);
			}
		} else if (hasFocus()) { // focused
			if (isExceedingMaxCharacters()) {
				paint.setColor(errorColor);
			} else {
				paint.setColor(lineColor);
			}
			canvas.drawRect(getScrollX(), lineStartY, getWidth() + getScrollX(), lineStartY + 1, paint);

			// draw the characters counter
			if (maxCharacters > 0) {
				if (!isExceedingMaxCharacters()) {
					paint.setColor(getCurrentHintTextColor());
				}
				Paint.FontMetrics fontMetrics = paint.getFontMetrics();
				float relativeHeight = -fontMetrics.ascent - fontMetrics.descent;
				String text = getText().length() + " / " + maxCharacters;
				canvas.drawText(text, getWidth() + getScrollX() - paint.measureText(text), lineStartY + innerComponentsSpacing + relativeHeight, paint);
			}
		} else { // normal
			paint.setColor(lineColor);
			canvas.drawRect(getScrollX(), lineStartY, getWidth() + getScrollX(), lineStartY + 1, paint);
		}

		// draw the floating label
		if (floatingLabelEnabled && !TextUtils.isEmpty(getHint())) {
			// calculate the text color
			paint.setColor(primaryColor);

			// calculate the vertical position
			int start = innerPaddingTop + floatingLabelTextSize + innerComponentsSpacing;
			int distance = innerComponentsSpacing;
			int position = (int) (start - distance * floatingLabelFraction);

			// calculate the alpha
			int alpha = (int) (floatingLabelFraction * 0xff * (0.74f * focusFraction + 0.26f));
			paint.setAlpha(alpha);

			// draw the floating label
			canvas.drawText(getHint().toString(), getPaddingLeft() + getScrollX(), position, paint);
		}

		// draw the bottom ellipsis
		if (hasFocus() && singleLineEllipsis && getScrollX() != 0) {
			paint.setColor(primaryColor);
			float startY = lineStartY + innerComponentsSpacing;
			canvas.drawCircle(bottomEllipsisSize / 2 + getScrollX(), startY + bottomEllipsisSize / 2, bottomEllipsisSize / 2, paint);
			canvas.drawCircle(bottomEllipsisSize * 5 / 2 + getScrollX(), startY + bottomEllipsisSize / 2, bottomEllipsisSize / 2, paint);
			canvas.drawCircle(bottomEllipsisSize * 9 / 2 + getScrollX(), startY + bottomEllipsisSize / 2, bottomEllipsisSize / 2, paint);
		}

		// draw the original things
		super.onDraw(canvas);
	}
	
	public boolean isExceedingMaxCharacters() {
		return maxCharacters > 0 && getText() != null && maxCharacters < getText().length();
	}

	
	private void init(final Context context) {
		// adapter中使用默认的emailSufixs中的数据，可以通过setAdapterString来更改
		this.setAdapter(new EmailAutoCompleteAdapter(context, R.layout.item_drop_mail, emailSufixs));
		// 使得在输入3个字符之后便开启自动完成
		this.setThreshold(3);
		this.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					String text = MaterialMailAutoEditText.this.getText().toString();
					// 当该文本域重新获得焦点后，重启自动完成
					if (!"".equals(text))
						performFiltering(text, 0);
				} else {
					// 当文本域丢失焦点后，检查输入email地址的格式
					/**
					 * 改为自行处理
					 */
//					MaterialMailAutoEditText ev = (MaterialMailAutoEditText) v;
//					String text = ev.getText().toString();
//					// 这里正则写的有点粗暴:)
//					if (text != null && text.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
//
//					} else {
//						SHToast.showToast(context, "邮箱格式不正确哦~", 1000);
//					}
				}
			}
		});
		initClear();
	}

	private void initText() {
		if (!TextUtils.isEmpty(getText())) {
			CharSequence text = getText();
			setText(null);
			setHintTextColor(baseColor & 0x00ffffff | 0x44000000);
			setText(text);
			floatingLabelFraction = 1;
			floatingLabelShown = true;
		} else {
			setHintTextColor(baseColor & 0x00ffffff | 0x44000000);
		}
	}

	private float getFloatingLabelFraction() {
		return floatingLabelFraction;
	}

	private void setFloatingLabelFraction(float floatingLabelFraction) {
		this.floatingLabelFraction = floatingLabelFraction;
		invalidate();
	}

	private float getFocusFraction() {
		return focusFraction;
	}

	private void setFocusFraction(float focusFraction) {
		this.focusFraction = focusFraction;
		invalidate();
	}

	private int getPixel(int dp) {
		return Density.dp2px(getContext(), dp);
	}

	private void initPadding() {
		extraPaddingTop = floatingLabelEnabled ? floatingLabelTextSize + innerComponentsSpacing : innerComponentsSpacing;
		extraPaddingBottom = maxCharacters > 0 ? floatingLabelTextSize : singleLineEllipsis ? innerComponentsSpacing + bottomEllipsisSize : 0;
		extraPaddingBottom += innerComponentsSpacing * 2;
		setPaddings(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
	}
	
	private void initClear() { 
    	//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
    	mClearDrawable = getCompoundDrawables()[2]; 
        if (mClearDrawable == null) { 
//        	throw new NullPointerException("You can add drawableRight attribute in XML");
        	mClearDrawable = getResources().getDrawable(R.drawable.delete_selector); 
        } 
        
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight()); 
        //默认设置隐藏图标
        setClearIconVisible(false); 
        //设置焦点改变的监听
        setOnFocusChangeListener(this); 
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this); 
    } 

	/**
	 * use {@link #setPaddings(int, int, int, int)} instead, or the paddingTop and the paddingBottom may be set incorrectly.
	 */
	@Deprecated
	@Override
	public final void setPadding(int left, int top, int right, int bottom) {
		super.setPadding(left, top, right, bottom);
	}

	/**
	 * Use this method instead of {@link #setPadding(int, int, int, int)} to automatically set the paddingTop and the paddingBottom correctly.
	 */
	public void setPaddings(int left, int top, int right, int bottom) {
		innerPaddingTop = top;
		innerPaddingBottom = bottom;
		super.setPadding(left, top + extraPaddingTop, right, bottom + extraPaddingBottom);
	}

	/**
	 * get inner top padding, not the real paddingTop
	 */
	public int getInnerPaddingTop() {
		return innerPaddingTop;
	}

	/**
	 * get inner bottom padding, not the real paddingBottom
	 */
	public int getInnerPaddingBottom() {
		return innerPaddingBottom;
	}

	private void initFloatingLabel() {
		if (floatingLabelEnabled) {
			// observe the text changing
			addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s.length() == 0) {
						if (floatingLabelShown) {
							floatingLabelShown = false;
							getLabelAnimator().reverse();
						}
					} else if (!floatingLabelShown) {
						floatingLabelShown = true;
						if (getLabelAnimator().isStarted()) {
							getLabelAnimator().reverse();
						} else {
							getLabelAnimator().start();
						}
					}
				}
			});
			if (highlightFloatingLabel) {
				// observe the focus state to animate the floating label's text color appropriately
				interFocusChangeListener = new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							if (getLabelFocusAnimator().isStarted()) {
								getLabelFocusAnimator().reverse();
							} else {
								getLabelFocusAnimator().start();
							}
						} else {
							getLabelFocusAnimator().reverse();
						}
						if (outerFocusChangeListener != null) {
							outerFocusChangeListener.onFocusChange(v, hasFocus);
						}
					}
				};
				super.setOnFocusChangeListener(interFocusChangeListener);
			}
		}

	}

	public void setBaseColor(int color) {
		baseColor = color;
		postInvalidate();
	}

	public void setPrimaryColor(int color) {
		primaryColor = color;
		postInvalidate();
	}

	private void setFloatingLabelInternal(int mode) {
		switch (mode) {
			case FLOATING_LABEL_NORMAL:
				floatingLabelEnabled = true;
				highlightFloatingLabel = false;
				break;
			case FLOATING_LABEL_HIGHLIGHT:
				floatingLabelEnabled = true;
				highlightFloatingLabel = true;
				break;
			default:
				floatingLabelEnabled = false;
				highlightFloatingLabel = false;
				break;
		}
	}

	public void setFloatingLabel(int mode) {
		setFloatingLabelInternal(mode);
		postInvalidate();
	}

	public void setSingleLineEllipsis() {
		setSingleLineEllipsis(true);
	}

	public void setSingleLineEllipsis(boolean enabled) {
		singleLineEllipsis = enabled;
		postInvalidate();
	}

	public int getMaxCharacters() {
		return maxCharacters;
	}

	public void setMaxCharacters(int max) {
		maxCharacters = max;
		postInvalidate();
	}

	public void setErrorColor(int color) {
		errorColor = color;
		postInvalidate();
	}

	@Override
	public void setOnFocusChangeListener(OnFocusChangeListener listener) {
		if (interFocusChangeListener == null) {
			super.setOnFocusChangeListener(listener);
		} else {
			outerFocusChangeListener = listener;
		}
	}

	private ObjectAnimator getLabelAnimator() {
		if (labelAnimator == null) {
			labelAnimator = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f);
		}
		return labelAnimator;
	}

	private ObjectAnimator getLabelFocusAnimator() {
		if (labelFocusAnimator == null) {
			labelFocusAnimator = ObjectAnimator.ofFloat(this, "focusFraction", 0f, 1f);
		}
		return labelFocusAnimator;
	}
	
	@Override
	protected void replaceText(CharSequence text) {
		// 当我们在下拉框中选择一项时，android会默认使用AutoCompleteTextView中Adapter里的文本来填充文本域
		// 因为这里Adapter中只是存了常用email的后缀
		// 因此要重新replace逻辑，将用户输入的部分与后缀合并
		Log.i(TAG + " replaceText", text.toString());
		String t = this.getText().toString();
		int index = t.indexOf("@");
		if (index != -1)
			t = t.substring(0, index);
		super.replaceText(t + text);
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode) {
		// 该方法会在用户输入文本之后调用，将已输入的文本与adapter中的数据对比，若它匹配
		// adapter中数据的前半部分，那么adapter中的这条数据将会在下拉框中出现
		Log.i(TAG + " performFiltering", text.toString() + "   " + keyCode);
		String t = text.toString();
		// 因为用户输入邮箱时，都是以字母，数字开始，而我们的adapter中只会提供以类似于"@163.com"
		// 的邮箱后缀，因此在调用super.performFiltering时，传入的一定是以"@"开头的字符串
		int index = t.indexOf("@");
		if (index == -1) {
			if (t.matches("^[a-zA-Z0-9_]+$")) {
				super.performFiltering("@", keyCode);
			} else
				this.dismissDropDown();// 当用户中途输入非法字符时，关闭下拉提示框
		} else {
			super.performFiltering(t.substring(index), keyCode);
		}
	}

	private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {
		public EmailAutoCompleteAdapter(Context context, int textViewResourceId, String[] email_s) {
			super(context, textViewResourceId, email_s);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i(TAG, "in GetView");
			View v = convertView;
			if (v == null)
				v = LayoutInflater.from(getContext()).inflate(R.layout.item_drop_mail, null);
			TextView tv = (TextView) v.findViewById(R.id.tv_drop);
			String t = MaterialMailAutoEditText.this.getText().toString();
			int index = t.indexOf("@");
			if (index != -1)
				t = t.substring(0, index);
			// 将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示
			tv.setText(t + getItem(position));
			Log.i(TAG, tv.getText().toString());
			return v;
		}

	}
	
	 /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override 
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				
				if (touchable) {
					this.setText("");
				}
			}
		}

		return super.onTouchEvent(event);
	}
 
    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override 
    public void onFocusChange(View v, boolean hasFocus) { 
    	this.hasFoucs = hasFocus;
        if (hasFocus) { 
            setClearIconVisible(getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        } 
    } 
 
 
    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) { 
        Drawable right = visible ? mClearDrawable : null; 
        setCompoundDrawables(getCompoundDrawables()[0], 
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
    } 
    
    
    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, 
            int after) { 
    	if(hasFoucs){
    		setClearIconVisible(s.length() > 0);
    	}
    } 
 
    @Override 
    public void beforeTextChanged(CharSequence s, int start, int count, 
            int after) { 
         
    } 
 
    @Override 
    public void afterTextChanged(Editable s) { 
         
    } 
}