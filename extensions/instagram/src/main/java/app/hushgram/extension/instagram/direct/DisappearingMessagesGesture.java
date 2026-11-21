/*
 * Copyright 2026 HushGram contributors
 * https://github.com/SysAdminDoc/HushGram
 */
package app.hushgram.extension.instagram.direct;

import app.hushgram.extension.instagram.settings.FamilyNames;
import app.hushgram.extension.instagram.settings.Settings;
import app.hushgram.extension.shared.Utils;
import app.hushgram.extension.shared.diagnostics.HookStatus;

/**
 * Runtime switch for the disappearing-messages swipe hook.
 * Fails open: Instagram keeps its native gesture if settings are unavailable or a read fails.
 */
public final class DisappearingMessagesGesture {
    private DisappearingMessagesGesture() {
    }

    public static boolean enabled() {
        try {
            HookStatus.invoked(FamilyNames.DISAPPEARING_MESSAGES_GESTURE);
            return Utils.settingsReady() && Settings.DISABLE_DISAPPEARING_MESSAGES_GESTURE.get();
        } catch (Throwable ignored) {
            HookStatus.threw(FamilyNames.DISAPPEARING_MESSAGES_GESTURE, "switch read", ignored);
            return false;
        }
    }
}
