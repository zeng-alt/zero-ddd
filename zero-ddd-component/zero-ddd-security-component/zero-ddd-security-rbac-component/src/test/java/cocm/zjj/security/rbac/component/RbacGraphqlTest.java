package cocm.zjj.security.rbac.component;

import graphql.language.Definition;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:43
 */
public class RbacGraphqlTest {


    @Test
    public void test() {

        Definition definition = new Parser().parseDocument("""
                            {
                            findTenant {
                        accountCount
                                address
                        companyName
                                contactPhone
                        createdBy
                                createdDate
                        contactUserName
                                domain
                    }
                    findTenantDataSource {
                        createdBy
                                createdDate
                        driverClassName
                                id
                        lastModifiedBy
                                lastModifiedDate
                    }
                    run
                    queryTenantMenu {
                        createdBy
                                createdDate
                        lastModifiedDate
                    }
                }""").getDefinitions().get(0);

        OperationDefinition definition1 = (OperationDefinition) definition;
        for (Selection selection : definition1.getSelectionSet().getSelections()) {
            System.out.println(((Field) selection).getName());
        }
        System.out.println(definition);
    }
}
