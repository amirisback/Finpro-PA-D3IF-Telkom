package org.d3ifcool.service.networks.api;

import org.d3ifcool.service.models.Mahasiswa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static org.d3ifcool.service.networks.bridge.ApiUrl.FinproUrl.PARAMETER_DELETE;
import static org.d3ifcool.service.networks.bridge.ApiUrl.FinproUrl.PARAMETER_MAHASISWA;
import static org.d3ifcool.service.networks.bridge.ApiUrl.FinproUrl.PARAMETER_UPDATE;
import static org.d3ifcool.service.networks.bridge.ApiUrl.FinproUrl.URL_MAHASISWA;

/**
 * Created by ikhsan ramadhan
 * =========================================
 * Finpro
 * Copyright (C) 3/2/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhamad Ikhsan Ramadhan
 * E-mail   : ikhsanramadhan28@gmail.com
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 */
public interface ApiInterfaceMahasiswa {

    @FormUrlEncoded
    @POST(URL_MAHASISWA)
    Call<Mahasiswa> createMahasiswa(
            @Field("mhs_nim") String mhs_nim,
            @Field("mhs_nama") String mhs_nama,
            @Field("mhs_angkatan") String angkatan,
            @Field("mhs_kontak") String mhs_kontak,
            @Field("mhs_email") String mhs_email
    );

    @FormUrlEncoded
    @POST(URL_MAHASISWA + PARAMETER_UPDATE + PARAMETER_MAHASISWA)
    Call<Mahasiswa> updateMahasiswa(
            @Path("mahasiswa") String nim_mhs,
            @Field("mhs_nama") String mhs_nama,
            @Field("mhs_angkatan") String angkatan,
            @Field("mhs_kontak") String mhs_kontak,
            @Field("mhs_email") String mhs_email
    );

    @GET(URL_MAHASISWA)
    Call<List<Mahasiswa>> getMahasiswa();

    @POST(URL_MAHASISWA + PARAMETER_DELETE + PARAMETER_MAHASISWA)
    Call<Mahasiswa> deleteMahasiswa(@Path("mahasiswa") String mhs_nim);

    @GET(URL_MAHASISWA + PARAMETER_MAHASISWA)
    Call<Mahasiswa> getMahasiswaByParameter(@Path("mahasiswa") String username_mhs);


}