package com.smartchit.smartchit360;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldchitRepo extends JpaRepository<GoldChit, Long> {}