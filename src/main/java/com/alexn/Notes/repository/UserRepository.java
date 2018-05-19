package com.alexn.Notes.repository;


import com.alexn.Notes.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<Note, Long> {

    Page<Note> findByisComplete(boolean isComplete, Pageable pageable);
}
