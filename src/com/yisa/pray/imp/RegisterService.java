package com.yisa.pray.imp;

import com.yisa.pray.entity.OperationResult;
import com.yisa.pray.utils.UrlUtils;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Query;

public interface RegisterService {
	
	@POST(UrlUtils.GET_VERIFI_CODE)
	Call<OperationResult> getCode(@Query("phone") String phone);

}
