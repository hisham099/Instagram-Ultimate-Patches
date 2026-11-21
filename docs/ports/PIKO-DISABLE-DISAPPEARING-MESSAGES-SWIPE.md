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


## 8. Follow-up semantic duplicate review

A second pass inspected the pinned HushGram Instagram patch source tree for disappearing-message, ephemeral-message, vanish-mode, swipe and gesture implementations. No HushGram patch was found that disables the specific upward gesture used to turn on disappearing messages.

Nearby HushGram features are not equivalent:
- `Stop swipe up to reply` concerns story replies, not the disappearing-messages chat gesture.
- `Lock your messages` is a message-access/privacy control, not a gesture override.
- Message history and ephemeral-media features have different behavior and should not be treated as duplicates.

**Updated decision:** retain this as a high-value unique Piko candidate. It remains a source-level candidate only; no code has been imported and no 456 compatibility claim is made. Before porting, inspect the exact 456 APK for the two linked fingerprints and confirm the inserted branch preserves the normal disable-mode gesture cleanup. Keep the preference default off and expose it only if the patch successfully applies.

## 9. Deeper settings-path review (2026-11-21)

The full UI dependency chain is now confirmed from the pinned Piko source:

- `SettingsStatus.java` owns the `disableDisappearingMessagesGesture` availability flag and includes it in `dmSection()`.
- `SettingsStatus.loadStatusMap()` publishes the availability state under `piko_disable_disappearing_messages_gesture`.
- `ScreenBuilder.java` adds the switch only when that availability flag is true. It binds the visible title and description to the string resources and the persistent `Settings.DISABLE_DISAPPEARING_MESSAGES_GESTURE` key.
- `Settings.java` declares a BooleanSetting with key `disable_disappearing_messages_gesture`, default `false`, and Piko's `REQUIRED` setting policy.
- `Pref.java` returns true only when both the saved preference and the patch-availability flag are true. This prevents a stale preference from activating a patch that did not apply.
- The patch calls `enableSettings("disableDisappearingMessagesGesture")`, which is what exposes the setting through the availability map.

### Integration consequence

This feature is coupled to Piko's shared settings architecture. Its `settingsPatch` dependency is not a small utility: it pulls in shared extension hooks, settings resources, native switch styling, activity/modal handling, navigation/action-bar hooks, profile entities, notification fixes, link validation, signature spoofing, build-expiry handling, and other patches. Importing the feature by copying `settingsPatch` would therefore pull a large unrelated patch graph into IUP and risk duplicate hooks and conflicts.

**IUP port decision:** do not import Piko's `settingsPatch` wholesale. Implement the feature against IUP's own settings/extension registry once the actual IUP build source tree is established. Preserve the two-gate logic (saved preference AND patch successfully applied), default the switch off, and show it only when the patch is available.

### Proposed IUP bilingual strings

- English title: `Disable disappearing-message swipe`
- English description: `Prevents an upward swipe in a chat from turning on disappearing messages`
- Arabic title: `منع تفعيل الرسائل المختفية بالسحب`
- Arabic description: `يمنع السحب إلى أعلى داخل المحادثة من تفعيل وضع الرسائل المختفية`

These are proposed IUP strings, not yet added to an application resource bundle.

### Newly verified source files

- [SettingsStatus.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsStatus.java)
- [ScreenBuilder.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/ScreenBuilder.java)
- [SettingsPatch.kt](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/patches/src/main/kotlin/app/crimera/patches/instagram/misc/settings/SettingsPatch.kt)
- [Piko NOTICE](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/NOTICE)

## 10. Updated status

| Gate | Status |
|---|---|
| Kotlin patch logic | Source-reviewed |
| Persistent preference key/default | Source-reviewed |
| Availability flag and UI visibility gate | Source-reviewed |
| Piko settings dependency impact | Source-reviewed; broad dependency intentionally not imported |
| IUP-native settings adapter | Pending IUP build-tree integration |
| Arabic/English resource entries | Text prepared; not yet in app resources |
| Exact 456 APK fingerprint match | **Unverified; target APK/DEX required** |
| IUP code import/build/runtime test | Not done |