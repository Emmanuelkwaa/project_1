package com.skillstorm.services;

/**
 * This class helps extract specific url endpoints from the request url
 */
public class URLParserService {

    /**
     * This method extracts ID from the giving url
     * @param url   The giving url that will be dissected to extract ID
     * @return int  Integer ID
     */
    public int extractIdFromURL(String url) {
//		System.out.println(url); // /12/123
        String[] splitString = url.split("/"); // [12, 123]
        int id;
        try {
            id = Integer.parseInt(splitString[1]);
            return id;
        } catch (Exception e) {
            return id = 0;
        }
    }

//    public int extractQueryFromURL(String url) {
//		String splitString = url.substring(url.indexOf("=")+1);
//		return Integer.parseInt(splitString);
//	}

    /**
     * This method extracts sub-url from the giving url
     * @param url       The giving url that will be dissected to extract sub-url
     * @return String   String sub-url
     */
    public String extractSubUrlFromURL(String url) {
        try {
            String splitString = url.split("/")[1];
            if (splitString.equals("complete")) {
                return splitString;
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}
