package com.alexn.Notes.service;

import com.alexn.Notes.model.Note;
import com.alexn.Notes.dao.NoteDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    NoteDao noteDao;

    public NoteService(NoteDao noteDao){
        this.noteDao = noteDao;
    }

    public Note findOne(Long id) {
        return noteDao.findOne(id);
    }
    public Page findAll(Pageable pageable){
        return noteDao.findAll(pageable);
    }
    public Page findByisComplete(boolean isComplete, Pageable pageable){
        return noteDao.findByisComplete(isComplete, pageable);
    }
    public void save(Note note){
        noteDao.save(note);
    }
    public void delete(Note note){
        noteDao.delete(note);
    }
}
