package com.alexn.Notes.dao;

import com.alexn.Notes.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteDao {
    Note findOne(Long id);
    Page findAll(Pageable pageable);
    Page findByisComplete(boolean isComplete, Pageable pageable);
    void save(Note note);
    void delete(Note note);
}
