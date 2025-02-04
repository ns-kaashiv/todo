package com.springboot.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    @Autowired
    TodoRepository todoRepository;
        
    @GetMapping("/")
    public String todos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "index";
    }
    
    @PostMapping("/todoNew")
    public String add(@RequestParam String todoItem, @RequestParam
        String status, Model model) {
        TodoModel todo = new TodoModel(todoItem, status);
        todo.setTodoItem(todoItem);
        todo.setCompleted(status);
        todoRepository.save(todo);
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/";
    }
    
    @PostMapping("/todoDelete/{id}")
    public String delete(@PathVariable long id, Model model) {
        todoRepository.deleteById(id);
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/"; 
    }
    
    @PostMapping("/todoUpdate/{id}")
    public String update(@PathVariable long id, Model model) {
        TodoModel todo = todoRepository.findById(id).get();
        if("Yes".equals(todo.getCompleted())) {
        todo.setCompleted("No");
        }
        else {
        todo.setCompleted("Yes");
        }
        todoRepository.save(todo);
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/";
    }
}
