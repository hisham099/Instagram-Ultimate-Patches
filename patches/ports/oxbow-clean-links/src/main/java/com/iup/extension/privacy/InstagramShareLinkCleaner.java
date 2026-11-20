package com.iup.extension.privacy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pure-Java URL cleaner ported from Oxbow's GPL-3.0 Clean share links feature.
 *
 * This utility intentionally has no Android or Morphe dependency so its URL
 * behavior can be tested independently before bytecode hooks are ported.
 */
public final class InstagramShareLinkCleaner {
    private InstagramShareLinkCleaner() {}

    private static final Pattern INSTAGRAM_URL = Pattern.compile(
            "https?://(?:www\\.)?(?:instagram\\.com|instagr\\.am|ig\\.me)/[^\\s\\\"'<>]*",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern FUNCTIONAL_PARAMETER = Pattern.compile(
            "^(?:img_index|story_media_id|comment_id|hl)$", Pattern.CASE_INSENSITIVE);

    public static CharSequence clean(CharSequence text) {
        if (text == null) return null;
        String source = text.toString();
        Matcher matcher = INSTAGRAM_URL.matcher(source);
        StringBuilder output = null;
        int last = 0;
        while (matcher.find()) {
            String url = matcher.group();
            String cleaned = cleanUrl(url);
            if (cleaned.equals(url)) continue;
            if (output == null) output = new StringBuilder(source.length());
            output.append(source, last, matcher.start()).append(cleaned);
            last = matcher.end();
        }
        if (output == null) return text;
        return output.append(source, last, source.length()).toString();
    }

    public static String cleanUrl(String url) {
        if (url == null) return null;
        // Defense in depth: never alter non-Instagram URLs, even when this helper is called directly.
        if (!INSTAGRAM_URL.matcher(url).matches()) return url;
        int queryStart = url.indexOf('?');
        if (queryStart < 0) return url;
        int fragmentStart = url.indexOf('#', queryStart);
        String query = fragmentStart < 0
                ? url.substring(queryStart + 1)
                : url.substring(queryStart + 1, fragmentStart);
        StringBuilder kept = new StringBuilder();
        for (String pair : query.split("&")) {
            if (pair.isEmpty()) continue;
            int equals = pair.indexOf('=');
            String key = equals < 0 ? pair : pair.substring(0, equals);
            if (!FUNCTIONAL_PARAMETER.matcher(key).matches()) continue;
            if (kept.length() > 0) kept.append('&');
            kept.append(pair);
        }
        String base = url.substring(0, queryStart);
        String fragment = fragmentStart < 0 ? "" : url.substring(fragmentStart);
        return kept.length() == 0 ? base + fragment : base + "?" + kept + fragment;
    }
}
