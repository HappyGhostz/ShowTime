package com.example.rumens.showtime.reader;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.reader.bookclassify.BookClassifyListFragment;
import com.example.rumens.showtime.reader.booklist.BookTypeListFragment;
import com.example.rumens.showtime.reader.bookrank.BookRankListFragment;
import com.example.rumens.showtime.reader.booksearch.ScanLocalBookActivity;
import com.example.rumens.showtime.reader.booksearch.SearchResultActivity;
import com.example.rumens.showtime.reader.downloadservice.DownloadBookService;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.SharedPreferencesUtil;
import com.example.rumens.showtime.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Zhaochen Ping
 * @create 2017/4/18
 * @description
 */

public class ReaderFragment extends BaseFragment {

    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.bt_search)
    ImageButton mBtSearch;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_new_layout)
    TabLayout mTabNewLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;


    private List<String> titles;
    private List<Fragment> fragments;
    private ViewPagerAdapter pagerAdapter;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_reader_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        getActivity().startService(new Intent(getActivity(), DownloadBookService.class));
        initToolBar(mToolBar, true, "小说");
        setHasOptionsMenu(true);
        pagerAdapter = new ViewPagerAdapter(getFragmentManager());


    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("书架");
        titles.add("分类");
        titles.add("社区");
        titles.add("排行榜");
        fragments = new ArrayList<>();
        fragments.add(BookTypeListFragment.lunch("书架"));
        fragments.add(BookClassifyListFragment.lunch("分类"));
        fragments.add(BookTypeListFragment.lunch("社区"));
        fragments.add(BookRankListFragment.lunch("排行榜"));

    }

    @Override
    protected void updateViews() {
        initData();
        pagerAdapter.setItems(fragments, titles);
        mViewpager.setAdapter(pagerAdapter);
        mTabNewLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DownloadBookService.cancel();
        getActivity().stopService(new Intent(getActivity(), DownloadBookService.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_channel:
                new AlertDialog.Builder(mContext)
                        .setTitle("阅读页翻页效果")
                        .setSingleChoiceItems(getResources().getStringArray(R.array.setting_dialog_style_choice),
                                SharedPreferencesUtil.getInstance().getInt(Constant.FLIP_STYLE, 0),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferencesUtil.getInstance().putInt(Constant.FLIP_STYLE, which);
                                        dialog.dismiss();
                                    }
                                })
                        .create().show();
                break;
            case R.id.action_scan_local_book:
                ScanLocalBookActivity.startActivity(mContext);
                break;
        }
        return false;
    }

    @OnClick(R.id.bt_search)
    public void onViewClicked() {
        String bookName = mEtInput.getText().toString();
        if(TextUtils.isEmpty(bookName)){
            ToastUtils.showToast("请输入书名");
            return;
        }else {
            SearchResultActivity.lunch(mContext,bookName);
        }

    }
}
