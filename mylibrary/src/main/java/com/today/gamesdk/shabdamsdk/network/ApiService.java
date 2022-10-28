package com.today.gamesdk.shabdamsdk.network;

import com.today.gamesdk.shabdamsdk.model.GetWordRequest;
import com.today.gamesdk.shabdamsdk.model.SignupRequest;
import com.today.gamesdk.shabdamsdk.model.adduser.AddUserRequest;
import com.today.gamesdk.shabdamsdk.model.adduser.AddUserResponse;
import com.today.gamesdk.shabdamsdk.model.deleteAccount.DeleteAccountRequest;
import com.today.gamesdk.shabdamsdk.model.deleteAccount.DeleteAccountResponse;
import com.today.gamesdk.shabdamsdk.model.dictionary.CheckWordDicRequest;
import com.today.gamesdk.shabdamsdk.model.dictionary.CheckWordDicResponse;
import com.today.gamesdk.shabdamsdk.model.game_user_update_token.UpdateUserTokenRequest;
import com.today.gamesdk.shabdamsdk.model.game_user_update_token.UpdateUserTokenResponse;
import com.today.gamesdk.shabdamsdk.model.gamesubmit.GameSubmitResponse;
import com.today.gamesdk.shabdamsdk.model.gamesubmit.SubmitGameRequest;
import com.today.gamesdk.shabdamsdk.model.getwordresp.GetWordResponse;
import com.today.gamesdk.shabdamsdk.model.leaderboard.GetLeaderboardList;
import com.today.gamesdk.shabdamsdk.model.leaderboard.GetLeaderboardRequest;
import com.today.gamesdk.shabdamsdk.model.statistics.StatisticsMainModel;
import com.today.gamesdk.shabdamsdk.model.word.WordResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("get_words")
    Observable<GetWordResponse> fetchNewWord(@Body GetWordRequest body);

   /* @POST("get_words")
    Observable<GetWordResponse> fetchEnglisNewWord(@Url String url);*/

    @POST("get_leaderboard")
    Observable<GetLeaderboardList> getLeaderBoardAPIList(@Body GetLeaderboardRequest body);

    @POST("get_streak")
    Observable<StatisticsMainModel> getStreakData(@Body GetLeaderboardRequest body);

    @POST("add_gameuser")
    Observable<AddUserResponse> addUser(@Body AddUserRequest body);

    @POST("check_words")
    Observable<CheckWordDicResponse> checkWord(@Body CheckWordDicRequest body);

    @POST("game_submit")
    Observable<GameSubmitResponse> submitGame(@Body SubmitGameRequest body);

    @POST("signup_gameuser")
    Observable<AddUserResponse> signUpUser(@Body SignupRequest body);

    @POST("get_gamewordsid")
    Observable<WordResponse> fetchWordFromWordId(@Body CheckWordDicRequest body);

    @POST("gameuser_update_token")
    Observable<UpdateUserTokenResponse> updateUserToken(@Body UpdateUserTokenRequest body);

    @POST("delete_shabdam_user")
    Observable<DeleteAccountResponse> deleteAccount(@Body DeleteAccountRequest body);

    /*@GET
    Observable<GetWordResponse> fetchEnglisNewWord(@Url String url);*/



}
