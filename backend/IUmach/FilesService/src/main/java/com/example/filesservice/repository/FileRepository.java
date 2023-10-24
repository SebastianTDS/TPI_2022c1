package com.example.filesservice.repository;

import com.example.filesservice.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {

    File findByIdUserAndIdGroupIsNullAndIsFileIsFalse(String id);

    File findByIdGroupAndIsFileFalse(Long id);

    List<File> findAllByIdGroupAndIsFileTrue(Long id);

    File findByName(String name);

    @Query(value = "SELECT SUM(f.size) FROM files f WHERE f.is_file = 1 AND f.id_group = :id", nativeQuery = true)
	Long getGroupRepoSpace(@Param("id") Long idGroup);
}
