package com.smartchit.smartchit360;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MembersRepo extends JpaRepository<Members, Long> {
    List<Members> findByChitId(Long chitId);
}