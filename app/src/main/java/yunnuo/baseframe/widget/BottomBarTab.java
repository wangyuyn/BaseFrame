package yunnuo.baseframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yunnuo.baseframe.R;
import yunnuo.baseframe.utils.ContextUtils;


public class BottomBarTab extends RelativeLayout {
    private ImageView mIcon;
    private Context mContext;
    private TextView mTextView;
    private int mTabPosition = -1;
    private int icon;
    private static boolean ifshow = false;

    public BottomBarTab(Context context, @DrawableRes int icon, String title) {
        this(context, null, icon,  title);
    }


    public BottomBarTab(Context context, AttributeSet attrs, int icon, String title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, String title) {
//        setBackgroundResource(R.drawable.bottom_bg);
        mContext = context;
        this.icon =icon;
       /* TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();*/
        //android.R.attr.selectableItemBackground
        //android.R.attr.selectableItemBackgroundBorderless
        TypedArray typedArray;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            typedArray = context.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackgroundBorderless});
        }else {
            typedArray = context.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        }
        Drawable drawable = typedArray.getDrawable(0);
       setBackground(drawable);
        typedArray.recycle();

//        setOrientation(LinearLayout.VERTICAL);
        mIcon = new ImageView(context);
//        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = ContextUtils.dip2px(context, 7f);
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);
        mIcon.setId(R.id.icon);

        // mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        LayoutParams textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textViewParams.topMargin = ContextUtils.dip2px(context, 2.5f);
        textViewParams.bottomMargin = ContextUtils.dip2px(context, 2f);
        mTextView = new TextView(context);
        mTextView.setText(title);
        mTextView.setTextSize(10);
        mTextView.setLayoutParams(textViewParams);
        addView(mIcon);
        addView(mTextView);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.main_text_select));

        } else {
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.main_text_unselect));
        }
    }


    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
