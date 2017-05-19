package com.example.rumens.showtime.reader.booksearch;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.rumens.showtime.App;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.BookRecommendListAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */

public class ScanLocalBookActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_localbook_list)
    RecyclerView mRvLocalbookList;
    private BookRecommendListAdapter mAdapter;

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {
        initToolBar(mToolbar,true,"本地书籍");
        mToolbar.setNavigationIcon(R.mipmap.ab_back);
        mAdapter = new BookRecommendListAdapter(this,true);
        RecyclerViewHelper.initRecyclerViewV(this,mRvLocalbookList,true,mAdapter);

        queryFiles();
    }

    private void queryFiles() {
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        // cache
        String bookpath = FileUtils.createRootPath(App.getAppContext());

        // 查询后缀名为txt与pdf，并且不位于项目缓存中的文档
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and ("
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? )",
                new String[]{"%" + bookpath + "%",
                        "%" + Constant.SUFFIX_TXT,
                        "%" + Constant.SUFFIX_PDF,
                        "%" + Constant.SUFFIX_EPUB,
                        "%" + Constant.SUFFIX_CHM}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            List<Recommend.RecommendBooks> list = new ArrayList<>();


            do {
                String path = cursor.getString(dataindex);

                int dot = path.lastIndexOf("/");
                String name = path.substring(dot + 1);
                if (name.lastIndexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."));

                Recommend.RecommendBooks books = new Recommend.RecommendBooks();
                books._id = name;
                books.path = path;
                books.title = name;
                books.isFromSD = true;
                books.lastChapter = FileUtils.formatFileSizeToString(cursor.getLong(sizeindex));

                list.add(books);
            } while (cursor.moveToNext());

            cursor.close();

            mAdapter.updateItems(list);
        } else {
            mAdapter.cleanItems();
        }
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_local_book_scan;
    }

    @Override
    protected void initInjector() {

    }


    public static void startActivity(Context mContext) {
        mContext.startActivity(new Intent(mContext, ScanLocalBookActivity.class));
    }
}
