package com.faboda.curl.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardCurlTokenizer implements CurlTokenizer {

    private List<String> tokens;

    public StandardCurlTokenizer(String curlCommand){
        tokens = new ArrayList<>();
        tokenize(curlCommand);
    }

    @Override
    public void tokenize(String command) {
        String curlRegex = "\\S+";
        Pattern pattern = Pattern.compile(curlRegex);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            if (!matcher.group().equals("\\")) {
                tokens.add(matcher.group());
            }
        }
        cleanTokens();
    }

    public void cleanTokens() {
        StringBuilder temp = new StringBuilder();
        List<String> tempTokens = new ArrayList<>();

        for (int curr = 0; curr < tokens.size(); curr++) {
            if (tokens.get(curr).startsWith("-")) {
                int next = curr + 1;
                temp.append(tokens.get(curr)).append(" ");
                while (next < tokens.size() && !tokens.get(next).startsWith("-")) {
                    temp.append(tokens.get(next)).append(" ");
                    next++;
                }

                tempTokens.add(temp.toString());
                temp.delete(0, temp.length());

                curr = next - 1;
            } else {
                tempTokens.add(tokens.get(curr));
            }
        }

        tokens = tempTokens;
    }


    @Override
    public String nextToken() {
        return null;
    }

    @Override
    public boolean hasMoreTokens() {
        return false;
    }

    @Override
    public List<String> getTokens() {
        return tokens;
    }
}
