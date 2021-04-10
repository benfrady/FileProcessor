package com.youssef.filesprocessor.searchreplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchReplaceEngine
{
    public String searchAndReplace(final String text, final String searchWord, final String replaceWord)
    {
    	final Pattern pattern = Pattern.compile(searchWord, Pattern.CASE_INSENSITIVE);
    	Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(replaceWord);
    }
}
