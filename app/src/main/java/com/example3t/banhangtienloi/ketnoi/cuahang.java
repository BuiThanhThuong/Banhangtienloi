package com.example3t.banhangtienloi.ketnoi;

import com.example3t.banhangtienloi.model.Donhangmd;
import com.example3t.banhangtienloi.model.mathangmd;
import com.example3t.banhangtienloi.model.usermd;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface cuahang {
    @GET("themsanpham.php")
    Observable<mathangmd> getThemsanpham();
    @POST("user.php")
    @FormUrlEncoded
    Observable<usermd>user(
            @Field("SDT") String SDT,
            @Field("matkhau") String matkhau,
            @Field("tenuser") String tenuser,
            @Field("gmail") String gmail
    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<usermd>dangnhap(
            @Field("SDT") String SDT,
            @Field("matkhau") String matkhau

    );


    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<mathangmd> seach(
            @Field("seach") String seach
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<usermd> donhang(
                    @Field("sdt") String sdt,
                    @Field("email") String email,
                    @Field("tongtien") String tongtien,
                    @Field("iduser") Integer iduser,
                    @Field("tenuser") String tenuser,
                    @Field("diachi") String diachi,
                    @Field("soluong") Integer soluong,
                    @Field("chitiet") String chitiet
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<Donhangmd> xemdonhang(
            @Field("iduser") int iduser
    );
    
}
