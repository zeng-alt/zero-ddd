package com.zjj.jpa.component;

import com.zjj.bean.componenet.ApplicationContextHelper;
import io.vavr.control.Option;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月03日 18:19
 */
@MappedSuperclass
public abstract class JpaBaseEntity<K, T> {

	/**
	 * 创建者
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createTime;

	/**
	 * 更新者
	 */
	private Long updateBy;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updateTime;

	// @PersistenceContext
	// private @Transient EntityManager entityManager;

	public abstract K getId();

	public Option<T> findById() {
		return Option
				.of(ApplicationContextHelper.getBean(EntityManager.class).find((Class<T>) this.getClass(), getId()));
	}

	public void save() {
		EntityManager entityManager = ApplicationContextHelper.getBean(EntityManager.class);
		TransactionTemplate transactionTemplate = ApplicationContextHelper.getBean(TransactionTemplate.class);
		JpaBaseEntity<K, T> t = this;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				entityManager.persist(t);
			}
		});
	}

	public void removeById() {
		EntityManager entityManager = ApplicationContextHelper.getBean(EntityManager.class);
		TransactionTemplate transactionTemplate = ApplicationContextHelper.getBean(TransactionTemplate.class);
		JpaBaseEntity<K, T> t = this;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				entityManager.remove(t);
			}
		});
	}

	public Page<T> pageByList(int page, int pageSize) {
		EntityManager entityManager = ApplicationContextHelper.getBean(EntityManager.class);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery((Class<T>) this.getClass());
		Root<T> root = criteriaQuery.from((Class<T>) this.getClass());
		criteriaQuery.select(root);

		criteriaQuery.where(getQueryRestrictions(criteriaBuilder, root));

		// 添加排序条件，例如按创建日期降序排列
		// criteriaQuery.where(criteriaBuilder.equal(root.get("entityType"),
		// this.getClass().getSimpleName()));
		// criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
		// criteriaBuilder.like(root.get("name"), "zjj");
		// 设置分页参数
		int offset = (page - 1) * pageSize;
		criteriaQuery.distinct(true);
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(pageSize);

		// 计算总记录数
		long totalCount = getTotalCount(criteriaBuilder, entityManager);

		// 创建 Page 对象
		return new PageImpl<>(typedQuery.getResultList(), PageRequest.of(page, pageSize), totalCount);
	}

	private long getTotalCount(CriteriaBuilder criteriaBuilder, EntityManager entityManager) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from((Class<T>) this.getClass());
		criteriaQuery.select(criteriaBuilder.count(root));
		// criteriaQuery.where(getQueryRestrictions(criteriaQuery));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private Predicate[] getQueryRestrictions(CriteriaBuilder criteriaBuilder, Root<T> root) {
		// 根据实际需求添加查询限制条件
		List<Predicate> list = new ArrayList<>();

		return list.toArray(new Predicate[0]);
	}

}
