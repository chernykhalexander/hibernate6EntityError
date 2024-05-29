package org.hibernate.bugs;

import jakarta.persistence.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPA6UnitTestCase
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

//		em.unwrap(Session.class).refresh(cr, new LockOptions(LockMode.PESSIMISTIC_WRITE).setFollowOnLocking(false));
		em.refresh(cr, LockModeType.PESSIMISTIC_WRITE); //<-- too many entities been locked up here
		transaction.commit();

		em.close();
	}
}
