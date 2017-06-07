package com.example.rumens.showtime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.rumens.showtime.R;

/**
 * @author Zhaochen Ping
 * @create 2017/6/3
 * @description
 */

public class CircleImageView extends ImageView {
    /**
     * 圆形头像默认，CENTER_CROP!=系统默认的CENTER_CROP；
     * 将图片等比例缩放，让图像的长边边与ImageView的边长度相同，短边不够的留空白，缩放后截取圆形部分进行显示。
     */
    private static final ScaleType SCALE_TYPE=ScaleType.CENTER_CROP;
    /**
     * 图片的压缩质量
     * ALPHA_8就是Alpha由8位组成，------ALPHA_8 代表8位Alpha位图
     * ARGB_4444就是由4个4位组成即16位，------ARGB_4444 代表16位ARGB位图
     * ARGB_8888就是由4个8位组成即32位，------ARGB_8888 代表32位ARGB位图
     * RGB_565就是R为5位，G为6位，B为5位共16位，------ARGB_565 代表8位RGB位图
     */
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    /**
     * 默认ColorDrawable的宽和高
     */
    private static final int COLOR_DRAWABLE_DEFAULT=1;
    /**
     * 默认边框宽度
     */
    private  static final int DEFAULT_BORDER_WIDTH = 0;

    /**
     * 默认边框颜色
     */
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    /**
     * 画图片的矩形
     */
    private RectF mDrawaleRect = new RectF();
    /**
     * 画边框的矩形
     */
    private RectF mBorderRect = new RectF();
    /**
     * 对图片进行缩放和移动的矩阵
     */
    private final Matrix mShaderMatrix = new Matrix();
    /**
     * 画图片的画笔
     */
    private final Paint mDrawablePaint = new Paint();
    /**
     * 画边框的画笔
     */
    private final Paint mBorderPaint = new Paint();
    /**
     * 默认边框颜色
     */
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    /**
     * 默认边框宽度
     */
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

    private Bitmap mBitmap;
    /**
     * 产生一个画有一个位图的渲染器（Shader）
     */
    private BitmapShader mBitmapShader;
    /**
     * 图片的实际宽度
     */
    private int mBitmapWidth;
    /**
     * 图片实际高度
     */
    private int mBitmapHeight;
    /**
     * 图片半径
     */
    private float mDrawableRadius;
    /**
     * 边框半径
     */
    private float mBorderRadius;
    /**
     * 是否初始化准备好
     */
    private boolean mReady;
    /**
     * 内边距
     */
    private boolean mSetupPending;
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(SCALE_TYPE);
        /**
         * 获取在xml中声明的属性
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        typedArray.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width,DEFAULT_BORDER_WIDTH);
        typedArray.getColor(R.styleable.CircleImageView_civ_border_color,DEFAULT_BORDER_COLOR);
        typedArray.recycle();
        mReady=true;
        if(mSetupPending){
            setUp();
            mSetupPending=false;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable()==null){
            return;
        }
        /**
         * 画圆形图片
         */
        canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,mDrawablePaint);/**
         * 画圆形边框
         */
        canvas.drawCircle(getWidth()/2,getHeight()/2,mBorderRadius,mBorderPaint);

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap=bm;
        setUp();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap=getBitmapFromDrawable(drawable);
        setUp();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap=getBitmapFromDrawable(getDrawable());
        setUp();
    }
    /**
     * 获取资源图片
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if(drawable==null){
            return null;
        }
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if(drawable instanceof ColorDrawable){
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DEFAULT,COLOR_DRAWABLE_DEFAULT,BITMAP_CONFIG);
            }else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }catch (OutOfMemoryError e){
            return null;
        }
    }
    /**
     * 画圆形图的方法
     */
    private void setUp() {
        if(!mReady){
            mSetupPending=true;
            return;
        }
        if(mBitmap==null){
            return;
        }
        /**
         *调用这个方法来产生一个画有一个位图的渲染器（Shader）。
         bitmap   在渲染器内使用的位图
         tileX      The tiling mode for x to draw the bitmap in.   在位图上X方向花砖模式
         tileY     The tiling mode for y to draw the bitmap in.    在位图上Y方向花砖模式
         TileMode：（一共有三种）
         CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
         REPEAT ：横向和纵向的重复渲染器图片，平铺。
         MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像方式平铺。
         */
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        /**
         * 设置画圆形的画笔
         */
        mDrawablePaint.setAntiAlias(true);
        mDrawablePaint.setShader(mBitmapShader);
        /**
         * 设置画边框的画笔
         */
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBitmapWidth);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
        /**
         * 设置边框矩形的坐标
         */
        mBorderRect.set(0,0,getWidth(),getHeight());
        /**
         * 设置边框圆形的半径为图片的宽度和高度的一半的最大值
         */
        mBorderRadius=Math.max((mBorderRect.height()-mBorderWidth)/2,(mBorderRect.width()-mBorderWidth)/2);
        /**
         * 设置图片矩形的坐标
         */
        mDrawaleRect.set(mBorderWidth,mBorderWidth,mBorderRect.width() - mBorderWidth, mBorderRect.height() - mBorderWidth);
        /**
         * 设置图片圆形的半径为图片的宽度和高度的一半的最大值
         */
        mDrawableRadius = Math.max(mDrawaleRect.height() / 2, mDrawaleRect.width() / 2);

        updateShaderMatrix();
        /**
         * 调用onDraw()方法进行绘画
         */
        invalidate();
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;
        /**
         * 重置
         */
        mShaderMatrix.set(null);
        /**
         *计算缩放比，因为如果图片的尺寸超过屏幕，那么就会自动匹配到屏幕的尺寸去显示。
         * 确定移动的xy坐标
         *
         */
        if(mBitmapWidth*mDrawaleRect.height()>mDrawaleRect.width()*mBitmapHeight){
            scale = mDrawaleRect.width()/(float)mBitmapWidth;
            dy=(mDrawaleRect.height()-mBitmapHeight*scale)*0.5f;
        }else {
            scale = mDrawaleRect.height()/(float)mBitmapHeight;
            dx=(mDrawaleRect.width()-mBitmapWidth*scale)*0.5f;
        }
        mShaderMatrix.setScale(scale,scale);
        mShaderMatrix.postTranslate((int)(dx+0.5f)+mBorderWidth,(int)(dy+0.5f)+mBorderWidth);
        /**
         * 设置shader的本地矩阵
         */
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    /**
     * 获取边框颜色
     *
     * @return
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * 设置边框颜色
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }

        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    /**
     * 获取边框宽度
     *
     * @return
     */
    public int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }

        mBorderWidth = borderWidth;
        setUp();
    }

}
