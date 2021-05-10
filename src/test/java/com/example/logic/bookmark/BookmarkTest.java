package com.example.logic.bookmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkTest {
    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        bookmark = new Bookmark();
    }


    /*
     * Ensure that single keywords can be added to one bookmark
     * @param keyword: Check if single keyword can be added
     * */
    @ParameterizedTest
    @CsvSource({"Fun, true"})
    public void ensureSingleKeywordWillBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    /*
     * Ensure that multiple keywords can be added to one bookmark
     * @param keyword: Check if multiple keywords can be added
     * */
    @ParameterizedTest
    @CsvSource({"Fun, true", "Science, true", "Digital, true"})
    public void ensureMultipleKeywordsWillBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    /*
     * Ensure that empty strings can't be added to a bookmark
     * @param keyword: Check if empty keyword's can't be added to the list
     * */
    @ParameterizedTest
    @CsvSource({"Fun, true", ", false", "'', false"})
    public void ensureEmptyKeywordCantBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    /*
     * Ensure that duplicated keywords can't be add to a bookmark
     * @param keyword: Check if duplicated keyword won't be added to the list
     * */
    @ParameterizedTest
    @CsvSource({"Science, false"})
    public void ensureDuplicatedKeywordCantBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");

        bookmark.addKeyword("Science");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void ensureBookmarkFromSameDomainIsAddedToExistingBookmark(){
        //Arrange
        Bookmark existingBookmark = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark.setUrl("http://www.google.at/page1");
        newBookmark.setUrl("http://www.google.at");

        //Act
        boolean addAssociatedBookmark = bookmark.addAssociatedBookmark(existingBookmark, newBookmark);

        //Assert
        assertTrue(addAssociatedBookmark);
    }

    @Test
    public void ensureBookmarkFromOtherDomainIsNotAddedToBookmark(){
        //Arrange
        Bookmark existingBookmark = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark.setUrl("http://www.google.at/page1");
        newBookmark.setUrl("http://www.google.com/page1");

        //Act
        boolean addAssociatedBookmark = bookmark.addAssociatedBookmark(existingBookmark, newBookmark);

        //Assert
        assertFalse(addAssociatedBookmark);
    }

    @Test
    public void ensureExistingBookmarkFromSameDomainIsAddedToNewBookmark(){
        //Arrange
        Bookmark existingBookmark = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark.setUrl("http://www.google.at/page1");
        newBookmark.setUrl("http://www.google.at");

        //Act
        bookmark.addAssociatedBookmark(existingBookmark, newBookmark);
        List<Bookmark> bookmarks = existingBookmark.getBookmarksOfSameDomain(newBookmark);
        boolean isInside = bookmarks.contains(existingBookmark);

        //Assert
        assertTrue(isInside);
    }

}
