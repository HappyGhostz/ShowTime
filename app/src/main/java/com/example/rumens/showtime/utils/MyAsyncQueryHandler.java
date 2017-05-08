package com.example.rumens.showtime.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;





/**
 * Created by fullcircle on 2016/11/18.
 */

public class MyAsyncQueryHandler extends AsyncQueryHandler {
    public MyAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        //查询结束之后走这个方法 刷新listview 需要一个adapter对象
        //cursor 就是最新的查询结果 拿着这个cursor就可以刷新界面
        CursorAdapter adapter = (CursorAdapter) cookie;
        //替换下来的游标 changeCursor直接关闭  swapCursor通过返回值给这个游标返回来
        adapter.changeCursor(cursor); //使用最新的游标刷新界面
        //adapter.swapCursor(cursor);
    }
}
