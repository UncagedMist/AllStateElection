package tbc.uncagedmist.allstateelection.Remote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tbc.uncagedmist.allstateelection.Model.Election;
import tbc.uncagedmist.allstateelection.Model.Item;

public interface IMyAPI {

    @FormUrlEncoded
    @POST("getElection.php")
    Observable<List<Election>> getElection(
            @Field("status") String status
    );



    @FormUrlEncoded
    @POST("getItems.php")
    Observable<List<Item>> getItem(
            @Field("catId") String catId
    );

    @FormUrlEncoded
    @POST("insertToken.php")
    Call<String> insertToken(
            @Field("deviceId")String deviceId,
            @Field("token")String token
    );
}
