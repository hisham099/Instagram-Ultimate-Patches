# Piko implementation intake — Disable disappearing messages swipe

Audit date: 2026-11-20  
Source: `crimera/piko`  
Pinned commit: `297519632c897d19e9f0df0c39e5b315d1a7390b`  
Decision: **Unique feature candidate; source graph mapped; do not enable or ship for Instagram 456 until retargeted and tested.**

## 1. Exact upstream implementation

- Patch: `patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/DisableDisappearingMessagesGesturePatch.kt`
- Setting declaration: `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/Settings.java`, `DISABLE_DISAPPEARING_MESSAGES_GESTURE`
- Runtime preference: `extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/Pref.java`, `disableDisappearingMessagesGesture()`
- Patch-availability flag: `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsStatus.java`, `disableDisappearingMessagesGesture`
- Patch uses the Piko settings infrastructure and `enableSettings("disableDisappearingMessagesGesture")`.

## 2. Behavior and safety boundary

The patch prevents the upward chat gesture from enabling disappearing messages while preserving Instagram's own gesture cleanup path when the mode is inactive. It is a user-controlled gesture/UI behavior change; it does not disable disappearing messages globally, retrieve expired content, or bypass account protections.

## 3. Fingerprint and compatibility risk

The patch locates a method containing:
- string `disappearing_messages`;
- a call taking `UserSession` and `Ljava/lang`, returning boolean;
- a subsequent `MOVE_RESULT`;
- a `View.setOnTouchListener` call;
- `RefreshableNestedScrollingParent.setRefreshingDistance`;
- a local no-argument void method call.

It additionally verifies that the state-check method reference matches the separate toggle fingerprint. The injected logic allocates a free register and branches around the gesture path according to the runtime setting.

Piko's pinned `Constants.kt` declares **only Instagram 452.1.0.53.64**, version code 385811006 (ARM64). No 456 fingerprint evidence is present in this source snapshot. These method names and strings are not proof that the target 456 APK still has the same structure.

## 4. IUP duplicate check

A code-search check of the pinned HushGram baseline for the feature-specific setting/method identifier `disableDisappearingMessagesGesture` and the source string `disappearing_messages` returned no match. This supports treating the feature as a likely addition, but is not a complete semantic duplicate audit across all HushGram patch sources.

## 5. Integration dependencies

A correct port is not just the Kotlin patch file. It requires:
1. Rebase package imports from `app.crimera.patches...` onto IUP's actual patch namespace.
2. Map `settingsPatch`, `enableSettings`, `COMPATIBILITY_INSTAGRAM`, `USER_SESSION_CLASS`, `PREF_CALL_DESCRIPTOR`, and the Morphe fingerprint/instruction APIs to the actual IUP framework versions.
3. Add the persistent boolean setting to IUP's unified settings registry (default off until target verification).
4. Add the runtime getter and a patch-availability flag; ensure the flag is emitted only when the patch was applied successfully.
5. Add English and Arabic labels/descriptions and RTL-safe settings presentation.
6. Add patch-application tests against the exact Instagram 456.0.0.53.78 APK; test both setting states and verify the gesture to disable disappearing mode remains usable.
7. Preserve Piko GPL-3.0 provenance, its NOTICE requirements, and attribution for any copied/adapted source.

## 6. Status

| Gate | Status |
|---|---|
| Exact pinned source inspected | Passed |
| Kotlin patch behavior traced | Passed |
| Java setting/runtime/status dependencies traced | Passed |
| Initial HushGram exact-symbol duplicate search | No match found; semantic audit still needed |
| 456.0.0.53.78 fingerprints | **Not verified** |
| Code copied into IUP build graph | Not started |
| Unified setting + Arabic/English localization | Not started |
| Patch application / device test on 456 | Not run |
| Release eligibility | **No** |

## 7. Source links

- [Piko patch](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/DisableDisappearingMessagesGesturePatch.kt)
- [Settings.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/Settings.java)
- [Pref.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/Pref.java)
- [SettingsStatus.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsStatus.java)
- [Constants.kt](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/patches/src/main/kotlin/app/crimera/patches/instagram/utils/Constants.kt)
