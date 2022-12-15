package com.test.security.server;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import com.test.security.constant.CacheConstants;
import com.test.security.constant.Constants;
import com.test.security.entity.LoginUser;
import com.test.security.util.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author jin
 * @Date 2022/12/12 15:41
 * @Description TODO
 */
@Service
public class TokenServer {

    @Value("${token.secret}")
    private String secret;
    @Value("${token.header}")
    private String header;
    @Value("${token.expireTime}")
    private Integer expireTime;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * 创建token
     *
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        //不分离 session 和 cookie
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        response.addCookie(new Cookie("token", token));
        return token;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser
     */
    private void refreshToken(LoginUser loginUser) {
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }


    /**
     * 获取用户身份信息
     *
     * @param request
     * @return
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        //获取token
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = redisCache.getCacheObject(userKey);
                return user;
            } catch (Exception e) {

            }
        }
        return null;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token
     * @return
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);//header中获取token
        if (StrUtil.isEmpty(token)) {
            token = getCookieToken();//cookie中获取token
        }
        return token;
    }

    /**
     * 从cookie中获取token
     *
     * @return
     */
    private String getCookieToken() {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c != null && Constants.TOKEN.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

}
