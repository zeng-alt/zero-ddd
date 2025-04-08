package cocm.zjj.security.rbac.component.router;

import com.zjj.security.rbac.component.router.RouteTemplateTrie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 21:47
 */
public class TestRouteTemplateTrie {

    RouteTemplateTrie routeTemplateTrie = new RouteTemplateTrie();

    @BeforeEach
    public void before() {
        routeTemplateTrie.insert("/main/v1/user/detail/{id}");
        routeTemplateTrie.insert("/main/v1/user/detail");
        routeTemplateTrie.insert("/main/v1/user/{detail}");
        routeTemplateTrie.insert("/main/v1/user");

        routeTemplateTrie.insert("/main1/v1/user/detail/{id}/{username}");
        routeTemplateTrie.insert("/main1/v1/user/detail/{id}");
        routeTemplateTrie.insert("/main1/v1/user/detail");
        routeTemplateTrie.insert("/main1/v1/user/{detail}");
        routeTemplateTrie.insert("/main1/v1/user");
    }

    @Test
    public void testMatch() {
        String match = routeTemplateTrie.match("/main1/v1/user/detail/1/张三");
        assertThat(match).isEqualTo("/main1/v1/user/detail/{id}/{username}");
        match = routeTemplateTrie.match("/main1/v1/user/detail/1");
        assertThat(match).isEqualTo("/main1/v1/user/detail/{id}");
        match = routeTemplateTrie.match("/main1/v1/user/detail");
        assertThat(match).isEqualTo("/main1/v1/user/detail");
        match = routeTemplateTrie.match("/main1/v1/user/12");
        assertThat(match).isEqualTo("/main1/v1/user/{detail}");
        match = routeTemplateTrie.match("/main1/v1/user");
        assertThat(match).isEqualTo("/main1/v1/user");
    }

    @Test
    public void testDeleteRecursive() {
        routeTemplateTrie.deleteSubtree("/main");
        assertThat(routeTemplateTrie.getAllTemplates()).containsExactlyInAnyOrder(
                "/main1/v1/user/detail/{id}/{username}",
                "/main1/v1/user/detail/{id}",
                "/main1/v1/user/detail",
                "/main1/v1/user/{detail}",
                "/main1/v1/user"
        );
    }
}
