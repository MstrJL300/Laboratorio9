package edu.eci.arsw.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import edu.eci.arsw.model.*;

@RestController
public class EntryController {

    private final AtomicLong counter = new AtomicLong();
    private List<Entry> entries = new ArrayList<>();
    
    private Entry entry0 = new Entry("Title0","Content0");
    private Entry entry1 = new Entry("Title1","Content1");
    
    {
        entries.add(entry0);
        entries.add(entry1);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/blogs")
    public List<Entry> getEntries() {
        //Entry e = new Entry("Title0","Content0");
        //if(entries.contains(e))
            //System.out.println(entries.indexOf(e));
        return entries;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/blog")
    public  ResponseEntity<?>  postEntry(@RequestBody Entry p) {
        entries.add(p);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(method = RequestMethod.DELETE,value = "/blog")
    public  ResponseEntity<?>  deleteEntry(@RequestParam Integer p) {
        
        //int i = entries.indexOf(e);
        System.out.println(p);
        entries.remove(0);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
