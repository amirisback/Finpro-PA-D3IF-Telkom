package org.d3ifcool.dosen.activities.editors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.d3ifcool.dosen.R;
import org.d3ifcool.service.interfaces.works.BimbinganWorkView;
import org.d3ifcool.service.presenters.BimbinganPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DosenBimbinganTambahActivity extends AppCompatActivity implements BimbinganWorkView {
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    TextView tv_tanggal;
    String date;

    private BimbinganPresenter presenter;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_bimbingan_tambah);

        setTitle(getString(R.string.title_bimbingan_tambah));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.text_progress_dialog));
        presenter = new BimbinganPresenter(this);

        tv_tanggal = findViewById(R.id.act_dsn_mhs_bimbingan_textview_tanggal);
        EditText et_judul = findViewById(R.id.act_dsn_info_edittext_judul_bimbingan);
        EditText et_review = findViewById(R.id.act_dsn_info_edittext_konten);
        Button btn_simpan = findViewById(R.id.act_dsn_info_button_simpan);

        setDate();

        String judul = et_judul.getText().toString();
        String review = et_review.getText().toString();
        String tanggal = tv_tanggal.getText().toString();

        if (judul.isEmpty()) {
            et_judul.setError("Judul Harus di Isi");
        }else if (review.isEmpty()){
            et_review.setError("Review Harus di Isi");
        }else{
            presenter.createBimbingan(review,judul,tanggal,1);
        }
    }

    private void setDate(){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        month = month + 1;
        date = checkDigit(day)+ " - "+ checkDigit(month)+ " - " + year ;
        tv_tanggal.setText(date);
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = checkDigit(dayOfMonth)+ " - "+ checkDigit(month)+ " - " + year ;
                tv_tanggal.setText(date);
            }
        };

        tv_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DosenBimbinganTambahActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
                        datePickerDialog,year,month,day);
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
    }


    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void onSucces() {
        finish();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
