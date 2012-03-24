package com.tydic.sso.Util;

/**
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-24
 * Time: 20:59:30
 * To change this template use File | Settings | File Templates.
 */
public class CommandMethod {
    public static final String cleanupUrl(String url) {
        if (url == null) {
            return null;
        }

        int jsessionPosition = url.indexOf(";jsession");

        if (jsessionPosition == -1) {
            return url;
        }

        int questionMarkPosition = url.indexOf("?");

        if (questionMarkPosition < jsessionPosition) {
            return url.substring(0, url.indexOf(";jsession"));
        }

        return url.substring(0, jsessionPosition) + url.substring(questionMarkPosition);
    }
}
