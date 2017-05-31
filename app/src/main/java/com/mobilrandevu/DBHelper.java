package com.mobilrandevu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobilrandevu.Model.AkademisyenPOJO;
import com.mobilrandevu.Model.OgrenciPOJO;
import com.mobilrandevu.Model.RandevuPOJO;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MobilRandevu";
    private static final String TABLE_AKADEMİSYEN = "Akademisyen";
    private static final String TABLE_OGRENCİ = "Ogrenci";
    private static final String TABLE_RANDEVU = "Randevu";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlAkademisyen = "CREATE TABLE " + TABLE_AKADEMİSYEN + "(ID INTEGER PRIMARY KEY,Email TEXT,Adi TEXT,Parola TEXT)";
        db.execSQL(sqlAkademisyen);

        String sqlOgrenci = "CREATE TABLE " + TABLE_OGRENCİ + "(ID INTEGER PRIMARY KEY,OgrenciNumarasi TEXT,Adi TEXT,Parola TEXT)";
        db.execSQL(sqlOgrenci);

        String sqlRandevu = "CREATE TABLE " + TABLE_RANDEVU + "(ID INTEGER PRIMARY KEY,Tarih TEXT,Saat TEXT,OgrenciID INTEGER,AkademisyenID INTEGER)";
        db.execSQL(sqlRandevu);
    }

    //region AKADEMİSYEN İŞLEMLERİ
    public List<AkademisyenPOJO> AkademisyenleriGetir() {
        List<AkademisyenPOJO> akademisyenler = new ArrayList<AkademisyenPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_AKADEMİSYEN;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            AkademisyenPOJO akademisyen = new AkademisyenPOJO();
            akademisyen.setId(cursor.getInt(0));
            akademisyen.setEmail(cursor.getString(1));
            akademisyen.setAdi(cursor.getString(2));
            akademisyen.setParola(cursor.getString(3));
            akademisyenler.add(akademisyen);
        }
        cursor.close();
        db.close();
        return akademisyenler;
    }

    public List<AkademisyenPOJO> AkademisyenGetir(String email, String parola) {
        List<AkademisyenPOJO> akademisyenler = new ArrayList<AkademisyenPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_AKADEMİSYEN + " WHERE Email='" + email + "' and Parola='" + parola + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            AkademisyenPOJO akademisyen = new AkademisyenPOJO();
            akademisyen.setId(cursor.getInt(0));
            akademisyen.setEmail(cursor.getString(1));
            akademisyen.setAdi(cursor.getString(2));
            akademisyen.setParola(cursor.getString(3));
            akademisyenler.add(akademisyen);
        }
        cursor.close();
        db.close();
        return akademisyenler;
    }

    public List<AkademisyenPOJO> AkademisyenGetirByID(int id) {
        List<AkademisyenPOJO> akademisyenler = new ArrayList<AkademisyenPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_AKADEMİSYEN + " WHERE ID=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            AkademisyenPOJO akademisyen = new AkademisyenPOJO();
            akademisyen.setId(cursor.getInt(0));
            akademisyen.setEmail(cursor.getString(1));
            akademisyen.setAdi(cursor.getString(2));
            akademisyen.setParola(cursor.getString(3));
            akademisyenler.add(akademisyen);
        }
        cursor.close();
        db.close();
        return akademisyenler;
    }

    public void AkademisyenEkle(AkademisyenPOJO akademisyenPOJO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", akademisyenPOJO.getEmail());
        values.put("Adi", akademisyenPOJO.getAdi());
        values.put("Parola", akademisyenPOJO.getParola());
        db.insert(TABLE_AKADEMİSYEN, null, values);
        db.close();
    }

    public void AkademisyenGuncelle(String id, String email, String adi, String parola) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("Adi", adi);
        values.put("Parola", parola);
        db.update(TABLE_AKADEMİSYEN, values, "ID=" + id, null);
        db.close();
    }

    public void AkademisyenSil(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AKADEMİSYEN, "ID=" + id, null);
        db.close();
    }
