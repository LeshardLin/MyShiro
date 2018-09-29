package cn.jhkj.shiro.Realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.HashCodeCustomizer;

/**
 * @Auther: LinYan
 * @Date: 2018/9/29 09:21
 * @Description:
 */
public class CustomRealmTest {

    CustomRealm customRealm = new CustomRealm();

    @Test
    public void testAuthentication() {
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");
        subject.checkPermissions("user:add");

    }

    @Test
    public void getMD5() {
        String password = "admin";

        Md5Hash md5Hash = new Md5Hash(password,"admin");
        md5Hash.setIterations(1);

        System.out.println("password: " + md5Hash);
        System.out.println("f6fdffe48c908deb0f4c3bd36c032e72".length());
    }
}
