package fr.umlv.javainside.lab1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class SwitchExampleTest {
	
    @Test
    void dog() {
        assertEquals(1, SwitchExample.switchExample("dog"));
    }
    
    @Test
    void cat() {
        assertEquals(2, SwitchExample.switchExample("cat"));
    }
    
    @Test
    void other() {
        assertEquals(4, SwitchExample.switchExample("frog"));
    }
    @Test
    void dog2() {
        assertEquals(1, SwitchExample.switchExample2("dog"));
    }
    
    @Test
    void cat2() {
        assertEquals(2, SwitchExample.switchExample2("cat"));
    }
    
    @Test
    void other2() {
        assertEquals(4, SwitchExample.switchExample2("mouse"));
    }
}
