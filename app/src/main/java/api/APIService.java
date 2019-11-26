package api;

import models.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded

    @POST("register")
    Call<Result> createUser(
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("pin") String pin

    );

    @GET("display_lawyers")
    Call<String> getString();

}