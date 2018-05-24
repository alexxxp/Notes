package com.alexn.Notes.controllers;

import com.alexn.Notes.Note;
import com.alexn.Notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping(path = "/")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    private int PAGE_SIZE = 10;
    private String[] notesStates = {"All","Incomplete", "Complete"};
    private String[] dateOrders = {"New first", "Old first"};

    @GetMapping(path = "/")
    public ModelAndView example(@RequestParam(required = false) Integer page,
                                @RequestParam(required = false) String selectedNotesState,
                                @RequestParam(required = false) String selDateOrder) {
        if (page == null)
            page = 1;
        if (selectedNotesState == null)
            selectedNotesState = "All";
        if (selDateOrder == null)
            selDateOrder = "New first";

        Sort.Direction tempDateOrder;
        if (selDateOrder.equals("New first"))
            tempDateOrder = Sort.Direction.DESC;
        else
            tempDateOrder = Sort.Direction.ASC;

        Pageable pageable = new PageRequest(page - 1, PAGE_SIZE,
                new Sort(tempDateOrder, "createDate"));

        Page<Note> notes = null;
        if (selectedNotesState.equals("All"))
            notes = userRepository.findAll(pageable);
        else if (selectedNotesState.equals("Incomplete"))
            notes = userRepository.findByisComplete(false, pageable);
        else if (selectedNotesState.equals("Complete"))
            notes = userRepository.findByisComplete(true, pageable);

        ModelAndView modelAndView = new ModelAndView("greeting");
        modelAndView.addObject("notes", notes);
        modelAndView.addObject("curPage", page);
        modelAndView.addObject("notesStates", notesStates);
        modelAndView.addObject("selectedNotesState", selectedNotesState);
        modelAndView.addObject("dateOrders", dateOrders);
        modelAndView.addObject("selDateOrder", selDateOrder);
        return modelAndView;
    }


    @PostMapping(path = "/delete")
    public @ResponseBody
    String delete(@RequestParam Long id) {
        Note note = userRepository.findOne(id);
        if (note != null) {
            userRepository.delete(note);
            return "Successfully deleted";
        }
        return "Note doesn't exist";
    }

    @GetMapping(path = "/edit")
    public ModelAndView edit(@RequestParam Long id) {
        Note note = userRepository.findOne(id);
        if (note != null) {
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("note", note);
            return modelAndView;
        }
        return null;
    }

    @PostMapping(path = "/save")
    public @ResponseBody String save(@RequestParam Long id,
                     @RequestParam String name,
                     @RequestParam String content,
                     @RequestParam String isComplete) {

        Note note = userRepository.findOne(id);

        if (note != null) {
            if (!name.equals(note.getName()))
                note.setName(name);
            if (!content.equals(note.getContent()))
                note.setContent(content);
            String prevIsComplete = String.valueOf(note.isComplete);
            if (!isComplete.equals(prevIsComplete))
                note.setComplete(Boolean.valueOf(isComplete));
            userRepository.save(note);
            return "Saved";
        }
        return "Not found";
    }

    @GetMapping(path = "/new_note")
    public ModelAndView new_note() {
        ModelAndView modelAndView = new ModelAndView("new_note");
        return modelAndView;
    }

    @PostMapping(path = "/newNote")
    public @ResponseBody String newNote(@RequestParam String name,
                                        @RequestParam String content,
                                        @RequestParam String isComplete){
        Note note = new Note();
        note.setName(name);
        note.setContent(content);
        note.setComplete(Boolean.valueOf(isComplete));
        note.setCreateDate(new Date());
        userRepository.save(note);
        return "Saved";
    }
}
