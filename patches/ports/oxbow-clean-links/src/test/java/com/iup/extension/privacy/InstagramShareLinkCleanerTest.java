package com.iup.extension.privacy;

/** Dependency-free regression tests; run with the JDK using the documented command. */
public final class InstagramShareLinkCleanerTest {
    private static int assertions;

    public static void main(String[] args) {
        eq("https://www.instagram.com/p/ABC/",
                InstagramShareLinkCleaner.cleanUrl("https://www.instagram.com/p/ABC/?igsh=xyz"));
        eq("https://www.instagram.com/p/ABC/?img_index=2",
                InstagramShareLinkCleaner.cleanUrl("https://www.instagram.com/p/ABC/?img_index=2&igsh=1"));
        eq("https://instagram.com/natgeo#x",
                InstagramShareLinkCleaner.cleanUrl("https://instagram.com/natgeo?igshid=1#x"));
        eq("https://www.instagram.com/p/ABC/?comment_id=9&hl=en",
                InstagramShareLinkCleaner.cleanUrl("https://www.instagram.com/p/ABC/?comment_id=9&utm_source=x&hl=en"));
        eq("https://example.com/?igsh=1",
                InstagramShareLinkCleaner.cleanUrl("https://example.com/?igsh=1"));
        eq("https://www.instagram.com/p/ABC/",
                InstagramShareLinkCleaner.cleanUrl("https://www.instagram.com/p/ABC/?utm_source=x&igsh=1"));
        eq("See https://www.instagram.com/p/A/ and https://ig.me/m/user",
                InstagramShareLinkCleaner.clean("See https://www.instagram.com/p/A/?igsh=x and https://ig.me/m/user?utm_campaign=y").toString());
        eq("https://www.instagram.com/p/A/?story_media_id=42",
                InstagramShareLinkCleaner.cleanUrl("https://www.instagram.com/p/A/?story_media_id=42&obrf=x"));
        eq("https://www.instagram.com/p/A/?hl=en#section",
                InstagramShareLinkCleaner.cleanUrl("https://www.instagram.com/p/A/?hl=en&utm_source=x#section"));
        String unchanged = "No Instagram URL here";
        if (InstagramShareLinkCleaner.clean(unchanged) != unchanged) {
            throw new AssertionError("unchanged input should retain its CharSequence instance");
        }
        assertions++;
        System.out.println("PASS: " + assertions + " assertions");
    }

    private static void eq(String expected, String actual) {
        assertions++;
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + "\nActual:   " + actual);
        }
    }
}
