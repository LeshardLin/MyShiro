package cn.jhkj.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import sun.security.provider.MD5;

/**
 * @Auther: LinYan
 * @Date: 2018/9/28 09:11
 * @Description:
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("Admin", "123123", "admin");
    }

    @Test
    public void testAuthentication() {
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Admin", "123123");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

//        subject.logout();
//        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");
    }

    @Test
    public void md5Test() {
        String password = "123456";

        System.out.println("MD5普通加密" + new Md5Hash(password));
        System.out.println("MD5加盐" + new Md5Hash(password, "#"));
    }
}
