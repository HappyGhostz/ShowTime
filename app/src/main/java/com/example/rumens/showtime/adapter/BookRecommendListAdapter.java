package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.reader.bookdetail.BookDetailActivity;
import com.example.rumens.showtime.reader.bookread.ReadActivity;
import com.example.rumens.showtime.reader.booksearch.ScanLocalBookActivity;
import com.example.rumens.showtime.rxBus.EventManager;
import com.example.rumens.showtime.utils.BookSettingManager;
import com.example.rumens.showtime.utils.CollectionsManager;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FileUtils;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.utils.ToastUtils;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookRecommendListAdapter extends BaseQuickAdapter<Recommend.RecommendBooks> {
    private String mBookListType;
    private boolean isLocalBook;

    public BookRecommendListAdapter(Context context, String mBookListType) {
        super(context);
        this.mBookListType = mBookListType;
    }

    public BookRecommendListAdapter(ScanLocalBookActivity context, boolean isLocalBook) {
        super(context);
        this.isLocalBook = isLocalBook;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_recommend_item;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final Recommend.RecommendBooks item) {
        String latelyUpdate = "";
        if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.updated))) {
            latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.updated) + ":";
        }

        holder.setText(R.id.tvRecommendTitle, item.title)
                .setText(R.id.tvLatelyUpdate, latelyUpdate)
                .setText(R.id.tvRecommendShort, item.lastChapter)
                .setVisible(R.id.ivTopLabel, item.isTop)
                .setVisible(R.id.ckBoxSelect, item.showCheckBox)
                .setVisible(R.id.ivUnReadDot, FormatUtils.formatZhuiShuDateString(item.updated)
                        .compareTo(item.recentReadingTime) > 0);

        if (item.path != null && item.path.endsWith(Constant.SUFFIX_PDF)) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_pdf);
        } else if (item.path != null && item.path.endsWith(Constant.SUFFIX_EPUB)) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_epub);
        } else if (item.path != null && item.path.endsWith(Constant.SUFFIX_CHM)) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_chm);
        } else if (item.isFromSD) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_txt);
            long fileLen = FileUtils.getChapterFile(item._id, 1).length();
            if (fileLen > 10) {
                double progress = ((double) BookSettingManager.getInstance().getReadProgress(item._id)[2]) / fileLen;
                NumberFormat fmt = NumberFormat.getPercentInstance();
                fmt.setMaximumFractionDigits(2);
                holder.setText(R.id.tvRecommendShort, "当前阅读进度：" + fmt.format(progress));
            }
        } else if (!BookSettingManager.getInstance().isNoneCover()) {
            ImageView image = holder.getView(R.id.ivRecommendCover);
            ImageLoader.loadCenterCrop(mContext,Constant.IMG_BASE_URL + item.cover,image, R.drawable.cover_default);
        } else {
            holder.setImageResource(R.id.ivRecommendCover, R.drawable.cover_default);
        }

        CheckBox ckBoxSelect = holder.getView(R.id.ckBoxSelect);
        ckBoxSelect.setChecked(item.isSeleted);
        ckBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                item.isSeleted = isChecked;
            }
        });
        if(isLocalBook){
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.path.endsWith(Constant.SUFFIX_TXT)) {
                        // TXT
                        new AlertDialog.Builder(mContext)
                                .setTitle("提示")
                                .setMessage(String.format(mContext.getString(
                                        R.string.book_detail_is_joined_the_book_shelf), item.title))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 拷贝到缓存目录
                                        FileUtils.fileChannelCopy(new File(item.path),
                                                new File(FileUtils.getChapterPath(item._id, 1)));
                                        // 加入书架
                                        if (CollectionsManager.getInstance().add(item)) {
//                                    mRecyclerView.showTipViewAndDelayClose(String.format(getString(
//                                            R.string.book_detail_has_joined_the_book_shelf), item.title));
                                            ToastUtils.showToast(String.format(mContext.getString(
                                                    R.string.book_detail_has_joined_the_book_shelf), item.title));
//                                    // 通知
                                            EventManager.refreshCollectionList();
                                        } else {
//                                    mRecyclerView.showTipViewAndDelayClose("书籍已存在");
                                            ToastUtils.showToast("书籍已存在");
                                        }
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    } else if (item.path.endsWith(Constant.SUFFIX_PDF)) {
                        // PDF
//                ReadPDFActivity.start(this, books.path);
                    } else if (item.path.endsWith(Constant.SUFFIX_EPUB)) {
                        // EPub
//                ReadEPubActivity.start(this, books.path);
                    } else if (item.path.endsWith(Constant.SUFFIX_CHM)) {
                        // CHM
//                ReadCHMActivity.start(this, books.path);
                    }
                }
            });
        }else {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReadActivity.lunch(mContext,item);
                }
            });
        }
        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showLongClickDialog(holder,item);
                return true;
            }
        });

    }

    private void showLongClickDialog(final BaseViewHolder holder, final Recommend.RecommendBooks item) {
        final boolean isTop = CollectionsManager.getInstance().isTop(item._id);
        String[] items;
        DialogInterface.OnClickListener listener;
        if (item.isFromSD) {
            items = mContext.getResources().getStringArray(R.array.recommend_item_long_click_choice_local);
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //置顶、取消置顶
                            CollectionsManager.getInstance().top(item._id, !isTop);
                            break;
                        case 1:
                            //删除
                            List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                            removeList.add(item);
                            showDeleteCacheDialog(holder,removeList);
//                            CollectionsManager.getInstance().remove(item._id);
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            };
        } else {
            items = mContext.getResources().getStringArray(R.array.recommend_item_long_click_choice);
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //置顶、取消置顶
                            CollectionsManager.getInstance().top(item._id, !isTop);
                            break;
                        case 1:
                            //书籍详情
                            BookDetailActivity.lunchForNewTask(mContext,item._id);
                            break;
                        case 2:
                            //移入养肥区
                            ToastUtils.showToast("正在拼命开发中...");
                            break;
                        case 3:
                            //缓存全本
                            if (item.isFromSD) {
                                ToastUtils.showToast("本地文件不支持该选项哦");
                            } else {
                                ToastUtils.showToast("请去正文中缓存");
//                                BookReadPresentr bb=new BookReadPresentr();
//                                bb.getBookMixAToc(item._id,"chapters");
//                                DownloadBookService.post(new DownloadQueue(item._id, mChapterList, 1, mChapterList.size()));
                            }
                            break;
                        case 4:
                            //删除
                            List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                            removeList.add(item);
                            showDeleteCacheDialog(holder,removeList);
//                            CollectionsManager.getInstance().remove(item._id);
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            };
        }
        if (isTop) items[0] = mContext.getString(R.string.cancle_top);
        new AlertDialog.Builder(mContext)
                .setTitle(item.title)
                .setItems(items, listener)
                .setNegativeButton(null, null)
                .create().show();
    }

    private void showDeleteCacheDialog(final BaseViewHolder holder, final List<Recommend.RecommendBooks> removeList) {
        final boolean selected[] = {true};
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{mContext.getString(R.string.delete_local_cache)}, selected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selected[0] = isChecked;
                            }
                        })
                .setPositiveButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new AsyncTask<String, String, String>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                ToastUtils.showToast("请稍后...");
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                CollectionsManager.getInstance().removeSome(removeList, selected[0]);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                removeItem(holder.getPosition());
                                notifyDataSetChanged();
                                ToastUtils.showToast("成功移除书籍");
//                                EventManager.refreshCollectionList();

                            }
                        }.execute();

                    }
                })
                .setNegativeButton(mContext.getString(R.string.cancel), null)
                .create().show();
    }
}
