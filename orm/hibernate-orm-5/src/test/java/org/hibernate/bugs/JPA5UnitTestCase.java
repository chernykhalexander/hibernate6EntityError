package org.hibernate.bugs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.UUID;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPA5UnitTestCase
{

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager em = entityManagerFactory.createEntityManager();

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		ChatRoom cr = new ChatRoom();
		ChatRoomUser cru = new ChatRoomUser();
		cru.setInfo("1234");
		cru.setChatRoom(cr);
		cru.setId(new ChatRoomUserId(cr.getId(), UUID.randomUUID().toString()));
		em.persist(cr);
		em.persist(cru);
		transaction.commit();

		transaction.begin();
		em.flush();
		em.clear();
		transaction.commit();

		transaction.begin();
		System.out.println("findById:");
		cr = em.find(ChatRoom.class, cr.getId());

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ChatRoom> query = criteriaBuilder.createQuery(ChatRoom.class);
		Root<ChatRoom> from = query.from(ChatRoom.class);

		query.where(criteriaBuilder.equal(from.get("id"), cr.getId()));
		TypedQuery<ChatRoom> typedQuery = em.createQuery(query);
		typedQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		System.out.println("query:");
		cr = typedQuery.getSingleResult();

		System.out.println("refresh:");
		em.refresh(cr, LockModeType.PESSIMISTIC_WRITE);// <-- only chat room here been locked
		transaction.commit();

		em.close();
	}
}
