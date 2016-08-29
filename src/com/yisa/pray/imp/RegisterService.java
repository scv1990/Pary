package com.yisa.pray.imp;

import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.OperationResult;
import com.yisa.pray.utils.UrlUtils;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RegisterService {
	/**
	 * @Title: getCode 
	 * @Description: TODO(获取验证码) 
	 * @param @param phone  手机号码
	 * @param @return    设定文件 
	 * @return Call<OperationResult>    返回类型 
	 * @throws
	 */
	@POST(UrlUtils.GET_VERIFI_CODE)
	Call<OperationResult> getCode(@Query("phone") String phone);
	
	/**
	 * 注册
	 * @param phone  电话
	 * @param userName  用户名
	 * @param password 密码
	 * @param code  邀请码
	 * @return
	 */
	@POST(UrlUtils.REGISTER)
	Call<ErrorMessage> register(@Query("phone") String phone,
								@Query("username") String userName,
								@Query("code") String verfiCode,
								@Query("password") String password,
								@Query("invitation_code") String inviteCode
								);
}
