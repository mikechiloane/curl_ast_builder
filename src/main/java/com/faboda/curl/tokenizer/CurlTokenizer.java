package com.faboda.curl.tokenizer;

import java.util.List;


public interface CurlTokenizer {

    /**
     * Tokenize the curl command into a list of tokens.
     *
     * @return A list of tokens extracted from the curl command.
     */
    void tokenize(String command);


    /**
     * Retrieve the next token in the sequence.
     *
     * @return The next token, or null if there are no more tokens.
     */
    String nextToken();

    /**
     * Check if there are more tokens to be retrieved.
     *
     * @return True if there are more tokens, false otherwise.
     */
    boolean hasMoreTokens();

    List<String> getTokens();
}

