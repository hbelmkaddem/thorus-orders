package com.barcode.barcode.Repository;

import com.barcode.barcode.model.Etats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<Etats,Long> {
}
