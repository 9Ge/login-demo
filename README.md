使用Mybatis + JWT + SpringSecurity 实现单点登录。

SQL中user表加密前的密码是：1234

这中间起最关键作用的就是token，token的安全与否，直接关系到系统的健壮性，这里我们选择使用JWT来实现token的生成和校验。 
JWT，全称JSON Web Token，官网地址https://jwt.io，是一款出色的分布式身份校验方案。可以生成token，也可以解析检验token。

代码开发流程：
1：先实现用户表从数据库中的[新增、查询]、开始测试
2：新增几个controller，模拟请求。
3：最后增加web security + jwt 的验证。
so 先实现前两步，在进行认证+鉴权的操作


代码逻辑：
1:用户登录信息拦截器 [ JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter]
2:实现登录逻辑，创建token  [ JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter] -->[successfulAuthentication]
3:用户权限拦截器 [JWTAuthorizationFilter extends BasicAuthenticationFilter]
4:查找登录用户信息 [UserDetailsServiceImpl implements UserDetailsService]
5：SpringSecurity配置[WebSecurityConfig extends WebSecurityConfigurerAdapter]
大致代码开发就是这些，按照步骤组装整个模块。

小的细节：
生成Token的帮助类：[JwtTokenUtils]


登录接口：[POST /auth/login Param:{"name":"Doug","pwd":"1234"}] 在**JWTAuthenticationFilter**类中有注明。
返回的token 在response header中。
请求是将token放入request header中 name:Authorization value:#{token}
