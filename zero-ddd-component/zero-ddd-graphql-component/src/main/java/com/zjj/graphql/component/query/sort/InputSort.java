//package com.zjj.graphql.component.sort;
//
//import graphql.PublicApi;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Sort;
//import org.springframework.lang.Nullable;
//import org.springframework.util.ObjectUtils;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Locale;
//import java.util.Optional;
//
///**
// * @author zengJiaJun
// * @crateTime 2024年10月19日 20:22
// * @version 1.0
// */
//@PublicApi
//public class InputSort {
//
//    private List<Order> orders;
//
//
//
//    public static class Order implements Serializable {
//        private static final boolean DEFAULT_IGNORE_CASE = false;
//        private static final Sort.NullHandling DEFAULT_NULL_HANDLING = Sort.NullHandling.NATIVE;
//        private final Direction direction;
//        private final String property;
//        private final boolean ignoreCase;
//        private final NullHandling nullHandling;
//
//        public Order(@Nullable Direction direction, String property) {
//            this(direction, property, DEFAULT_IGNORE_CASE, DEFAULT_NULL_HANDLING);
//        }
//
//        /**
//         * Creates a new {@link Sort.Order} instance. if order is {@literal null} then order defaults to
//         * {@link Sort#DEFAULT_DIRECTION}
//         *
//         * @param direction can be {@literal null}, will default to {@link Sort#DEFAULT_DIRECTION}
//         * @param property must not be {@literal null} or empty.
//         * @param nullHandlingHint must not be {@literal null}.
//         */
//        public Order(@Nullable Direction direction, String property, NullHandling nullHandlingHint) {
//            this(direction, property, DEFAULT_IGNORE_CASE, nullHandlingHint);
//        }
//    }
//
//    public enum Direction {
//
//        ASC, DESC;
//
//        /**
//         * Returns whether the direction is ascending.
//         *
//         * @return {@literal true} if ascending, {@literal false} otherwise.
//         * @since 1.13
//         */
//        public boolean isAscending() {
//            return this.equals(ASC);
//        }
//
//        /**
//         * Returns whether the direction is descending.
//         *
//         * @return {@literal true} if descending, {@literal false} otherwise.
//         * @since 1.13
//         */
//        public boolean isDescending() {
//            return this.equals(DESC);
//        }
//
//        /**
//         * Returns the {@link Sort.Direction} enum for the given {@link String} value.
//         *
//         * @param value the direction name.
//         * @return the {@link Sort.Direction} enum value for the given {@code value}.
//         * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
//         */
//        public static Sort.Direction fromString(String value) {
//
//            try {
//                return Sort.Direction.valueOf(value.toUpperCase(Locale.US));
//            } catch (Exception e) {
//                throw new IllegalArgumentException(String.format(
//                        "Invalid value '%s' for orders given; Has to be either 'desc' or 'asc' (case insensitive)", value), e);
//            }
//        }
//
//        /**
//         * Returns the {@link Sort.Direction} enum for the given {@link String} or {@code Optional.empty()} if it cannot be
//         * parsed into an enum value.
//         *
//         * @param value the direction name.
//         * @return Optional holding the {@link Sort.Direction} enum value or empty, if {@code value} cannot be parsed into
//         *         {@link Sort.Direction}.
//         */
//        public static Optional<Sort.Direction> fromOptionalString(String value) {
//
//            if (ObjectUtils.isEmpty(value)) {
//                return Optional.empty();
//            }
//
//            try {
//                return Optional.of(fromString(value));
//            } catch (IllegalArgumentException e) {
//                return Optional.empty();
//            }
//        }
//    }
//
//    public enum NullHandling {
//
//        /**
//         * Lets the data store decide what to do with nulls.
//         */
//        NATIVE,
//
//        /**
//         * A hint to the used data store to order entries with null values before non null entries.
//         */
//        NULLS_FIRST,
//
//        /**
//         * A hint to the used data store to order entries with null values after non null entries.
//         */
//        NULLS_LAST;
//    }
//}
