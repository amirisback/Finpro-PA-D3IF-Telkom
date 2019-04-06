package org.d3ifcool.dosen.activities.details;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.d3ifcool.dosen.R;
import org.d3ifcool.service.interfaces.lists.ProyekAkhirListView;
import org.d3ifcool.service.interfaces.works.JudulWorkView;
import org.d3ifcool.service.interfaces.works.MahasiswaWorkView;
import org.d3ifcool.service.interfaces.works.ProyekAkhirWorkView;
import org.d3ifcool.service.models.ProyekAkhir;
import org.d3ifcool.service.presenters.JudulPresenter;
import org.d3ifcool.service.presenters.MahasiswaPresenter;
import org.d3ifcool.service.presenters.ProyekAkhirPresenter;

import java.util.ArrayList;
import java.util.List;

import static org.d3ifcool.service.helpers.Constant.ObjectConstanta.JUDUL_STATUS_DIGUNAKAN;

public class DosenJudulPaSubdosenAccActivity extends AppCompatActivity implements ProyekAkhirListView,
        JudulWorkView, ProyekAkhirWorkView, MahasiswaWorkView {

    public static final String EXTRA_JUDUL = "extra_judul";
    public static final String EXTRA_PROYEK_AKHIR = "extra_proyek_akhir";
    private static final String PARAM_PROYEK_AKHIR_NAMA_TIM = "proyek_akhir.nama_tim";
    private static final String PARAM_PROYEK_AKHIR_JUDUL_ID = "proyek_akhir.judul_id";
    private ProyekAkhirPresenter proyekAkhirPresenter;
    private JudulPresenter judulPresenter;
    private MahasiswaPresenter mahasiswaPresenter;
    private ProgressDialog progressDialog;
    private int extraJudulId;
    private String extraNamaTim;
    private ArrayList<ProyekAkhir> arrayListProyekAkhir = new ArrayList<>();
    private ArrayList<ProyekAkhir> tempProyekAkhirArrayList = new ArrayList<>();
    private TextView textViewMhsNIM1, textViewMhsNama1, textViewMhsNIM2, textViewMhsNama2, textViewKelompok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_judul_pa_subdosen_acc);

        setTitle(getString(R.string.title_judulpa_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        proyekAkhirPresenter = new ProyekAkhirPresenter(this, this);
        judulPresenter = new JudulPresenter(this);
        mahasiswaPresenter = new MahasiswaPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.text_progress_dialog));

        FloatingActionButton floatingActionButtonAccept = findViewById(R.id.act_dsn_mhs_judul_fab_accept);
        FloatingActionButton floatingActionButtonDecline = findViewById(R.id.act_dsn_mhs_judul_fab_decline);
        FloatingActionButton floatingActionButtonConversation = findViewById(R.id.act_dsn_mhs_judul_fab_conversation);

        textViewKelompok = findViewById(R.id.act_dsn_mhs_judul_kelompok);
        textViewMhsNIM1 = findViewById(R.id.act_dsn_mhs_judul_nim_1);
        textViewMhsNama1 = findViewById(R.id.act_dsn_mhs_judul_nama_1);
        textViewMhsNIM2 = findViewById(R.id.act_dsn_mhs_judul_nim_2);
        textViewMhsNama2 = findViewById(R.id.act_dsn_mhs_judul_nama_2);

        extraJudulId = getIntent().getIntExtra(EXTRA_JUDUL, 0);
        ProyekAkhir extraProyekAkhir = getIntent().getParcelableExtra(EXTRA_PROYEK_AKHIR);
        extraNamaTim = extraProyekAkhir.getNama_tim();

        textViewKelompok.setText(extraNamaTim);
        // search proyek_akhir berdasarkan judul_id
        proyekAkhirPresenter.searchProyekAkhir(PARAM_PROYEK_AKHIR_JUDUL_ID, String.valueOf(extraJudulId));

        floatingActionButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < arrayListProyekAkhir.size(); i++) {
                    proyekAkhirPresenter.deleteProyekAkhir(arrayListProyekAkhir.get(i).getProyek_akhir_id());
                    mahasiswaPresenter.updateMahasiswaJudul(arrayListProyekAkhir.get(i).getMhs_nim(), 0);
                }
                proyekAkhirPresenter.createProyekAkhir(extraJudulId, tempProyekAkhirArrayList.get(0).getMhs_nim(), extraNamaTim);
                mahasiswaPresenter.updateMahasiswaJudul(tempProyekAkhirArrayList.get(0).getMhs_nim(), extraJudulId);
                if (tempProyekAkhirArrayList.size() == 2){
                    proyekAkhirPresenter.createProyekAkhir(extraJudulId, tempProyekAkhirArrayList.get(1).getMhs_nim(), extraNamaTim);
                    mahasiswaPresenter.updateMahasiswaJudul(tempProyekAkhirArrayList.get(0).getMhs_nim(), extraJudulId);
                }
                judulPresenter.updateStatusJudul(extraJudulId, JUDUL_STATUS_DIGUNAKAN);

            }
        });

        floatingActionButtonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proyekAkhirPresenter.deleteProyekAkhir(tempProyekAkhirArrayList.get(0).getProyek_akhir_id());
                mahasiswaPresenter.updateMahasiswaJudul(tempProyekAkhirArrayList.get(0).getMhs_nim(), 0);
                if (tempProyekAkhirArrayList.size() == 2) {
                    proyekAkhirPresenter.deleteProyekAkhir(tempProyekAkhirArrayList.get(tempProyekAkhirArrayList.size()-1).getProyek_akhir_id());
                    mahasiswaPresenter.updateMahasiswaJudul(tempProyekAkhirArrayList.get(1).getMhs_nim(), 0);
                }
                finish();
            }
        });

        floatingActionButtonConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onGetListProyekAkhir(List<ProyekAkhir> proyekAkhirList) {
        arrayListProyekAkhir.clear();
        arrayListProyekAkhir.addAll(proyekAkhirList);

        for (int i = 0; i < arrayListProyekAkhir.size(); i++){
            if (arrayListProyekAkhir.get(i).getNama_tim().equalsIgnoreCase(extraNamaTim)){
                // jika proyek_akhir getNamaTim = session
                tempProyekAkhirArrayList.add(arrayListProyekAkhir.get(i));
            }
        }

        textViewMhsNIM1.setText(tempProyekAkhirArrayList.get(0).getMhs_nim());
        textViewMhsNama1.setText(tempProyekAkhirArrayList.get(0).getMhs_nama());

        if (tempProyekAkhirArrayList.size() == 2) {
            textViewMhsNIM2.setText(tempProyekAkhirArrayList.get(tempProyekAkhirArrayList.size()-1).getMhs_nim());
            textViewMhsNama2.setText(tempProyekAkhirArrayList.get(tempProyekAkhirArrayList.size()-1).getMhs_nama());
        }
    }

    @Override
    public void onSucces() {
    }

    @Override
    public void onSuccesWorkJudul() {
        finish();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
