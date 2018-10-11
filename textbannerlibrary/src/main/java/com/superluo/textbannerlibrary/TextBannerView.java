package com.superluo.textbannerlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnimRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.superluo.textbannerlibrary.utils.DisplayUtils;

import java.util.List;

/**
 *
 * @描述 文字自动轮播（跑马灯）
 * @author luoweichao
 * @email superluo666@gmail.com
 * @date 2018/3/28/028 21:21
 *
 */
public class TextBannerView extends RelativeLayout {
    private ViewFlipper mViewFlipper;
    private int mInterval = 3000;/**文字切换时间间隔,默认3s*/
    private boolean isSingleLine = false;/**文字是否为单行,默认false*/
    private int mTextColor = 0xff000000;/**设置文字颜色,默认黑色*/
    private int mTextSize = 16; /**设置文字尺寸,默认16px*/
    private int mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;/**文字显示位置,默认左边居中*/
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;

    private boolean hasSetDirection = false;
    private int direction = DIRECTION_BOTTOM_TO_TOP;
    private static final int DIRECTION_BOTTOM_TO_TOP = 0;
    private static final int DIRECTION_TOP_TO_BOTTOM = 1;
    private static final int DIRECTION_RIGHT_TO_LEFT = 2;
    private static final int DIRECTION_LEFT_TO_RIGHT = 3;
    @AnimRes
    private int inAnimResId = R.anim.anim_right_in;
    @AnimRes
    private int outAnimResId = R.anim.anim_left_out;
    private boolean hasSetAnimDuration = false;
    private int animDuration = 1500;/**默认1.5s*/
    private int mFlags = -1;/**文字划线*/
    private static final int STRIKE = 0;
    private static final int UNDER_LINE = 1;
    private int mTypeface = Typeface.NORMAL;/**设置字体类型：加粗、斜体、斜体加粗*/
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_BOLD = 1;
    private static final int TYPE_ITALIC = 2;
    private static final int TYPE_ITALIC_BOLD = 3;

    private List<String> mDatas;
    private ITextBannerItemClickListener mListener;
    private boolean isStarted;
    private boolean isDetachedFromWindow;


    public TextBannerView(Context context) {
        this(context,null);
    }

