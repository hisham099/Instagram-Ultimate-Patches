# Port assessment: Piko — Disable disappearing messages swipe

Audit date: 2026-11-20  
Decision: **Unique feature candidate; proceed to target-456 fingerprint review before importing. Not yet ported or certified.**

## Immutable provenance

- Upstream: https://github.com/crimera/piko
- Pinned commit: `297519632c897d19e9f0df0c39e5b315d1a7390b`
- Declared Instagram target: `452.1.0.53.64`
- License: GPL-3.0; source file also refers to a NOTICE file for GPLv3 §7(b), which must be preserved and reviewed before copying.
- Patch: `patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/DisableDisappearingMessagesGesturePatch.kt`
- Setting declaration: `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/Settings.java`, key `disable_disappearing_messages_gesture`.
- Runtime accessor: `extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/Pref.java`, method `disableDisappearingMessagesGesture()`.
- User-facing resources: `patches/src/main/resources/addresources/values/instagram/strings.xml`.

## Feature behavior

Prevents the upward swipe gesture in a chat from enabling disappearing messages, while preserving Instagram's own gesture cleanup path when the setting is active. The setting defaults to off and is connected to Piko's settings framework.

## Patch mechanics

The patch declares two fingerprints:
1. `ToggleDisappearingMessagesFingerprint`: a void method containing the `disappearing_messages` string, a call returning boolean with UserSession and another object parameter, followed by `MOVE_RESULT`.
2. `DisappearingMessagesGestureFingerprint`: a related method with a boolean state check, `View.setOnTouchListener`, `RefreshableNestedScrollingParent.setRefreshingDistance`, and a final no-argument void call.

At the state-check point it inserts a runtime setting test and branches to skip the gesture path, while leaving the cleanup path available. It verifies that both fingerprints refer to the same state-check method before modifying bytecode.

## Duplicate review against HushGram

The pinned HushGram Instagram manifest and source search did not return an equivalent “disable disappearing messages swipe” feature or matching key. This is a preliminary name/implementation search, not proof that no overlapping behavior exists elsewhere; inspect the HushGram chat/ephemeral-media patches before final acceptance.

## Required 456 port gates

- Resolve both fingerprints against the exact Instagram `456.0.0.53.78` APK (version code `386214574` ARM64, `386214562` ARMv7, `386214575` x86 as declared by the pinned HushGram manifest).
- Verify the matched method is the gesture controller and that the state-check instruction register/branch labels still have the same semantics.
- Confirm the inserted branch cannot block swipe-to-disable or break touch-listener cleanup.
- Replace Piko settings integration with IUP's single persistent settings registry and bilingual Arabic/English resources; do not copy Piko's complete settings subsystem.
- Add patch-application tests for all supported architectures and runtime tests in a chat with disappearing messages both enabled and disabled.
- Preserve Piko LICENSE/NOTICE and per-file attribution.

## Status

| Gate | Status |
|---|---|
| Source and pinned provenance identified | Passed |
| Kotlin patch and Java setting dependency traced | Passed |
| Exact duplicate found in HushGram | No equivalent found in preliminary search |
| 456 fingerprints resolved | **Not yet; blocked on target APK inspection** |
| IUP settings/localization integration | Not started |
| Port and build tests | Not started |
| Runtime test | Not run |
| Release eligibility | No |

## Source links

- [Patch implementation](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/DisableDisappearingMessagesGesturePatch.kt)
- [Settings.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/Settings.java)
- [Pref.java](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/Pref.java)
- [Instagram strings](https://github.com/crimera/piko/blob/297519632c897d19e9f0df0c39e5b315d1a7390b/patches/src/main/resources/addresources/values/instagram/strings.xml)
