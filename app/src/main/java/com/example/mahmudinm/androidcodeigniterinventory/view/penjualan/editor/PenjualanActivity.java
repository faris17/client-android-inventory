package com.example.mahmudinm.androidcodeigniterinventory.view.penjualan.editor;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmudinm.androidcodeigniterinventory.R;
import com.example.mahmudinm.androidcodeigniterinventory.model.Barang;
import com.example.mahmudinm.androidcodeigniterinventory.network.response.BarangResponse;
import com.example.mahmudinm.androidcodeigniterinventory.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class PenjualanActivity extends AppCompatActivity implements PenjualanView{

    PenjualanPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;
    Context mContext;

    String barang_id, nama, harga, jumlah_harga, jumlah_barang;

    @BindView(R.id.nama)
    Spinner s_nama;
    @BindView(R.id.jumlah_barang)
    EditText et_jumlah_barang;
    @BindView(R.id.jumlah_harga)
    EditText et_jumlah_harga;
    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;
    @BindView(R.id.content_update)
    LinearLayout content_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);
        ButterKnife.bind(this);

        session = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new PenjualanPresenter(this);

        presenter.getListBarang(session.getKeyToken());

        s_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nama = ((TextView) view.findViewById(R.id.nama)).getText().toString();
                harga = ((TextView) view.findViewById(R.id.harga)).getText().toString();
                barang_id = ((TextView) view.findViewById(R.id.barang_id)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnTextChanged(R.id.jumlah_barang) void change_jumlah_barang() {
        // Cek jika value nya di kosongkan
        String s_jumlah_barang;
        if (et_jumlah_barang.getText().toString().isEmpty()) {
            s_jumlah_barang = "1" ;
        } else {
            s_jumlah_barang = et_jumlah_barang.getText().toString();
        }

        int total = Integer.parseInt(s_jumlah_barang) * Integer.parseInt(harga);
        et_jumlah_harga.setText(String.valueOf(total));
    }

    @OnClick(R.id.simpan) void simpan() {
        Toast.makeText(this, nama + harga + barang_id, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void statusSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListBarang(BarangResponse barangResponse) {
        SpinnerBarangAdapter adapter = new SpinnerBarangAdapter(this, R.layout.spinner_barang,
                barangResponse.getData());
        s_nama.setAdapter(adapter);
    }


}