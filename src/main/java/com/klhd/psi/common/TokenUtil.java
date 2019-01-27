package com.klhd.psi.common;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    private static final byte[] SECRET = "pOJgKWbNRiMQLalqNvHdPNbsYUN".getBytes();

    //生成一个token
    public static String creatToken(Map<String, Object> payloadMap) throws JOSEException {
        /**
         * JWSAlgorithm类里面有所有的加密算法法则，直接调用。
         */
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        //建立一个载荷Payload
        Payload payload = new Payload(new JSONObject(payloadMap));
        //将头部和载荷结合在一起
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //建立一个密匙
        JWSSigner jwsSigner = new MACSigner(SECRET);
        //签名
        jwsObject.sign(jwsSigner);
        //生成token
        return jwsObject.serialize();
    }

    public static Map<String, Object> valid(String token) throws Exception {
        //解析token
        JWSObject jwsObject = JWSObject.parse(token);
        //获取到载荷
        Payload payload = jwsObject.getPayload();
        //建立一个解锁密匙
        JWSVerifier jwsVerifier = new MACVerifier(SECRET);
        Map<String, Object> resultMap = new HashMap<>();
        //判断token
        if (jwsObject.verify(jwsVerifier)) {
            resultMap.put("success", true);
            //载荷的数据解析成json对象。
            JSONObject jsonObject = payload.toJSONObject();
            resultMap.put("payload", jsonObject);

            //判断token是否过期
            if (jsonObject.containsKey("exp")) {
                Long expTime = Long.valueOf(jsonObject.get("exp").toString());
                Long nowTime = new Date().getTime();
                resultMap.put("expired", false);
                //判断是否过期
                if (nowTime > expTime) {
                    //已经过期
                    resultMap.put("expired", true);
                }
            }
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
