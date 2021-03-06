package com.courtalon.jpa_associationonetomany.jpa;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.courtalon.jpa_associationonetomany.beans.*;

public class JpaTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testHibernate");
        Scanner input = new Scanner(System.in);

        input.nextLine();
        System.out.println("--------------------------------------");
		test1(emf);
/*
        input.nextLine();
		System.out.println("--------------------------------------");
		test2(emf);

		input.nextLine();
		System.out.println("--------------------------------------");
		test3(emf);
	*/	
		
		input.nextLine();
		System.out.println("--------------------------------------");
		test4(emf);
		
        input.nextLine();
		System.out.println("--------------------------------------");		

	    emf.close();
		System.out.println("done...");
	}




	public static void test1(EntityManagerFactory emf)
	{
		// on recupere un entityManager
		EntityManager em = emf.createEntityManager();
		// et une transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		//----------------------------------------------------
		Auteur a1 = new Auteur(0, "Vernes", "jules", "jule@yahoo.fr");
		Auteur a2 = new Auteur(0, "Zola", "emile", "emile@sncf.fr");
		Auteur a3 = new Auteur(0, "Bieber", "justin", "justin@yolo.com");
		
		em.persist(a1);
		em.persist(a2);
		em.persist(a3);
		
		Random rd = new Random();
		for (int i = 1; i <= 30; i++) {
			Post p = new Post(0, "titre" + i, "corps"+ i, rd.nextInt(10));
			// ici, je tire au hazard un nombre entre 1 et 3
			// et je recuper l'auteur correspond
			p.setAuteur(em.find(Auteur.class, rd.nextInt(3) + 1));
			em.persist(p);
		}
		// ici, ceci ne marchera pas
		// hibernate n'initialise la collection qu'au chargement d'une entité depuis la
		// base...(find, query, merge, etc... mais pas persist)
		for (Post p1: a1.getPosts()) {
			System.out.println(p1);
		}
		//----------------------------------------------------
		tx.commit();
		em.close();
	}


	public static void test2(EntityManagerFactory emf)
	{
		// on recupere un entityManager
		EntityManager em = emf.createEntityManager();
		// et une transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		//----------------------------------------------------
		
		Post p1 = em.find(Post.class, 1);
		System.out.println("1---------------------");
		System.out.println(p1);
		System.out.println("2---------------------");
		System.out.println(p1.getAuteur());
		System.out.println("3---------------------");
		
		Auteur a1 = em.find(Auteur.class, 2);
		System.out.println("1---------------------");
		System.out.println(a1);
		System.out.println("2---------------------");
		for (Post p : a1.getPosts()) {
			System.out.println(p);
		}
		System.out.println("3---------------------");
		
		
		
		//----------------------------------------------------
		tx.commit();
		em.close();
	}

	public static void test3(EntityManagerFactory emf)
	{
		// on recupere un entityManager
		EntityManager em = emf.createEntityManager();
		// et une transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		//----------------------------------------------------
		
		Post p1 = new Post(0, "le tour du monde en 80 jours", "blah blah blah", 8);
		//em.persist(p1);

		/*
		 * attention, seul le coté maitre de l'association, c.a.d l'entite avec la clé
		 * étrangere provoque une mise a jour de la base (ici c'est post)
		 * 
		 * faire aussi attention a l'ordre de sauvegarde, si l'on n'a pas de cascade de persitence
		 */
		
		Auteur a1 = em.find(Auteur.class, 1);
		//a1.getPosts().add(p1);
		a1.addPost(p1);
		//p1.setAuteur(em.find(Auteur.class, 1));
		
		System.out.println("-------------------------------------------------");
		Post p2 = new Post(0, "le horla", "tralalalala...", 7);
		Auteur a2 = new Auteur(0, "maupassant", "guy", "guy@normandie.fr");
		
		p2.setAuteur(a2);
		//em.persist(a2);
		em.persist(p2);
		
		System.out.println("----------------------");
		Auteur a3 = em.find(Auteur.class, 3);
		em.remove(a3);
		
		//----------------------------------------------------
		tx.commit();
		em.close();
	}
	
	public static void test4(EntityManagerFactory emf)
	{
		// on recupere un entityManager
		EntityManager em = emf.createEntityManager();
		// et une transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		//----------------------------------------------------
		
/*		TypedQuery<Post> requete1 = em.createQuery(
				"select p from Post as p where p.auteur.id=1",
				Post.class);*/
		TypedQuery<Post> requete1 = em.createQuery(
				"select p from Post as p where p.auteur.nom='Zola'",
				Post.class);
		List<Post> posts = requete1.getResultList();
		for (Post p : posts) {
			System.out.println(p);
		}
		System.out.println("----------------------");
		
/*		TypedQuery<Auteur> requete2 = em.createQuery(
				"select a from Auteur as a where a.posts.rating > 5",
				Auteur.class);*/
		// lister les auteur aillant plus de 11 posts
		TypedQuery<Auteur> requete2 = em.createQuery(
				"select a from Auteur as a where size(a.posts) > 11",
				Auteur.class);

		List<Auteur> auteurs = requete2.getResultList();
		for (Auteur a : auteurs) {
			System.out.println(a);
		}
		System.out.println("----------------------");
		
	/*	Query requete3 = em.createQuery(
				"select a.nom, AVG(p.rating) from Auteur as a, in(a.posts) as p group by a.nom");*/
		
		// lister les nom d'auteur avec la moyenne des rating de leur posts
		Query requete3 = em.createQuery(
				"select a.nom, AVG(p.rating) from Auteur as a join a.posts as p group by a.nom");
		// fonctions d'aggregation disponnible en JPQL
		// AVG, SUM, COUNT, MIN, MAX
		
		List<Object[]> data = requete3.getResultList();
		for (Object[] ligne : data) {
			System.out.println(Arrays.toString(ligne));
		}
		
		
		
		//----------------------------------------------------
		tx.commit();
		em.close();
	}

}