    public TextBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**初始化控件*/
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextBannerViewStyle, defStyleAttr, 0);
        mInterval = typedArray.getInteger(R.styleable.TextBannerViewStyle_setInterval, mInterval);//文字切换时间间隔
        isSingleLine = typedArray.getBoolean(R.styleable.TextBannerViewStyle_setSingleLine, false);//文字是否为单行
        mTextColor = typedArray.getColor(R.styleable.TextBannerViewStyle_setTextColor, mTextColor);//设置文字颜色
        if (typedArray.hasValue(R.styleable.TextBannerViewStyle_setTextSize)) {//设置文字尺寸
            mTextSize = (int) typedArray.getDimension(R.styleable.TextBannerViewStyle_setTextSize, mTextSize);
            mTextSize = DisplayUtils.px2sp(context, mTextSize);
        }
        int gravityType = typedArray.getInt(R.styleable.TextBannerViewStyle_setGravity, GRAVITY_LEFT);//显示位置
        switch (gravityType) {
            case GRAVITY_LEFT:
                mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case GRAVITY_CENTER:
                mGravity = Gravity.CENTER;
                break;
            case GRAVITY_RIGHT:
                mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        hasSetAnimDuration = typedArray.hasValue(R.styleable.TextBannerViewStyle_setAnimDuration);
        animDuration = typedArray.getInt(R.styleable.TextBannerViewStyle_setAnimDuration, animDuration);//动画时间
        hasSetDirection = typedArray.hasValue(R.styleable.TextBannerViewStyle_setDirection);
        direction = typedArray.getInt(R.styleable.TextBannerViewStyle_setDirection, direction);//方向
        if (hasSetDirection) {
            switch (direction) {
                case DIRECTION_BOTTOM_TO_TOP:
                    inAnimResId = R.anim.anim_bottom_in;
                    outAnimResId = R.anim.anim_top_out;
                    break;
                case DIRECTION_TOP_TO_BOTTOM:
                    inAnimResId = R.anim.anim_top_in;
                    outAnimResId = R.anim.anim_bottom_out;
                    break;
                case DIRECTION_RIGHT_TO_LEFT:
                    inAnimResId = R.anim.anim_right_in;
                    outAnimResId = R.anim.anim_left_out;
                    break;
                case DIRECTION_LEFT_TO_RIGHT:
                    inAnimResId = R.anim.anim_left_in;
                    outAnimResId = R.anim.anim_right_out;
                    break;
            }
        } else {
            inAnimResId = R.anim.anim_right_in;
            outAnimResId = R.anim.anim_left_out;
        }
        mFlags = typedArray.getInt(R.styleable.TextBannerViewStyle_setFlags, mFlags);//字体划线
        switch (mFlags) {
            case STRIKE:
                mFlags = Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG;
                break;
            case UNDER_LINE:
                mFlags = Paint.UNDERLINE_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG;
                break;
            default:
                mFlags = 0|Paint.ANTI_ALIAS_FLAG;
                break;
        }
        mTypeface = typedArray.getInt(R.styleable.TextBannerViewStyle_setTypeface, mTypeface);//字体样式
        switch (mTypeface) {
            case TYPE_BOLD:
                mTypeface = Typeface.BOLD;
                break;
            case TYPE_ITALIC:
                mTypeface = Typeface.ITALIC;
                break;
            case TYPE_ITALIC_BOLD:
                mTypeface = Typeface.ITALIC|Typeface.BOLD;
                break;
            default:
                break;
        }


        mViewFlipper = new ViewFlipper(getContext());//new 一个ViewAnimator
        mViewFlipper.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mViewFlipper);
        startViewAnimator();
        //设置点击事件
        mViewFlipper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mViewFlipper.getDisplayedChild();//当前显示的子视图的索引位置
                if (mListener!=null){
                    mListener.onItemClick(mDatas.get(position),position);
                }
            }
        });

    }

    /**暂停动画*/
    public void stopViewAnimator(){
        if (isStarted){
            removeCallbacks(mRunnable);
            isStarted = false;
        }
    }

    /**开始动画*/
    public void startViewAnimator(){
        if (!isStarted){
            if (!isDetachedFromWindow){
                isStarted = true;
                postDelayed(mRunnable,mInterval);
            }
        }
    }

    /**
     * 设置延时间隔
     */
    private AnimRunnable mRunnable = new AnimRunnable();
    private class AnimRunnable implements Runnable{

        @Override
        public void run() {
            if (isStarted){
                setInAndOutAnimation(inAnimResId, outAnimResId);
                mViewFlipper.showNext();//手动显示下一个子view。
                postDelayed(this,mInterval + animDuration);
            }else {
                stopViewAnimator();
            }

        }
    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(animDuration);
        mViewFlipper.setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(animDuration);
        mViewFlipper.setOutAnimation(outAnim);
    }



    /**设置数据集合*/
    public void setDatas(List<String> datas){
        this.mDatas = datas;
        if (DisplayUtils.notEmpty(mDatas)){
            mViewFlipper.removeAllViews();
            for (int i = 0; i < mDatas.size(); i++) {
                TextView textView = new TextView(getContext());
                setTextView(textView,i);

                mViewFlipper.addView(textView,i);//添加子view,并标识子view位置
            }
        }

    }

    /**
     * 设置数据集合伴随drawable-icon
     * @param datas 数据
     * @param drawable 图标
     * @param size 图标尺寸
     * @param direction 图标位于文字方位
     */
    public void setDatasWithDrawableIcon(List<String> datas, Drawable drawable,int size, int direction){
        this.mDatas = datas;
        if (DisplayUtils.isEmpty(mDatas)){
            return;
        }
        mViewFlipper.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            TextView textView = new TextView(getContext());
            setTextView(textView,i);

            textView.setCompoundDrawablePadding(8);
            float scale = getResources().getDisplayMetrics().density;// 屏幕密度 ;
            int muchDp = (int) (size * scale + 0.5f);
            drawable.setBounds(0, 0, muchDp, muchDp);
            if (direction==Gravity.LEFT){
                textView.setCompoundDrawables(drawable,null,null , null);//左边
            }else if (direction==Gravity.TOP){
                textView.setCompoundDrawables(null,drawable,null , null);//顶部
            }else if (direction==Gravity.RIGHT){
                textView.setCompoundDrawables(null,null, drawable, null);//右边
            }else if (direction==Gravity.BOTTOM){
                textView.setCompoundDrawables(null,null, null, drawable);//底部
            }


            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);//水平方向
            linearLayout.setGravity(mGravity);//子view显示位置跟随TextView
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.
                    LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(textView,param);

            mViewFlipper.addView(linearLayout,i);//添加子view,并标识子view位置
        }
    }
    /**设置TextView*/
    private void setTextView(TextView textView,int position){
        textView.setText(mDatas.get(position));
        //任意设置你的文字样式，在这里
        textView.setSingleLine(isSingleLine);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(mTextColor);
        textView.setTextSize(mTextSize);
        textView.setGravity(mGravity);
        textView.getPaint().setFlags(mFlags);//字体划线
        textView.setTypeface(null, mTypeface);//字体样式
    }


    /**设置点击监听事件回调*/
    public void setItemOnClickListener(ITextBannerItemClickListener listener){
        this.mListener = listener;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isDetachedFromWindow=true;
        stopViewAnimator();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isDetachedFromWindow=false;
        startViewAnimator();

    }
}
