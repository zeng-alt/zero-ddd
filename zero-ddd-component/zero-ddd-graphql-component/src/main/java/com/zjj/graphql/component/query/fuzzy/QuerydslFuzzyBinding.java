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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.CollectionPathBase;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link MultiValueBinding} creating {@link Predicate} based on the {@link Path}s type.
 * Binds:
 * <ul>
 * <li><i>{@link Object}</i> as {@link NumberExpression#like(String)} ()} on simple properties.</li>
 * <li><i>{@link Object}</i> as {@link StringExpression#likeIgnoreCase(String)} ()} on simple properties.</li>
 * <li><i>{@link Object}</i> as {@link SimpleExpression#eq()} on simple properties.</li>
 * <li><i>{@link Object}</i> as {@link SimpleExpression#contains()} on collection properties.</li>
 * <li><i>{@link Collection}</i> as {@link SimpleExpression#in()} on simple properties.</li>
 * </ul>
 *
 * @author zengJiaJun
 * @crateTime 2024年10月19日 23:23
 * @version 1.0
 */
class QuerydslFuzzyBinding implements MultiValueBinding<Path<? extends Object>, Object> {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Optional<Predicate> bind(Path<?> path, Collection<? extends Object> value) {

		Assert.notNull(path, "Path must not be null");
		Assert.notNull(value, "Value must not be null");

		if (value.isEmpty()) {
			return Optional.empty();
		}

		if (path instanceof CollectionPathBase) {

			BooleanBuilder builder = new BooleanBuilder();

			for (Object element : value) {

				if (element instanceof Collection<?> nestedCollection) {

					for (Object nested : nestedCollection) {
						builder.and(((CollectionPathBase) path).contains(nested));
					}
				} else {
					builder.and(((CollectionPathBase) path).contains(element));
				}

			}

			return Optional.of(builder.getValue());
		}

		if (path instanceof StringExpression expression) {
			if (value.size() > 1) {
				expression.in(value.stream().map(Object::toString).collect(Collectors.toSet()));
			}
			Object object = value.iterator().next();
			return Optional.of(object == null
					? expression.isNull()
					: expression.likeIgnoreCase(object.toString()));
		}

		if (path instanceof NumberExpression<?> expression) {
			if (value.size() == 1) {
				Object object = value.iterator().next();
				return Optional.of(object == null
						? expression.isNull()
						: expression.like("%" + object + "%"));
			}
		}

		if (path instanceof SimpleExpression expression) {

			if (value.size() > 1) {
				return Optional.of(expression.in(value));
			}

			Object object = value.iterator().next();
			return Optional.of(object == null //
					? expression.isNull() //
					: expression.eq(object));
		}

		throw new IllegalArgumentException(
				String.format("Cannot create predicate for path '%s' with type '%s'", path, path.getMetadata().getPathType()));
	}
}
