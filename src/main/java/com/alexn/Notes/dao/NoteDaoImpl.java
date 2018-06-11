package com.alexn.Notes.dao;

import com.alexn.Notes.model.Note;
import com.alexn.Notes.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class NoteDaoImpl implements NoteDao{

    UserRepository userRepository;

    public NoteDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Note findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Page findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page findByisComplete(boolean isComplete, Pageable pageable) {
        return userRepository.findByisComplete(isComplete, pageable);
    }

    @Override
    public void save(Note note) {
        userRepository.save(note);
    }

    @Override
    public void delete(Note note) {
        userRepository.delete(note);
    }
}
