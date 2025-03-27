package com.zjj.graphql.component.mutation;

import com.zjj.bean.componenet.BeanHelper;
import graphql.ExecutionInput;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import graphql.execution.preparsed.persisted.ApolloPersistedQuerySupport;
import graphql.execution.preparsed.persisted.InMemoryPersistedQueryCache;
import graphql.language.Document;
import graphql.parser.Parser;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 10:45
 */
public class TestSimpleMutation {


    @Test
    public void testCopy() {
        UserCopy userCopy = new UserCopy();
        userCopy.setName("张三");

        UserCopy toCopy = new UserCopy();
        toCopy.setId("1");
        toCopy.setName("李四");
        toCopy.setPhoneNumber("123456789");

        BeanHelper.copyPropertiesIgnoringNull(userCopy, toCopy);

        System.out.println(toCopy);
    }

    @Data
    public static class UserCopy {
        private String id;
        private String name;
        private String phoneNumber;
    }

    @Test
    public void testSave() {
        PreparsedDocumentProvider provider =
                new ApolloPersistedQuerySupport(new InMemoryPersistedQueryCache(Collections.emptyMap()));

        Document document = new Parser().parseDocument(query);

        PreparsedDocumentEntry preparsedDocumentEntry = new PreparsedDocumentEntry(document);
        ExecutionInput build = ExecutionInput.newExecutionInput(query).build();
        CompletableFuture<PreparsedDocumentEntry> documentAsync = provider.getDocumentAsync(build, (executionInput) -> preparsedDocumentEntry);
        documentAsync.thenAccept(preparsedDocumentEntry1 -> {
            System.out.println(preparsedDocumentEntry1.getDocument());
        });
    }

    private static String query = """
            query MyQuery {
                queryUser {
                    avatar
                    createdBy
                    createdDate
                    deleted
                    email
                    gender
                    id
                    lastModifiedBy
                    lastModifiedDate
                    nickName
                    password
                    phoneNumber
                    status
                    tenantBy
                }
            }     
            """;
}
