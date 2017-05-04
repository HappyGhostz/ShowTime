package com.example.rumens.showtime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.rumens.showtime.R;

/**
 * @author Zhaochen Ping
 * @create 2017/4/22
 * @description
 */

public class RippleView extends RelativeLayout {
    // 点击加速度
    private static final int RIPPLE_ACCELERATE = 20;
    // 5种触摸状态
    private static final int RIPPLE_NORMAL = 0;
    private static final int RIPPLE_SINGLE = 1;
    private static final int RIPPLE_LONG_PRESS = 2;
    private static final int RIPPLE_ACTION_MOVE = 3;
    private static final int RIPPLE_ACTION_UP = 4;
    // 触摸状态
    private int rippleStatus = RIPPLE_NORMAL;
    private int rippleColor;
    private int rippleType;
    private boolean hasToZoom;
    private boolean isCentered;
    private int rippleDuration=400;
    private int frameRate=10;
    private int rippleAlpha=90;
    private int ripplePadding;
    private Handler canvasHandler;
    private float zoomScale;
    private int zoomDuration;
    private boolean isListMode;
    private Paint paint;
    private int lastLongPressX;
    private int lastLongPressY;
    private int touchSlop;
    private GestureDetector mGestureDetector;
    private boolean animationOnRunning=false;
    private ScaleAnimation scaleAnimation;
    private int WIDTH;
    private int HEIGHT;
    private int radiusMax=0;
    private float x = -1;
    private float y = -1;
    private Bitmap originBitmap;
    private int timer = 0;
    private OnRippleCompleteListener onCompletionListener;
    private int durationEmpty=-1;
    private int timerEmpty=0;

