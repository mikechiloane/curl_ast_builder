package com.faboda.curl;

import com.faboda.curl.ast.ASTNode;
import com.faboda.curl.ast.CurlASTBuilder;
import com.faboda.curl.tokenizer.StandardCurlTokenizer;

import java.util.List;

public class MockASTService {

    private final static String curl = "curl 'https://dummyjson.com/products/add' \\\n" +
            "  -H 'Content-Type: application/json' \\\n" +
            "  --data-raw '{ \"title\": \"BMW Pencil\"}'";

    public static ASTNode buildAST(){
        List<String> tokens = new StandardCurlTokenizer(curl).getTokens();
        return CurlASTBuilder.buildAST(tokens);
    }
}
