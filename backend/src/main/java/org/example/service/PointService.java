package org.example.service;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.example.dto.PointDTO;
import org.example.dto.PointResponseDTO;
import org.example.model.AreaChecker;
import org.example.model.Point;
import org.example.model.User;

@Stateless
public class PointService {

	@PersistenceContext(unitName = "Lab4Unit")
    private EntityManager em;

	public List<PointResponseDTO> processPoints(List<PointDTO> dtos, String login) {
		 User user = em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
				 .setParameter("login", login)
                 .getSingleResult();

		 List<Point> points = new ArrayList<>();

		 for (PointDTO dto: dtos) {
			 long startTime = System.nanoTime();
			 boolean hit = AreaChecker.checkHit(dto.getX(), dto.getY(), dto.getR(), dto.getClick());
			 long endTime = System.nanoTime();

			 notifyJmx(login, hit);

			 Point point = new Point(dto.getX(), dto.getY(), dto.getR(), hit, user);
			 point.setCheckTime(LocalDateTime.now());
			 point.setExecutionTime(endTime - startTime);

			 em.persist(point);
			 points.add(point);
		 }

		 return makeResult(points);
	}

	public List<PointResponseDTO> getHistory(String login, int page, int size) {
		List<Point> points = em.createQuery("SELECT p FROM Point p WHERE p.user.login = :login ORDER BY p.checkTime DESC", Point.class)
				.setParameter("login", login)
				.setFirstResult((page - 1) * size)
				.setMaxResults(size)
				.getResultList();

		return makeResult(points);
	}

	public List<PointResponseDTO> getAllUserPoints(String login) {
		List<Point> points = em.createQuery("SELECT p FROM Point p WHERE p.user.login = :login ORDER BY p.checkTime DESC", Point.class)
				.setParameter("login", login)
				.getResultList();

		return makeResult(points);
	}

	public void clearHistory(String login) {
		// 1. Сначала находим пользователя по логину
		User user = em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
				.setParameter("login", login)
				.getSingleResult();

		// 2. Удаляем точки напрямую по полученному user_id
		em.createQuery("DELETE FROM Point p WHERE p.user = :user")
				.setParameter("user", user)
				.executeUpdate();
	}

	private List<PointResponseDTO> makeResult(List<Point> points) {
		return points.stream().map(p -> new PointResponseDTO(
		        p.getX(), p.getY(), p.getR(), p.getHit(),
		        p.getCheckTime().toString(), p.getExecutionTime()
		    )).collect(Collectors.toList());
	}

	private void notifyJmx(String login, boolean hit) {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			mbs.invoke(
					new ObjectName("org.example.jmx:type=CountMisses"),
					"registerPoint",
					new Object[]{ login, hit },
					new String[]{ String.class.getName(), boolean.class.getName() }
			);

			mbs.invoke(
					new ObjectName("org.example.jmx:type=Statistic"),
					"calculateStatistic",
					new Object[]{ login, hit },
					new String[]{ String.class.getName(), boolean.class.getName() }
			);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
