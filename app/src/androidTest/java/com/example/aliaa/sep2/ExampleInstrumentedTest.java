package com.example.aliaa.sep2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.example.aliaa.sep2.controller.Engine;
import com.example.aliaa.sep2.model.Book;
import com.example.aliaa.sep2.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends InstrumentationTestCase {
    Engine engine ;
    @Before
    public void setUp(){
        engine = Engine.getInstance(InstrumentationRegistry.getTargetContext());


    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.aliaa.sep2", appContext.getPackageName());
    }
    @Test
    public void signUpTest() throws Exception {
        User newUser = new User ("shereen","shereen@gmail.com","123456789","12345678912345",0);

        assertEquals(true, engine.addUser(newUser));
    }
    @Test
    public void emailSignUpTest() throws Exception {
        User newUser = new User ("shereen","shhhhhhhh","123485869258","56726517486938",0);
        assertEquals(false, engine.addUser(newUser));
    }
    @Test
    public void passwordSignUpTest() throws Exception {
        User newUser = new User ("shereen","shereen@gmail.com","1234","56726517486938",0);
        assertEquals(false,engine.addUser(newUser));
    }
    @Test
    public void creditCardTest() throws Exception {
        User newUser = new User ("shereen","shereen@gmail.com","12345678","5678",0);

        assertEquals(false, engine.addUser(newUser));
    }
    /*@Test
    public void LoginTest() throws Exception {

        assertEquals(false, engine.findUser("shereen@gmail.com","123456789" +
                ""));
    }*/
    @Test
    public void emailLoginTest() throws Exception {

        assertEquals(false, engine.findUser("mohamed@gmail.com","1234"));
    }

    @Test
    public void passwordLoginTest() throws Exception {

        assertEquals(true, engine.findUser("aliaa@gmail.com","123456789"));
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void searchByCategory() throws Exception {

        assertEquals("a", engine.search("history","category").get(0).getTitle());
    }
    @Test
    public void searchByAuthor() throws Exception {

        assertEquals(3, engine.search("aliaa","author").size());
    }
    @Test
    public void searchByZone() throws Exception {

        assertEquals(2, engine.search("a","zone").size());
    }
    @Test
    public void searchByDate() throws Exception {

        assertEquals("c", engine.search("2010","date").get(0).getTitle());
    }
    @Test
    public void searchByName() throws Exception {

        assertEquals("a", engine.search("a","name").get(0).getTitle());
    }
    @Test
    public void getAllBooks() throws Exception {

        assertEquals(4, engine.getAllBooks().size());
    }
    @Test
    public void searchByWrongName() throws Exception {

        assertEquals(0, engine.search("d", "name").size());
    }
    @Test
    public void searchByWrongCategory() throws Exception {
        assertEquals(0, engine.search("computer","category").size());

    }
    @Test
    public void searchByWrongZone() throws Exception {

        assertEquals(0, engine.search("d","zone").size());
    }
    @Test
    public void searchByWrongAuthors() throws Exception {

        assertEquals(0, engine.search("radwa","author").size());
    }
    @Test
    public void addBook() throws Exception {
        Book a = new Book(1,"1234567891234", "a", "history", "aliaa", "bla bla" , 2014, 1, "a");
        assertEquals(true, engine.addBook(a));
    }
    @Test
    public void addBookExisted() throws Exception {
        Book a = new Book(2,"1234567891234", "a", "history", "aliaa", "bla bla" , 2014, 1, "a");
        assertEquals(false, engine.addBook(a));
    }
    @Test
    public void addWrongISBN() throws Exception {
        Book a = new Book(3,"", "a", "history", "aliaa", "bla bla" , 2014, 1, "a");
        assertEquals(false, engine.addBook(a));
    }
    @Test
    public void addWrongAuthor() throws Exception {
        Book a = new Book(4,"4561234561234", "a", "history", "123", "bla bla" , 2014, 1, "a");
        assertEquals(false, engine.addBook(a));
    }
    @Test
    public void addWrongDate() throws Exception {
        Book a = new Book(5,"9638527418529", "a", "history", "123", "bla bla" , 2019, 1, "a");
        assertEquals(false, engine.addBook(a));
    }
    @Test
    public void addWrongCategory() throws Exception {
        Book a = new Book(6,"9638577418529", "a", "555", "123", "bla bla" , 2019, 1, "a");
        assertEquals(false, engine.addBook(a));
    }
    @Test
    public void deleteBook() throws Exception {
        assertEquals(true, engine.deleteBook("1234567891234"));
    }
    @Test
    public void deleteWrongBook() throws Exception {
        assertEquals(false, engine.deleteBook("1234567891"));
    }
    @Test
    public void deleteNonExistedBook() throws Exception {
        assertEquals(false, engine.deleteBook("4564564564564"));
    }



}
