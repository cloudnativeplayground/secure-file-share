package com.securefileshare.repository;

import com.securefileshare.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    // Custom query to find a file by its name
    Optional<File> findByFileName(String fileName);

    // Custom query to find files by encryption status
    Iterable<File> findByEncrypted(boolean encrypted);
}

