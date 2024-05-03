package com.faboda.curl.ast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurlASTBuilder {
    public static ASTNode buildAST(List<String> tokens) {

        ASTNode rootNode = new ASTNode(NodeType.COMMAND, tokens.get(0));
        ASTNode urlNode = new ASTNode(NodeType.URL, tokens.get(1).replace("'", ""));
        ASTNode headerNode = new ASTNode(NodeType.HEADERS, "headers");
        ASTNode dataNode = new ASTNode(NodeType.DATA, "data");

        Map<String, String> headers = parseHeaders(tokens);
        Map<String, String> rawData = parseDataRaw(tokens);


        List<ASTNode> headerChildrenNodes = buildChildHeaderNodes(headers);
        headerNode.addChildren(headerChildrenNodes);

        List<ASTNode> dataChildrenNodes = buildRawDataNodes(rawData);
        dataNode.addChildren(dataChildrenNodes);

        rootNode.addChild(urlNode);
        rootNode.addChildren(headerChildrenNodes);
        rootNode.addChildren(dataChildrenNodes);

        return rootNode;

    }


    private static List<ASTNode> buildChildHeaderNodes(Map<String, String> headers) {
        List<ASTNode> childrenNodes = new ArrayList<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            ASTNode header = new ASTNode(NodeType.HEADER, entry.getValue());
            header.setKey(entry.getKey());
            childrenNodes.add(header);
        }
        return childrenNodes;
    }

    public static Map<String, String> parseHeaders(List<String> curlTokens) {
        Map<String, String> headers = new HashMap<>();

        int length = curlTokens.size();
        for (int i = 0; i < length; i++) {
            if (curlTokens.get(i).startsWith("-H") && (i + 1) < length) {
                String header = curlTokens.get(i).substring(3);
                String[] parts = header.split(":");
                if (parts.length == 2) {
                    headers.put(parts[0], parts[1].replace("'", ""));
                }
            }
        }
        return headers;
    }

    private static List<ASTNode> buildRawDataNodes(Map<String, String> dataPairs) {
        List<ASTNode> childrenNodes = new ArrayList<>();
        for (Map.Entry<String, String> pair : dataPairs.entrySet()) {
            ASTNode dataNode = new ASTNode(NodeType.DATAPAIR, pair.getValue());
            dataNode.setKey(pair.getKey());
            childrenNodes.add(dataNode);
        }

        return childrenNodes;
    }

    public static Map<String, String> parseDataRaw(List<String> curlTokens) {
        Map<String, String> dataItems = new HashMap<>();

        for (String curlToken : curlTokens) {
            if (curlToken.startsWith("--data-raw")) {
                String payload = curlToken.split(" ", 2)[1];
                String[] pairs = payload.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = keyValue[1];
                        dataItems.put(key, value);
                    }
                }
                if (dataItems.isEmpty()) {
                    Gson gson = new Gson();
                    Map<String, Object> gsoData = gson.fromJson(payload.replace("'", ""), Map.class);
                    for (Map.Entry<String, Object> entry : gsoData.entrySet()) {
                        dataItems.put(entry.getKey(), entry.getValue().toString());
                    }
                }


            }
        }
        return dataItems;
    }
}
