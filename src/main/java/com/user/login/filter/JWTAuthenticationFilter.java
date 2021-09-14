package com.user.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.login.jwt.JwtTokenUtils;
import com.user.login.user.model.User;
import com.user.login.web.ResultMsg;
import com.user.login.web.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 ,
 * attemptAuthentication：接收并解析用户凭证。 
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token并返回。
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  
  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    this.setMessageSource(messageSource());
    super.setFilterProcessesUrl("/auth/login");
  }

  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource
            = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages.properties");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    User loginUser = null;
    // 从输入流中获取到登录的信息
    try {
      loginUser = new ObjectMapper().readValue(request.getInputStream(), User.class);
    } catch (IOException e) {
      return null;
    }
    try {
      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginUser.getName(), loginUser.getPwd())
      );
    } catch (AuthenticationException e) {
      log.error(e.getMessage());
      throw new LoginException("login failed，"+e.getMessage());
    }
  }

  // 成功验证后调用的方法
  // 如果验证成功，就生成token并返回
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                      HttpServletResponse response,
                      FilterChain chain,
                      Authentication authResult) throws IOException, ServletException {
    org.springframework.security.core.userdetails.User jwtUser= (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
    log.info("{}",jwtUser.getAuthorities());
    System.out.println("jwtUser:" + jwtUser.toString());

    String role = "";
    Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
    for (GrantedAuthority authority : authorities){
      role = authority.getAuthority();
    }

    String token = JwtTokenUtils.createToken(jwtUser.getUsername(), role);
    successfulAuthentication(response,JwtTokenUtils.TOKEN_PREFIX + token);
  }


  protected void successfulAuthentication(HttpServletResponse response,String tokenStr) throws IOException, ServletException {
    ResultMsg resultMsg = new ResultMsg();
    resultMsg.setResultCode(200);
    resultMsg.setResultMessage("登陆成功");
    resultMsg.setResultObject(tokenStr);
    logResult(response,resultMsg);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    ResultMsg resultMsg = new ResultMsg();
    resultMsg.setResultCode(403);
    resultMsg.setResultMessage("登陆失败");
    resultMsg.setResultObject(failed.getMessage());
    logResult(response,resultMsg);
  }


  public void logResult( HttpServletResponse response, ResultMsg resultMsg) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    ObjectMapper objectMapper  = new ObjectMapper();
    response.getWriter().write(objectMapper.writeValueAsString(resultMsg));
  }
}