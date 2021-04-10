package com.youssef.filesprocessor.searchreplace;

public final class SearchReplaceObject
{
    private final SearchReplaceEngine searchReplaceEngine;
    private final String searchWord;
    private final String replaceWord;

    public SearchReplaceObject(final String searchWord,
                             final String replaceWord,
                             final SearchReplaceEngine searchReplaceEngine)
    {
        this.searchReplaceEngine = searchReplaceEngine;
        this.searchWord = searchWord;
        this.replaceWord = replaceWord;
    }

    public String getSearchWord()
    {
        return searchWord;
    }

    public String searchAndReplace(final String text)
    {
        return searchReplaceEngine.searchAndReplace(text, searchWord, replaceWord);
    }
}