//endregion

    //region OGRENCİ İŞLEMLERİ
    public List<OgrenciPOJO> OgrencileriGetir() {
        List<OgrenciPOJO> ogrenciler = new ArrayList<OgrenciPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_OGRENCİ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            OgrenciPOJO ogrenci = new OgrenciPOJO();
            ogrenci.setId(cursor.getInt(0));
            ogrenci.setOgrenciNumarasi(cursor.getString(1));
            ogrenci.setAdi(cursor.getString(2));
            ogrenci.setParola(cursor.getString(3));
            ogrenciler.add(ogrenci);
        }
        cursor.close();
        db.close();
        return ogrenciler;
    }

    public List<OgrenciPOJO> OgrenciGetir(String ogrenciNumarasi, String parola) {
        List<OgrenciPOJO> ogrenciler = new ArrayList<OgrenciPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_OGRENCİ + " WHERE OgrenciNumarasi='" + ogrenciNumarasi + "' and Parola='" + parola + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            OgrenciPOJO ogrenci = new OgrenciPOJO();
            ogrenci.setId(cursor.getInt(0));
            ogrenci.setOgrenciNumarasi(cursor.getString(1));
            ogrenci.setAdi(cursor.getString(2));
            ogrenci.setParola(cursor.getString(3));
            ogrenciler.add(ogrenci);
        }
        cursor.close();
        db.close();
        return ogrenciler;
    }

    public List<OgrenciPOJO> OgrenciGetirByID(int id) {
        List<OgrenciPOJO> ogrenciler = new ArrayList<OgrenciPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_OGRENCİ + " WHERE ID=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            OgrenciPOJO ogrenci = new OgrenciPOJO();
            ogrenci.setId(cursor.getInt(0));
            ogrenci.setOgrenciNumarasi(cursor.getString(1));
            ogrenci.setAdi(cursor.getString(2));
            ogrenci.setParola(cursor.getString(3));
            ogrenciler.add(ogrenci);
        }
        cursor.close();
        db.close();
        return ogrenciler;
    }

    public void OgrenciEkle(OgrenciPOJO ogrenciPOJO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OgrenciNumarasi", ogrenciPOJO.getOgrenciNumarasi());
        values.put("Adi", ogrenciPOJO.getAdi());
        values.put("Parola", ogrenciPOJO.getParola());
        db.insert(TABLE_OGRENCİ, null, values);
        db.close();
    }

    public void OgrenciGuncelle(String id, String ogrenciNumarasi, String adi, String parola) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OgrenciNumarasi", ogrenciNumarasi);
        values.put("Adi", adi);
        values.put("Parola", parola);
        db.update(TABLE_OGRENCİ, values, "ID=" + id, null);
        db.close();
    }

    public void OgrenciSil(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OGRENCİ, "ID=" + id, null);
        db.close();
    }
    //endregion ÖĞRENCİ İŞLEMLERİ

    //region RANDEVU İŞLEMLERİ
    public List<RandevuPOJO> RandevulariGetir() {
        List<RandevuPOJO> randevular = new ArrayList<RandevuPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RANDEVU;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            RandevuPOJO randevu = new RandevuPOJO();
            randevu.setId(cursor.getInt(0));
            randevu.setTarih(cursor.getString(1));
            randevu.setSaat(cursor.getString(2));
            randevu.setOgrenciID(cursor.getInt(3));
            randevu.setAkademisyenID(cursor.getInt(4));
            randevular.add(randevu);
        }
        cursor.close();
        db.close();
        return randevular;
    }

    public List<RandevuPOJO> RandevuGetir(int id) {
        List<RandevuPOJO> randevular = new ArrayList<RandevuPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RANDEVU + " WHERE ID=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            RandevuPOJO randevu = new RandevuPOJO();
            randevu.setId(cursor.getInt(0));
            randevu.setTarih(cursor.getString(1));
            randevu.setSaat(cursor.getString(2));
            randevu.setOgrenciID(cursor.getInt(3));
            randevu.setAkademisyenID(cursor.getInt(4));
            randevular.add(randevu);
        }
        cursor.close();
        db.close();
        return randevular;
    }

    public List<RandevuPOJO> RandevulariGetirByOgrenciID(String tarih, int ogrenciID) {
        List<RandevuPOJO> randevular = new ArrayList<RandevuPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RANDEVU + " WHERE Tarih='" + tarih + "' and OgrenciID=" + ogrenciID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            RandevuPOJO randevu = new RandevuPOJO();
            randevu.setId(cursor.getInt(0));
            randevu.setTarih(cursor.getString(1));
            randevu.setSaat(cursor.getString(2));
            randevu.setOgrenciID(cursor.getInt(3));
            randevu.setAkademisyenID(cursor.getInt(4));
            randevular.add(randevu);
        }
        cursor.close();
        db.close();
        return randevular;
    }

    public List<RandevuPOJO> RandevulariGetirByTip(int id) {
        List<RandevuPOJO> randevular = new ArrayList<RandevuPOJO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "Select * From " + TABLE_RANDEVU;
        if (Statikler.tip == 0)
            selectQuery = "SELECT * FROM " + TABLE_RANDEVU + " WHERE OgrenciID=" + id;
        else if (Statikler.tip == 1)
            selectQuery = "SELECT * FROM " + TABLE_RANDEVU + " WHERE AkademisyenID=" + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            RandevuPOJO randevu = new RandevuPOJO();
            randevu.setId(cursor.getInt(0));
            randevu.setTarih(cursor.getString(1));
            randevu.setSaat(cursor.getString(2));
            randevu.setOgrenciID(cursor.getInt(3));
            randevu.setAkademisyenID(cursor.getInt(4));
            randevular.add(randevu);
        }
        cursor.close();
        db.close();
        return randevular;
    }

    public void RandevuEkle(RandevuPOJO randevuPOJO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tarih", randevuPOJO.getTarih());
        values.put("Saat", randevuPOJO.getSaat());
        values.put("OgrenciID", randevuPOJO.getOgrenciID());
        values.put("AkademisyenID", randevuPOJO.getAkademisyenID());
        db.insert(TABLE_RANDEVU, null, values);
        db.close();
    }

    public void RandevuGuncelle(String id, String tarih, String saat, int ogrenciID, int akademisyenID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tarih", tarih);
        values.put("Saat", saat);
        values.put("OgrenciID", ogrenciID);
        values.put("AkademisyenID", akademisyenID);
        db.update(TABLE_RANDEVU, values, "ID=" + id, null);
        db.close();
    }

    public void RandevuSil(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RANDEVU, "ID=" + id, null);
        db.close();
    }

    //endregion RANDEVU İŞLEMLERİ

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_AKADEMİSYEN);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_OGRENCİ);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_RANDEVU);
        onCreate(db);
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);
        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);
            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});
            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {
                alc.set(0, c);
                c.moveToFirst();
                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }
}
