package cn.jhkj.shiro.Realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: LinYan
 * @Date: 2018/9/29 08:38
 * @Description:演示自定义的realm
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<>();

    {
        userMap.put("admin", "f6fdffe48c908deb0f4c3bd36c032e72");

        super.setName("customRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        //从数据库或是缓存中获取角色数据
        Set<String> roles = getRolesByUsername(username);
        Set<String> permissions = getPermissionsByUsername(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    private Set<String> getPermissionsByUsername(String username) {
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    private Set<String> getRolesByUsername(String username) {
        Set<String> sets = new HashSet<>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
        String username = (String) token.getPrincipal();

        //2.通过用户名到数据库中获取凭证
        String password = getPasswordByUsername(username);
        if (password == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("admin", password, "customRealm");

        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("admin"));

        return authenticationInfo;
    }

    /**
     * 模拟数据库查询凭证
     *
     * @param username
     * @return
     */
    private String getPasswordByUsername(String username) {
        return userMap.get(username);
    }
}
