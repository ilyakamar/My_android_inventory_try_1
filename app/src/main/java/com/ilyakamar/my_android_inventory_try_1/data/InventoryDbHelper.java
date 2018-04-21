package com.ilyakamar.my_android_inventory_try_1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class InventoryDbHelper extends SQLiteOpenHelper { // START InventoryDbHelper

    private final static String DB_NAME = "inventory.db";
    private final static int DB_VERSION = 1;
    public final static String LOG_TAG = InventoryDbHelper.class.getCanonicalName();

    public InventoryDbHelper(Context context) {// constructor
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// onCreate
        db.execSQL(StockContract.StockEntry.CREATE_TABLE_STOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// onUpgrade

    }

    public void insertItem(StockItem item) { // insertItem
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.COLUMN_NAME, item.getProductName());
        values.put(StockContract.StockEntry.COLUMN_PRICE, item.getPrice());
        values.put(StockContract.StockEntry.COLUMN_QUANTITY, item.getQuantity());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_NAME, item.getSupplierName());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_PHONE, item.getSupplierPhone());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_EMAIL, item.getSupplierEmail());
        values.put(StockContract.StockEntry.COLUMN_IMAGE, item.getImage());
        long id = db.insert(StockContract.StockEntry.TABLE_NAME, null, values);
    }// end insertItem

    public Cursor readStock() {// readStock (f)
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                StockContract.StockEntry._ID,
                StockContract.StockEntry.COLUMN_NAME,
                StockContract.StockEntry.COLUMN_PRICE,
                StockContract.StockEntry.COLUMN_QUANTITY,
                StockContract.StockEntry.COLUMN_SUPPLIER_NAME,
                StockContract.StockEntry.COLUMN_SUPPLIER_PHONE,
                StockContract.StockEntry.COLUMN_SUPPLIER_EMAIL,
                StockContract.StockEntry.COLUMN_IMAGE
        };

        Cursor cursor = db.query(
                StockContract.StockEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readItem(long itemId) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                StockContract.StockEntry._ID,
                StockContract.StockEntry.COLUMN_NAME,
                StockContract.StockEntry.COLUMN_PRICE,
                StockContract.StockEntry.COLUMN_QUANTITY,
                StockContract.StockEntry.COLUMN_SUPPLIER_NAME,
                StockContract.StockEntry.COLUMN_SUPPLIER_PHONE,
                StockContract.StockEntry.COLUMN_SUPPLIER_EMAIL,
                StockContract.StockEntry.COLUMN_IMAGE
        };

        String selection = StockContract.StockEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(itemId)};

        Cursor cursor = db.query(
                StockContract.StockEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public void updateItem(long currentItemId, int quantity) {// updateItem (f)
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.COLUMN_QUANTITY, quantity);
        String selection = StockContract.StockEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(currentItemId)};

        db.update(StockContract.StockEntry.TABLE_NAME,
                values, selection, selectionArgs);
    }// end updateItem

    public void sellOneItem(long itemId, int quantity) {// sellOneItem (f)
        SQLiteDatabase db = getWritableDatabase();
        int newQuantity = 0;
        if (quantity > 0) {
            newQuantity = quantity - 1;
        }
        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.COLUMN_QUANTITY, newQuantity);
        String selection = StockContract.StockEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(itemId)};
        db.update(StockContract.StockEntry.TABLE_NAME,
                values, selection, selectionArgs);
    }// end sellOneItem
}// END InventoryDbHelper

