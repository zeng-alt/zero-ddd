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
package com.zjj.graphql.component.query.condition;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathType;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;

/**
 * Default implementation of {@link MultiValueBinding} creating {@link Predicate} based on the {@link Path}s type.
 * Binds: <br>
 * 支持多条件查询，支持的条件请参考 {@link Option}
 *
 * @author zengJiaJun
 * @crateTime 2024年10月19日 23:23
 * @version 1.0
 */
@Slf4j
class QuerydslConditionBinding implements MultiValueBinding<Path<? extends Object>, Object> {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Optional<Predicate> bind(Path<?> path, Collection<? extends Object> value) {

		Assert.notNull(path, "Path must not be null");
		Assert.notNull(value, "Value must not be null");

		if (path instanceof SimpleExpression<?> expression && value.isEmpty()) {
			return Optional.of(expression.isNull());
		}


		Condition condition = (Condition) value.iterator().next();
		Collection v = condition.getValue();
		Option option = condition.getOption();

		if ((!Option.NULL.equals(option) && !Option.NOT_NULL.equals(option)) && (v == null || v.isEmpty())) {
			return Optional.empty();
		}

		if (path instanceof CollectionPathBase expression) {

			BooleanBuilder builder = new BooleanBuilder();

			for (Object element : value) {

				if (element instanceof Collection<?> nestedCollection) {

					for (Object nested : nestedCollection) {
						builder.and(expression.contains(nested));
					}
				} else {
					builder.and(expression.contains(element));
				}

			}

			assert builder.getValue() != null;
			return Optional.of(builder.getValue());
		}

		if (path instanceof SimpleExpression expression) {
			if (Option.NULL.equals(option)) {
				return Optional.of(expression.isNull());
			}

			if (Option.NOT_NULL.equals(option)) {
				return Optional.of(expression.isNotNull());
			}

			Object o = v.iterator().next();
			Optional<Predicate> optional = Match(option).of(
					Case($(Option.EQ), Optional.of(expression.eq(o))),
					Case($(Option.NE), Optional.of(expression.ne(o))),
					Case($(Option.IN), Optional.of(expression.in(v))),
					Case($(Option.NOT_IN), Optional.of(expression.notIn(v))),

					Case($(), () -> {
						log.warn("不是常规表达式");
						return Optional.empty();
					})
			);
			if (optional.isPresent()) {
				return optional;
			}
		}



		if (path instanceof NumberExpression expression) {

			Object o = v.iterator().next();
			Optional<Predicate> optional = Match(option).of(
					Case($(Option.LIKE), Optional.of(expression.stringValue().likeIgnoreCase("%" + o + "%"))),
					Case($(Option.NOT_LIKE), Optional.of(expression.stringValue().notLike("%" + o + "%"))),
					Case($(Option.RIGHT_LIKE), Optional.of(expression.stringValue().likeIgnoreCase("%" + o))),
					Case($(Option.LIFT_LIKE), Optional.of(expression.stringValue().likeIgnoreCase(o + "%"))),
					Case($(Option.GT), Optional.of(expression.gt((Number) o))),
					Case($(Option.GTE), Optional.of(expression.goe((Number) o))),
					Case($(Option.LT), Optional.of(expression.lt((Number) o))),
					Case($(Option.LTE), Optional.of(expression.loe((Number) o))),
					Case($(allOf(is(Option.BETWEEN), (t -> v.size() > 1))), () -> {
						Iterator iterator = v.iterator();
						return Optional.of(expression.between((Number) iterator.next(), (Number) iterator.next()));
					}),
					Case($(allOf(is(Option.NOT_BETWEEN), (t -> v.size() > 1))), () -> {
						Iterator iterator = v.iterator();
						return Optional.of(expression.notBetween((Number) iterator.next(), (Number) iterator.next()));
					}),
					Case($(), () -> {
						log.warn("不是常规表达式");
						return Optional.empty();
					})
			);
			if (optional.isPresent()) {
				return optional;
			}
		}


		if (path instanceof LiteralExpression expression) {

			Object o = v.iterator().next();
			Optional<Predicate> optional = Match(option).of(
					Case($(Option.LIKE), Optional.of(expression.stringValue().likeIgnoreCase("%" + o + "%"))),
					Case($(Option.NOT_LIKE), Optional.of(expression.stringValue().notLike("%" + o + "%"))),
					Case($(Option.RIGHT_LIKE), Optional.of(expression.stringValue().likeIgnoreCase("%" + o))),
					Case($(Option.LIFT_LIKE), Optional.of(expression.stringValue().likeIgnoreCase(o + "%"))),
					Case($(Option.GT), Optional.of(expression.gt((Comparable) o))),
					Case($(Option.GTE), Optional.of(expression.goe((Comparable) o))),
					Case($(Option.LT), Optional.of(expression.lt((Comparable) o))),
					Case($(Option.LTE), Optional.of(expression.loe((Comparable) o))),
					Case($(allOf(is(Option.BETWEEN), (t -> v.size() > 1))), () -> {
						Iterator iterator = v.iterator();
						return Optional.of(expression.between((Comparable) iterator.next(), (Comparable) iterator.next()));
					}),
					Case($(allOf(is(Option.NOT_BETWEEN), (t -> v.size() > 1))), () -> {
						Iterator iterator = v.iterator();
						return Optional.of(expression.notBetween((Comparable) iterator.next(), (Comparable) iterator.next()));
					}),
					Case($(), () -> {
						log.warn("不是文本表达式");
						return Optional.empty();
					})
			);
			if (optional.isPresent()) {
				return optional;
			}
		}

		throw new IllegalArgumentException(
				String.format("Cannot create predicate for path '%s' with type '%s'", path, path.getMetadata().getPathType()));
	}
}
