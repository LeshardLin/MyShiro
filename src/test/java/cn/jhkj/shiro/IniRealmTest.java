package cn.jhkj.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Auther: LinYan
 * @Date: 2018/9/28 10:07
 * @Description:
 */
public class IniRealmTest {

    @Test
    public void testIniRealm() {

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Admin", "123123");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        //验证身份
        subject.checkRole("admin");
        //验证权限
        subject.checkPermission("user:delete");
    }
}
