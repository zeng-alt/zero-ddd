/*
 * Copyright 2015-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zjj.graphql.component.query.fuzzy;

import com.querydsl.core.types.EntityPath;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * A component that will customize {@link QuerydslBindings} for the given entity path.
 *
 * @author Oliver Gierke
 * @since 1.11
 */
public interface QuerydslFuzzyBinderCustomizer<T extends EntityPath<?>> {

	/**
	 * Customize the {@link QuerydslBindings} for the given root.
	 *
	 * @param bindings the {@link QuerydslBindings} to customize, will never be {@literal null}.
	 * @param root the entity root, will never be {@literal null}.
	 */
	void customize(QuerydslFuzzyBinding bindings, T root);
}
