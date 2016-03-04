package com.example.lprub.proyectorest.interfaz;

import com.example.lprub.proyectorest.clases.Profesor;
import com.example.lprub.proyectorest.clases.Actividad;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ApiActividades {
    @GET("restful/api/actividad/ruben")
    Call<List<Actividad>> getActividades();

    @GET("restful/api/profesor")
    Call<List<Profesor>> getProfesores();

    @POST("restful/api/actividad")
    Call<Actividad> createActividad(@Body Actividad actividad);

    @PUT("restful/api/actividad")
    Call<Actividad> editaActividad(@Body Actividad actividad);

    @DELETE("restful/api/actividad/{id}")
    Call<Actividad> deleteActividad(@Path("id") String id);
}
