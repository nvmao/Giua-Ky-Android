package com.group.foodmanagement.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.InvoiceDetail;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailRepository extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "food_management";
    private static final String TABLE = "invoice_details";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_INVOICE_ID = "invoice_id";
    private static final String KEY_COUNT = "count";

    public InvoiceDetailRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PRODUCT_ID + " INTEGER,"
                + KEY_INVOICE_ID + " INTEGER,"
                + KEY_COUNT + " INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addInvoiceDetail(InvoiceDetail invoiceDetail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, invoiceDetail.getProductId());
        values.put(KEY_INVOICE_ID, invoiceDetail.getInvoiceId());
        values.put(KEY_COUNT, invoiceDetail.getCount());

        db.insert(TABLE, null, values);

        // add detail

        db.close();
    }

    // code to get the single contact
//    public InvoiceDetail getInvoice(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE, new String[] { KEY_ID,
//                        KEY_PRODUCT_ID, KEY_COUNT }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Invoice product = new Invoice(
//                Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1),
//                cursor.getString(2)
//        );
//
//        // get detail here by invoice
//        return product;
//    }

    // code to get all contacts in a list view
    public List<InvoiceDetail> getInvoicesDetail(int invoiceId) {

        List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(Integer.parseInt(cursor.getString(2)) == invoiceId){
                    InvoiceDetail invoiceDetail = new InvoiceDetail();
                    invoiceDetail.setId(Integer.parseInt(cursor.getString(0)));
                    invoiceDetail.setProductId(Integer.parseInt(cursor.getString(1)));
                    invoiceDetail.setInvoiceId(Integer.parseInt(cursor.getString(2)));
                    invoiceDetail.setCount(Integer.parseInt(cursor.getString(3)));

                    // get product

                    // Adding contact to list
                    invoiceDetails.add(invoiceDetail);
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return invoiceDetails;
    }

//    // code to update the single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }



}
