package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer> {

	@Query("from Booking where opid=?1 AND startTime = ?2 AND status=1")
	Booking checkslot(Integer opid, Integer startTime);

	List<Booking> findByUserId(Integer userid);

	@Query("from Booking where opid in (?1) and status=?2")
	List<Booking> findByOperatorIdAndStatus(List<Integer> ops, int i);

}