    public RippleView(Context context) {
        this(context,null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if(isInEditMode()){
            return;
        }
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        rippleColor = typedArray.getColor(R.styleable.RippleView_rv_color, Color.parseColor("#33626262"));
        rippleType = typedArray.getInt(R.styleable.RippleView_rv_type, 0);
        hasToZoom = typedArray.getBoolean(R.styleable.RippleView_rv_zoom, false);
        isCentered = typedArray.getBoolean(R.styleable.RippleView_rv_centered, false);
        rippleDuration = typedArray.getInteger(R.styleable.RippleView_rv_rippleDuration, rippleDuration);
        frameRate = typedArray.getInteger(R.styleable.RippleView_rv_framerate, frameRate);
        rippleAlpha = typedArray.getInteger(R.styleable.RippleView_rv_alpha, rippleAlpha);
        ripplePadding = typedArray.getDimensionPixelSize(R.styleable.RippleView_rv_ripplePadding, 0);
        canvasHandler = new Handler();
        zoomScale = typedArray.getFloat(R.styleable.RippleView_rv_zoomScale, 1.03f);
        zoomDuration = typedArray.getInt(R.styleable.RippleView_rv_zoomDuration, 200);
        isListMode = typedArray.getBoolean(R.styleable.RippleView_rv_listMode, false);
        typedArray.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(rippleColor);
        paint.setAlpha(rippleAlpha);
        this.setWillNotDraw(false);
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                animateRipple(e);
                sendClickEvent(true);
                lastLongPressX = (int) e.getX();
                lastLongPressY = (int) e.getY();
                rippleStatus = RIPPLE_LONG_PRESS;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return true;
            }
        });
        this.setDrawingCacheEnabled(true);
        this.setClickable(true);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animationOnRunning) {
            if (isListMode && (rippleStatus == RIPPLE_SINGLE
                    || rippleStatus == RIPPLE_ACTION_MOVE || rippleStatus == RIPPLE_ACTION_UP)) {
                doRippleWork(canvas, RIPPLE_ACCELERATE);
            } else {
                doRippleWork(canvas, 1);
            }
        }
    }

    private void doRippleWork(Canvas canvas, int offect) {
        canvas.save();
        if(rippleDuration<=timer*frameRate){
            canvas.drawCircle(x, y, (radiusMax * (((float) timer * frameRate) / rippleDuration)), paint);
            if(onCompletionListener!=null&&rippleStatus!=RIPPLE_ACTION_MOVE&&rippleStatus!=RIPPLE_LONG_PRESS){
                onCompletionListener.onComplete(this);
            }
            if(rippleStatus!=RIPPLE_LONG_PRESS){
                rippleStatus= RIPPLE_NORMAL;
                timer=0;
                durationEmpty = -1;
                timerEmpty = 0;
                animationOnRunning=false;
                if(Build.VERSION.SDK_INT!=23){
                    canvas.restore();
                }
            }
            invalidate();
            return;
        }else {
            canvasHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, frameRate);
        }
        if(timer==0){
            canvas.save();
        }
        canvas.drawCircle(x, y, (radiusMax * (((float) timer * frameRate) / rippleDuration)), paint);
        paint.setColor(Color.parseColor("#ffff4444"));

        if (rippleType == 1 && originBitmap != null && (((float) timer * frameRate) / rippleDuration) > 0.4f) {
            if (durationEmpty == -1)
                durationEmpty = rippleDuration - timer * frameRate;

            timerEmpty++;
            final Bitmap tmpBitmap = getCircleBitmap((int) ((radiusMax) * (((float) timerEmpty * frameRate) / (durationEmpty))));
            canvas.drawBitmap(tmpBitmap, 0, 0, paint);
            tmpBitmap.recycle();
        }

        paint.setColor(rippleColor);
        if (!isListMode) {
            if (rippleType == 1) {
                if ((((float) timer * frameRate) / rippleDuration) > 0.6f)
                    paint.setAlpha((int) (rippleAlpha - ((rippleAlpha) * (((float) timerEmpty * frameRate) / (durationEmpty)))));
                else
                    paint.setAlpha(rippleAlpha);
            } else
                paint.setAlpha((int) (rippleAlpha - ((rippleAlpha) * (((float) timer * frameRate) / rippleDuration))));
        }
        timer += offect;

    }

    private Bitmap getCircleBitmap(int radius) {
        final Bitmap output = Bitmap.createBitmap(originBitmap.getWidth(), originBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect((int) (x - radius), (int) (y - radius), (int) (x + radius), (int) (y + radius));

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(x, y, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, rect, rect, paint);

        return output;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
        scaleAnimation = new ScaleAnimation(1.0f, zoomScale, 1.0f, zoomScale, w / 2, h / 2);
        scaleAnimation.setDuration(zoomDuration);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(1);
    }

    private void sendClickEvent(boolean isLongClick) {
        if (getParent() instanceof AdapterView) {
            final AdapterView adapterView = (AdapterView) getParent();
            final int position = adapterView.getPositionForView(this);
            final long id = adapterView.getItemIdAtPosition(position);
            if (isLongClick) {
                if (adapterView.getOnItemLongClickListener() != null)
                    adapterView.getOnItemLongClickListener().onItemLongClick(adapterView, this, position, id);
            } else {
                if (adapterView.getOnItemClickListener() != null)
                    adapterView.getOnItemClickListener().onItemClick(adapterView, this, position, id);
            }
        }
    }

    private void animateRipple(MotionEvent event) {
        createAnimation(event.getX(), event.getY());
    }

    private void createAnimation(float x, float y) {
        if(this.isEnabled()&&!animationOnRunning){
            if(hasToZoom){
                this.startAnimation(scaleAnimation);
            }
            radiusMax = Math.max(WIDTH, HEIGHT);
            if(rippleType!=2){
                radiusMax/=2;
            }
            radiusMax-=ripplePadding;
            if(isCentered&&rippleType==1){
                this.x =getMeasuredWidth()/2;
                this.y = getMeasuredHeight()/2;
            }else {
                this.x=x;
                this.y=y;
            }
            animationOnRunning=true;
            if (rippleType == 1 && originBitmap == null)
                originBitmap = getDrawingCache(true);

            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception  e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mGestureDetector.onTouchEvent(event)){
            animateRipple(event);
            sendClickEvent(false);
            rippleStatus = RIPPLE_SINGLE;
        }
        if (rippleStatus == RIPPLE_LONG_PRESS) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                rippleStatus = RIPPLE_ACTION_UP;
            } else if (Math.abs(event.getX() - lastLongPressX) >= touchSlop ||
                    Math.abs(event.getY() - lastLongPressY) >= touchSlop) {
                rippleStatus = RIPPLE_ACTION_MOVE;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnRippleCompleteListener(OnRippleCompleteListener listener) {
        this.onCompletionListener = listener;
    }

    /**
     * Defines a callback called at the end of the Ripple effect
     */
    public interface OnRippleCompleteListener {
        void onComplete(RippleView rippleView);
    }

    public enum RippleType {
        SIMPLE(0),
        DOUBLE(1),
        RECTANGLE(2);

        int type;

        RippleType(int type) {
            this.type = type;
        }
    }
}
