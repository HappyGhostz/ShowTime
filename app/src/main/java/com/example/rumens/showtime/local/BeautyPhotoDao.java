package com.example.rumens.showtime.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BEAUTY_PHOTO".
*/
public class BeautyPhotoDao extends AbstractDao<BeautyPhoto, String> {

    public static final String TABLENAME = "BEAUTY_PHOTO";

    /**
     * Properties of entity BeautyPhoto.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Imgsrc = new Property(0, String.class, "imgsrc", true, "IMGSRC");
        public final static Property Pixel = new Property(1, String.class, "pixel", false, "PIXEL");
        public final static Property Docid = new Property(2, String.class, "docid", false, "DOCID");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property IsLove = new Property(4, boolean.class, "isLove", false, "IS_LOVE");
        public final static Property IsPraise = new Property(5, boolean.class, "isPraise", false, "IS_PRAISE");
        public final static Property IsDownload = new Property(6, boolean.class, "isDownload", false, "IS_DOWNLOAD");
    }


    public BeautyPhotoDao(DaoConfig config) {
        super(config);
    }
    
    public BeautyPhotoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BEAUTY_PHOTO\" (" + //
                "\"IMGSRC\" TEXT PRIMARY KEY NOT NULL ," + // 0: imgsrc
                "\"PIXEL\" TEXT," + // 1: pixel
                "\"DOCID\" TEXT," + // 2: docid
                "\"TITLE\" TEXT," + // 3: title
                "\"IS_LOVE\" INTEGER NOT NULL ," + // 4: isLove
                "\"IS_PRAISE\" INTEGER NOT NULL ," + // 5: isPraise
                "\"IS_DOWNLOAD\" INTEGER NOT NULL );"); // 6: isDownload
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BEAUTY_PHOTO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BeautyPhoto entity) {
        stmt.clearBindings();
 
        String imgsrc = entity.getImgsrc();
        if (imgsrc != null) {
            stmt.bindString(1, imgsrc);
        }
 
        String pixel = entity.getPixel();
        if (pixel != null) {
            stmt.bindString(2, pixel);
        }
 
        String docid = entity.getDocid();
        if (docid != null) {
            stmt.bindString(3, docid);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
        stmt.bindLong(5, entity.getIsLove() ? 1L: 0L);
        stmt.bindLong(6, entity.getIsPraise() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsDownload() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BeautyPhoto entity) {
        stmt.clearBindings();
 
        String imgsrc = entity.getImgsrc();
        if (imgsrc != null) {
            stmt.bindString(1, imgsrc);
        }
 
        String pixel = entity.getPixel();
        if (pixel != null) {
            stmt.bindString(2, pixel);
        }
 
        String docid = entity.getDocid();
        if (docid != null) {
            stmt.bindString(3, docid);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
        stmt.bindLong(5, entity.getIsLove() ? 1L: 0L);
        stmt.bindLong(6, entity.getIsPraise() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsDownload() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BeautyPhoto readEntity(Cursor cursor, int offset) {
        BeautyPhoto entity = new BeautyPhoto( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // imgsrc
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // pixel
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // docid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
            cursor.getShort(offset + 4) != 0, // isLove
            cursor.getShort(offset + 5) != 0, // isPraise
            cursor.getShort(offset + 6) != 0 // isDownload
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BeautyPhoto entity, int offset) {
        entity.setImgsrc(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPixel(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDocid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsLove(cursor.getShort(offset + 4) != 0);
        entity.setIsPraise(cursor.getShort(offset + 5) != 0);
        entity.setIsDownload(cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(BeautyPhoto entity, long rowId) {
        return entity.getImgsrc();
    }
    
    @Override
    public String getKey(BeautyPhoto entity) {
        if(entity != null) {
            return entity.getImgsrc();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BeautyPhoto entity) {
        return entity.getImgsrc() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
